import joblib

popularity = joblib.load('models/deck_popularity_model.joblib')

def top_decks(n=5):
    sorted_decks = sorted(popularity.items(), key=lambda x: x[1], reverse=True)
    return sorted_decks[:n]

print(top_decks())
