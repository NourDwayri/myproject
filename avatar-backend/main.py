from fastapi import FastAPI
from pydantic import BaseModel
from typing import List

app = FastAPI()

class Avatar(BaseModel):
    name: str
    description: str
    imageUrl: str

avatars = [
    {"name": "Michael", "description": "Beat me if you can", "imageUrl": "https://via.placeholder.com/150/FF0000/FFFFFF?text=Michael"},
    {"name": "Lina", "description": "I'm a girl, therefore smart", "imageUrl": "https://via.placeholder.com/150/00FF00/FFFFFF?text=Lina"},
    {"name": "Fadi", "description": "Being a nerd is cool", "imageUrl": "https://via.placeholder.com/150/0000FF/FFFFFF?text=Fadi"}
]

@app.get("/avatars", response_model=List[Avatar])
def get_avatars():
    return avatars
