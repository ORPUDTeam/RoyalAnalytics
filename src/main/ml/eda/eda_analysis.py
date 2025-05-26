import pandas as pd
from collections import Counter
import matplotlib.pyplot as plt

def run_eda(path='data/matches.csv'):
    df = pd.read_csv(path, sep=';')
    print("Размер данных:", df.shape)
    print("\nБаланс win:")
    print(df['win'].value_counts(normalize=True))
    print("\nПустые значения:")
    print(df.isna().sum())
    # Частота карт
    counts = Counter()
    for i in range(1,9):
        counts.update(df[f'card{i}'])
    print("\nТоп-10 карт по частоте:")
    for card, cnt in counts.most_common(10):
        print(f"{card}: {cnt}")
    # Визуализация
    df['win'].value_counts().plot.bar(title='win distribution')
    plt.show()

if __name__ == "__main__":
    run_eda()
