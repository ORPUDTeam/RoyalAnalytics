import joblib
from sklearn.ensemble import RandomForestRegressor
from sklearn.multioutput import MultiOutputRegressor
from sklearn.model_selection import train_test_split
from sklearn.preprocessing import OneHotEncoder
from sklearn.compose import ColumnTransformer
from sklearn.pipeline import Pipeline
from sklearn.metrics import r2_score, mean_squared_error

from ml.preprocess.prepare_data import load_data, FEATURE_COLS, MODEL_PATH


def train_and_save():
    # 1) Загрузка признаков и двух целей
    X, y = load_data()

    # 2) Разбиение на TRAIN/TEST
    X_train, X_test, y_train, y_test = train_test_split(
        X, y, test_size=0.2, random_state=42, stratify=y['win']
    )

    # 3) Pipeline: one-hot по картам → мульти-выход
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

    # 4) Обучение
    pipe.fit(X_train, y_train)

    # 5) Оценка качества сразу на test
    y_pred = pipe.predict(X_test)
    print("WIN R2:", r2_score(y_test['win'], y_pred[:, 0]))
    print("COUNT R2:", r2_score(y_test['deck_count'], y_pred[:, 1]))
    print("WIN MSE:", mean_squared_error(y_test['win'], y_pred[:, 0]))
    print("COUNT MSE:", mean_squared_error(y_test['deck_count'], y_pred[:, 1]))

    # 6) Сохранение модели
    joblib.dump(pipe, MODEL_PATH)
    print(f"Модель сохранена в {MODEL_PATH}")


if __name__ == "__main__":
    train_and_save()