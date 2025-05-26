import pandas as pd
from collections import Counter

def load_data(path='data/matches.csv', sep=';'):
    df = pd.read_csv(path, sep=sep)
    # X — DataFrame из 8 карт
    X = df[[f'card{i}' for i in range(1,9)]]
    # y1 — win
    y_win = df['win']
    # y2 — deck_count: популярность колод в датасете
    # считаем частоту каждой колоды
    deck_counts = Counter(tuple(df.loc[i, [f'card{j}' for j in range(1,9)]])
                          for i in df.index)
    # превращаем в серию по строкам
    y_count = df.apply(lambda row: deck_counts[tuple(row[f'card{j}' ] for j in range(1,9))], axis=1)
    return X, pd.concat([y_win.rename('win'), y_count.rename('deck_count')], axis=1)
