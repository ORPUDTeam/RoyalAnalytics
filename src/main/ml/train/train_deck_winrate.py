import pandas as pd
from sklearn.ensemble import RandomForestClassifier
from sklearn.model_selection import train_test_split
import joblib
from preprocess.prepare_data import load_and_prepare_data

df = load_and_prepare_data('data/matches.csv')

X = pd.DataFrame(df['deck'].tolist())
y = df['win']

X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2)

model = RandomForestClassifier(n_estimators=100)
model.fit(X_train, y_train)

joblib.dump(model, 'models/deck_winrate_model.joblib')
