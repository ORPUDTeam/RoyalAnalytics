import joblib
import pandas as pd
from preprocess.prepare_data import load_data

def top_popular(n=5):
    X, y = load_data()
    model = joblib.load('models/multitask_model.joblib')
    _, count_pred = model.predict(X).T
    df = pd.read_csv('data/matches.csv', sep=';')
    df['pop_pred'] = count_pred
    return df.sort_values('pop_pred', ascending=False).head(n)

if __name__ == "__main__":
    print(top_popular(5))
