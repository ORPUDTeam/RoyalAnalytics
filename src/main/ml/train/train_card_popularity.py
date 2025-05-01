import pandas as pd
from collections import Counter
import joblib
from preprocess.prepare_data import load_and_prepare_data

df = load_and_prepare_data('data/matches.csv')
card_counts = Counter()

for deck in df['deck']:
    card_counts.update(deck)

popularity = dict(card_counts)
joblib.dump(popularity, 'models/card_popularity_model.joblib')
