import pandas as pd

def load_and_prepare_data(path):
    df = pd.read_csv(path)
    df['deck'] = df['deck'].apply(lambda x: list(map(int, x.split(','))))
    return df
