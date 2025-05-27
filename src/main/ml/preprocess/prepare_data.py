import pandas as pd
from collections import Counter

MATCHES_CSV = 'ml/data/battle_history.csv'
CARDS_CSV = 'ml/data/cards_info.csv'
MODEL_PATH = 'ml/models/multitask_model.joblib'
FEATURE_COLS = [f'card{i}' for i in range(1, 9)]


def load_data(path: str = MATCHES_CSV, sep: str = ';'):
    """
    Загружает набор матчей и возвращает X (DataFrame с 8 картами)
    и y (DataFrame с двумя целевыми столбцами: win и deck_count).
    """
    df = pd.read_csv(path, sep=sep)
    X = df[FEATURE_COLS].copy()
    y_win = df['win']
    # популярность колод
    deck_counts = Counter(tuple(df.loc[i, FEATURE_COLS]) for i in df.index)
    y_count = df.apply(
        lambda row: deck_counts[tuple(row[c] for c in FEATURE_COLS)],
        axis=1
    )
    return X, pd.concat([y_win.rename('win'), y_count.rename('deck_count')], axis=1)


def load_cards(path: str = CARDS_CSV, sep: str = ';'):
    """
    Загружает информацию о картах:
    name — название
    elixir — стоимость
    trophies — минимальные кубки для разблокировки
    """
    return pd.read_csv(path, sep=sep)


def load_full_data(path: str = MATCHES_CSV, sep: str = ';'):
    """
    Возвращает полный DataFrame матчей (для EDA или вывода).
    """
    return pd.read_csv(path, sep=sep)