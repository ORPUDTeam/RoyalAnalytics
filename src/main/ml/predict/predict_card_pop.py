import joblib
from sklearn.preprocessing import OneHotEncoder
from sklearn.compose import ColumnTransformer
from ml.preprocess.prepare_data import FEATURE_COLS, MODEL_PATH, load_full_data


def top_cards(n: int = 10):
    pipe = joblib.load(MODEL_PATH)
    preproc: ColumnTransformer = pipe.named_steps['pre']
    ohe: OneHotEncoder = preproc.named_transformers_['cards_ohe']

    feat_names = ohe.get_feature_names_out(FEATURE_COLS)

    importances = pipe.named_steps['reg'].estimators_[0].feature_importances_

    card_imp = {}
    for fn, imp in zip(feat_names, importances):
        name = fn.split('_', 1)[1]
        card_imp[name] = card_imp.get(name, 0) + imp

    matches_df = load_full_data()

    total_picks = len(matches_df) * len(FEATURE_COLS)

    pick_counts = matches_df[FEATURE_COLS].stack().value_counts()

    result = []
    for card, importance in sorted(card_imp.items(), key=lambda x: x[1], reverse=True):
        pickrate = (pick_counts.get(card, 0) / total_picks) * 100
        result.append((card, importance, round(pickrate, 2)))

    return result[:n]


if __name__ == "__main__":
    for name, imp, pickrate in top_cards(10):
        print(f"{name}: Вклад в модель: {imp:.5f}, Пикрейт: {pickrate:.2f}%")
