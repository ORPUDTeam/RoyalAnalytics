import joblib
import pandas as pd

from ml.preprocess.prepare_data import load_full_data, load_cards, FEATURE_COLS, MODEL_PATH

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
    if card_name not in set(cards_df['name']):
        return None
    cost = cards_df.loc[cards_df['name'] == card_name, 'elixirCost'].iat[0]
    same = available[available['elixirCost'] == cost]
    if same.empty:
        return available.sample(1, random_state=42)['name'].iat[0]
    return same.sample(1, random_state=42)['name'].iat[0]


def recommend_decks(trophies: int, top_n: int = 3):
    X = matches_df[FEATURE_COLS]
    win_pred, _ = model.predict(X).T
    matches_df['win_prob'] = win_pred

    top_candidates = matches_df.sort_values('win_prob', ascending=False).head(1000)

    candidate = top_candidates.sample(n=min(100, len(top_candidates)), random_state=None).reset_index(drop=True)
    candidate = candidate.sample(frac=1, random_state=None).reset_index(drop=True)

    available = get_available_cards(trophies)
    available_names = set(available['name'])

    good_decks = []

    while not candidate.empty and len(good_decks) < top_n:
        row = candidate.iloc[0]
        deck = [row[c] for c in FEATURE_COLS]
        candidate = candidate.iloc[1:].reset_index(drop=True)

        if set(deck).issubset(available_names):
            if deck not in good_decks:
                good_decks.append(deck)
                continue

        new_deck = []
        used_cards = set()

        for c in deck:
            if c in available_names and c not in used_cards:
                new_deck.append(c)
                used_cards.add(c)
            else:
                replacement_candidates = list(available_names - used_cards)
                if not replacement_candidates:
                    new_deck = None
                    break
                replacement = replacement_candidates[0]
                new_deck.append(replacement)
                used_cards.add(replacement)

        if new_deck is None:
            continue
        if new_deck not in good_decks:
            good_decks.append(new_deck)

    return good_decks[:top_n]

if __name__ == "__main__":
    for i, rec in enumerate(recommend_decks(3000, 3), 1):
        print(f"{i}. {rec}")