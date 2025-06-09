from fastapi import FastAPI, HTTPException, Path
from pydantic import BaseModel
import joblib
from src.predict.recommend_decks import recommend_decks

class Resp(BaseModel):
    decks: list[list[str]]

app = FastAPI(
    title="Deck Recommendation Service",
    version="1.0",
)

@app.get("/recommend/{trophies}", response_model=Resp)
def recommend(trophies: int = Path(..., ge=0, le=10000)):
    try:
        decks = recommend_decks(trophies, top_n=1)
        if not decks:
            raise HTTPException(status_code=404, detail="No decks found")
    except HTTPException:
        raise
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))
    return Resp(decks=decks)

if __name__ == "__main__":
    import uvicorn
    uvicorn.run("app:app", host="0.0.0.0", port=8000)
