import joblib
import pandas as pd
from sklearn.ensemble import RandomForestRegressor
from sklearn.multioutput import MultiOutputRegressor
from sklearn.model_selection import train_test_split
from sklearn.preprocessing import OneHotEncoder
from sklearn.compose import ColumnTransformer
from sklearn.pipeline import Pipeline
from sklearn.metrics import r2_score, mean_squared_error

from src.preprocess.prepare_data import load_data, load_full_data, FEATURE_COLS, MODEL_PATH


def train_and_save():
    df = load_full_data()

    X = df[FEATURE_COLS]
    y_win = df['win']
    from collections import Counter
    deck_counts = Counter(tuple(df.loc[i, FEATURE_COLS]) for i in df.index)
    y_count = df.apply(lambda row: deck_counts[tuple(row[c] for c in FEATURE_COLS)], axis=1)
    y = pd.concat([y_win.rename('win'), y_count.rename('deck_count')], axis=1)

    X_train, X_test, y_train, y_test = train_test_split(
        X, y, test_size=0.2, random_state=42, stratify=y['win']
    )

    ohe = OneHotEncoder(handle_unknown='ignore')
    preproc = ColumnTransformer(
        [('cards_ohe', ohe, FEATURE_COLS)],
        remainder='drop'
    )
    base = RandomForestRegressor(n_estimators=200, random_state=42, n_jobs=-1)
    multi = MultiOutputRegressor(base)
    pipe = Pipeline([
        ('pre', preproc),
        ('reg', multi)
    ])

    pipe.fit(X_train, y_train)

    y_pred = pipe.predict(X_test)
    print("WIN R2:", r2_score(y_test['win'], y_pred[:, 0]))
    print("COUNT R2:", r2_score(y_test['deck_count'], y_pred[:, 1]))
    print("WIN MSE:", mean_squared_error(y_test['win'], y_pred[:, 0]))
    print("COUNT MSE:", mean_squared_error(y_test['deck_count'], y_pred[:, 1]))

    joblib.dump(pipe, MODEL_PATH)
    print(f"Модель сохранена в {MODEL_PATH}")

if __name__ == "__main__":
    train_and_save()