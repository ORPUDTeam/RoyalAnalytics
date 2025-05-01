import pandas as pd
import joblib

from preprocess.prepare_data import load_and_prepare_data

model = joblib.load('models/deck_winrate_model.joblib')
df = load_and_prepare_data('data/matches.csv')

def recommend_decks(trophies_min, trophies_max, top_n=5):
    filtered = df[(df['trophies'] >= trophies_min) & (df['trophies'] <= trophies_max)]
    decks = pd.DataFrame(filtered['deck'].tolist())
    preds = model.predict_proba(decks)[:, 1]
    filtered['win_prob'] = preds
    return filtered.sort_values('win_prob', ascending=False).head(top_n)[['deck', 'win_prob']]

print(recommend_decks(5000, 5200))
