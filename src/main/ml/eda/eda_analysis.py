import matplotlib.pyplot as plt
from collections import Counter
from ml.preprocess.prepare_data import load_full_data, FEATURE_COLS


def run_eda():
    df = load_full_data()
    print("Размер данных:", df.shape)
    print("\nБаланс win:")
    print(df['win'].value_counts(normalize=True))
    print("\nПустые значения:")
    print(df.isna().sum())

    # частота карт
    counts = Counter()
    for c in FEATURE_COLS:
        counts.update(df[c])
    print("\nТоп-10 карт по частоте:")
    for card, cnt in counts.most_common(10):
        print(f"{card}: {cnt}")

    # визуализация win
    df['win'].value_counts().plot.bar(title='win distribution')
    plt.tight_layout()
    plt.show()


if __name__ == "__main__":
    run_eda()