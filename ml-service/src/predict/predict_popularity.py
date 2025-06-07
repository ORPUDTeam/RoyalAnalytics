import joblib
from src.preprocess.prepare_data import load_data, load_full_data, MODEL_PATH


def top_popular(n: int = 5, trophies: int = 9000):
    X, y = load_data(trophies)

    model = joblib.load(MODEL_PATH)

    _, count_pred = model.predict(X).T

    df = load_full_data()
    df['pop_pred'] = count_pred
    return df.sort_values('pop_pred', ascending=False).head(n)

if __name__ == "__main__":
    print(top_popular(5))