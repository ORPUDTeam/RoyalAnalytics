import joblib
import pandas as pd
from sklearn.ensemble import RandomForestRegressor
from sklearn.multioutput import MultiOutputRegressor
from sklearn.model_selection import train_test_split
from sklearn.preprocessing import OneHotEncoder
from sklearn.compose import ColumnTransformer
from sklearn.pipeline import Pipeline

from preprocess.prepare_data import load_data

def train_and_save():
    # 1) Загрузка признаков и двух целей
    X, y = load_data()
    # 2) Разбиение
    X_train, X_test, y_train, y_test = train_test_split(
        X, y, test_size=0.2, random_state=42
    )
    # 3) Pipeline: one-hot по картам → мульти-выход
    ohe = OneHotEncoder(handle_unknown='ignore')
    preproc = ColumnTransformer(
        [('ohe', ohe, [f'card{i}' for i in range(1,9)])],
        remainder='drop'
    )
    base = RandomForestRegressor(n_estimators=200, random_state=42, n_jobs=-1)
    multi = MultiOutputRegressor(base)
    pipe = Pipeline([
        ('pre', preproc),
        ('reg', multi)
    ])
    # 4) Обучение
    pipe.fit(X_train, y_train)
    # 5) Сохранение
    joblib.dump(pipe, 'models/multitask_model.joblib')
    print("Модель сохранена в models/multitask_model.joblib")

if __name__ == "__main__":
    train_and_save()
