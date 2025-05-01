import pandas as pd
from collections import Counter
import joblib
from preprocess.prepare_data import load_and_prepare_data

df = load_and_prepare_data('data/matches.csv')

deck_counts = Counter()

for deck in df['deck']:
    deck_key = tuple(sorted(deck))
    deck_counts[deck_key] += 1

popularity = dict(deck_counts)
joblib.dump(popularity, 'models/deck_popularity_model.joblib')
