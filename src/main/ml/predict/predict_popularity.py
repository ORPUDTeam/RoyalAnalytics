import joblib
from ml.preprocess.prepare_data import load_data, load_full_data, MODEL_PATH


def top_popular(n: int = 5):
    # X, y
    X, y = load_data()
    # модель
    model = joblib.load(MODEL_PATH)
    # предсказание deck_count
    _, count_pred = model.predict(X).T

    # полный DataFrame для вывода
    df = load_full_data()
    df['pop_pred'] = count_pred
    return df.sort_values('pop_pred', ascending=False).head(n)


if __name__ == "__main__":
    print(top_popular(5))