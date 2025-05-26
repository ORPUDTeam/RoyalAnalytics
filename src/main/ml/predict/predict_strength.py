import joblib
import pandas as pd
from preprocess.prepare_data import load_data

def top_strong(n=5):
    # Загружаем X (игнорируем y)
    X, _ = load_data()
    model = joblib.load('models/multitask_model.joblib')
    win_pred, _ = model.predict(X).T
    df = pd.read_csv('data/matches.csv', sep=';')
    df['win_prob'] = win_pred
    return df.sort_values('win_prob', ascending=False).head(n)

if __name__ == "__main__":
    print(top_strong(5))
