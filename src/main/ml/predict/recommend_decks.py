import joblib
import pandas as pd

from ml.preprocess.prepare_data import load_full_data, load_cards, FEATURE_COLS, MODEL_PATH

# 1) загрузка
model = joblib.load(MODEL_PATH)
matches_df = load_full_data()
cards_df   = load_cards()


def get_available_cards(trophies: int) -> pd.DataFrame:
    return cards_df[cards_df['trophies'] <= trophies]


def replace_card_if_locked(card_name: str, available: pd.DataFrame) -> str:
    if card_name in set(available['name']):
        return card_name
    if card_name.lower() == 'mirror':
        return None
    # замена по elixir
    cost = cards_df.loc[cards_df['name'] == card_name, 'elixir'].iat[0]
    same = available[available['elixir'] == cost]
    if same.empty:
        return available.sample(1, random_state=42)['name'].iat[0]
    return same.sample(1, random_state=42)['name'].iat[0]


def recommend_decks(trophies: int, top_n: int = 3):
    X = matches_df[FEATURE_COLS]
    win_pred, _ = model.predict(X).T
    matches_df['win_prob'] = win_pred

    # кандидатный пул
    candidate = matches_df.sort_values('win_prob', ascending=False).head(500)
    available = get_available_cards(trophies)

    recs = []
    for _, row in candidate.iterrows():
        deck = [row[c] for c in FEATURE_COLS]
        if 'Mirror' in deck and 'Mirror' not in set(available['name']):
            continue
        new_deck = []
        skip = False
        for c in deck:
            rc = replace_card_if_locked(c, available)
            if rc is None:
                skip = True
                break
            new_deck.append(rc)
        if skip:
            continue
        recs.append({'deck': new_deck, 'win_prob': row['win_prob']})
        if len(recs) >= top_n:
            break
    return recs


if __name__ == "__main__":
    for i, rec in enumerate(recommend_decks(6000, 3), 1):
        print(f"{i}. {rec['deck']} — win={rec['win_prob']:.3f}")