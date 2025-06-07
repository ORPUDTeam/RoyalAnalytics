from fastapi import FastAPI, HTTPException
from pydantic import BaseModel
import joblib
from src.predict.recommend_decks import recommend_decks

class Req(BaseModel):
    trophies: int
    top_n: int = 3

class Resp(BaseModel):
    decks: list[list[str]]

app = FastAPI(
    title="Deck Recommendation Service",
    version="1.0",
)

@app.post("/recommend", response_model=Resp)
def recommend(req: Req):
    try:
        decks = recommend_decks(req.trophies, req.top_n)
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))
    return Resp(decks=decks)

if __name__ == "__main__":
    import uvicorn
    uvicorn.run("app:app", host="0.0.0.0", port=8000)
