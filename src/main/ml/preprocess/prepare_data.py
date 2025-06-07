import pandas as pd
from collections import Counter

MATCHES_CSV = 'ml/data/battle_history.csv'
CARDS_CSV = 'ml/data/cards_info.csv'
MODEL_PATH = 'ml/models/multitask_model.joblib'
FEATURE_COLS = [f'card{i}' for i in range(1, 9)]


def load_data(trophies: int, path: str = MATCHES_CSV, sep: str = ';'):
    df = pd.read_csv(path, sep=sep)
    X = df[FEATURE_COLS].copy()
    y_win = df['win']
    deck_counts = Counter(tuple(df.loc[i, FEATURE_COLS]) for i in df.index)
    y_count = df.apply(lambda row: deck_counts[tuple(row[c] for c in FEATURE_COLS)], axis=1)

    cards_df = load_cards()
    availability = get_card_availability(trophies, cards_df)

    for c in FEATURE_COLS:
        X[f'{c}_available'] = X[c].apply(lambda name: availability.get(name, 0))

    return X, pd.concat([y_win.rename('win'), y_count.rename('deck_count')], axis=1)

def load_cards(path: str = CARDS_CSV, sep: str = ';'):
    return pd.read_csv(path, sep=sep)


def load_full_data(path: str = MATCHES_CSV, sep: str = ';'):
    return pd.read_csv(path, sep=sep)

def get_card_availability(trophies: int, cards_df: pd.DataFrame):
    availability = {}
    for card in cards_df['name']:
        availability[card] = int(cards_df.loc[cards_df['name'] == card, 'trophies'].iat[0] <= trophies)
    return availability
