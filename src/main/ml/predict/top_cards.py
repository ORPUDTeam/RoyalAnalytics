import joblib

popularity = joblib.load('models/card_popularity_model.joblib')

def top_cards(n=10):
    sorted_cards = sorted(popularity.items(), key=lambda x: x[1], reverse=True)
    return sorted_cards[:n]

print(top_cards())
