import joblib
from sklearn.preprocessing import OneHotEncoder
from sklearn.compose import ColumnTransformer
from ml.preprocess.prepare_data import FEATURE_COLS, MODEL_PATH


def top_cards(n: int = 10):
    # загружаем Pipeline
    pipe = joblib.load(MODEL_PATH)
    preproc: ColumnTransformer = pipe.named_steps['pre']
    ohe: OneHotEncoder = preproc.named_transformers_['cards_ohe']

    # имена one-hot фич: cardX_<name>
    feat_names = ohe.get_feature_names_out(FEATURE_COLS)
    # важности базового регрессора
    importances = pipe.named_steps['reg'].estimators_[0].feature_importances_

    # агрегируем по названию карты
    card_imp = {}
    for fn, imp in zip(feat_names, importances):
        # название карты после '_' в feature name
        name = fn.split('_', 1)[1]
        card_imp[name] = card_imp.get(name, 0) + imp

    # топ-N карт
    return sorted(card_imp.items(), key=lambda x: x[1], reverse=True)[:n]


if __name__ == "__main__":
    print(top_cards(10))