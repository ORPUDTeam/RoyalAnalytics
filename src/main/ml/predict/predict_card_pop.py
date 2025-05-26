import joblib
from sklearn.preprocessing import OneHotEncoder

# Импортируем ColumnTransformer так же, как в обучении, чтобы знать имена фич
from sklearn.compose import ColumnTransformer

# Чтобы получить feature names, нам нужно скопировать препроцессор
import pickle

def top_cards(n=10):
    pipe = joblib.load('models/multitask_model.joblib')
    # достаём обученный one-hot encoder
    preproc: ColumnTransformer = pipe.named_steps['pre']
    ohe: OneHotEncoder = preproc.named_transformers_['ohe']
    # имена карт
    card_names = ohe.get_feature_names_out([f'card{i}' for i in range(1,9)])
    # feature_importances_ у каждого рандомфора
    importances = pipe.named_steps['reg'].estimators_[0].feature_importances_
    # свёртываем на карты (они идут блоком по 8*уникальных карт)
    df_imp = sorted(zip(card_names, importances), key=lambda x: x[1], reverse=True)
    return df_imp[:n]

if __name__ == "__main__":
    print(top_cards(10))
