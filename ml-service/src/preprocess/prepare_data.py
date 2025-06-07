import pandas as pd
from collections import Counter
from pathlib import Path

ROOT = Path(__file__).parent.parent

MATCHES_CSV = ROOT / 'data' / 'battle_history.csv'
CARDS_CSV   = ROOT / 'data' / 'cards_info.csv'
MODEL_PATH  = ROOT / 'models' / 'multitask_model.joblib'

FEATURE_COLS = [f'card{i}' for i in range(1, 9)]


def load_full_data() -> pd.DataFrame:
    return pd.read_csv(MATCHES_CSV, sep=';')


def load_cards() -> pd.DataFrame:
    return pd.read_csv(CARDS_CSV, sep=';')


def get_card_availability(trophies: int, cards_df: pd.DataFrame) -> dict[str, int]:
    return {
        row['name']: int(row['trophies'] <= trophies)
        for _, row in cards_df.iterrows()
    }


def load_data(trophies: int) -> tuple[pd.DataFrame, pd.DataFrame]:
    df = load_full_data()
    X = df[FEATURE_COLS].copy()
    y_win = df['win']

    deck_counts = Counter(tuple(df.loc[i, FEATURE_COLS]) for i in df.index)
    y_count = df.apply(
        lambda row: deck_counts[tuple(row[c] for c in FEATURE_COLS)],
        axis=1
    )

    cards_df   = load_cards()
    availability = get_card_availability(trophies, cards_df)

    for c in FEATURE_COLS:
        X[f'{c}_available'] = X[c].map(availability).fillna(0).astype(int)

    y = pd.concat([y_win.rename('win'), y_count.rename('deck_count')], axis=1)
    return X, y