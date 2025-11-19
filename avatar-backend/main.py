from fastapi import FastAPI, HTTPException
from pydantic import BaseModel
from typing import List
from sqlalchemy import Column, Integer, String, create_engine
from sqlalchemy.orm import declarative_base, sessionmaker

app = FastAPI()

# ----- SQLite setup -----
DATABASE_URL = "sqlite:///./avatars.db"
engine = create_engine(DATABASE_URL, connect_args={"check_same_thread": False})
SessionLocal = sessionmaker(autocommit=False, autoflush=False, bind=engine)
Base = declarative_base()

# ----- Database model -----
class AvatarDB(Base):
    __tablename__ = "avatars"
    id = Column(Integer, primary_key=True, index=True)
    name = Column(String, index=True)
    description = Column(String)
    imageUrl = Column(String)

Base.metadata.create_all(bind=engine)

# ----- Pydantic schema -----
class Avatar(BaseModel):
    name: str
    description: str
    imageUrl: str

class AvatarResponse(Avatar):
    id: int

# ----- API endpoints -----
@app.get("/avatars", response_model=List[AvatarResponse])
def get_avatars():
    db = SessionLocal()
    avatars = db.query(AvatarDB).all()
    db.close()
    return avatars

@app.post("/avatars", response_model=AvatarResponse)
def add_avatar(avatar: Avatar):
    db = SessionLocal()
    new_avatar = AvatarDB(name=avatar.name, description=avatar.description, imageUrl=avatar.imageUrl)
    db.add(new_avatar)
    db.commit()
    db.refresh(new_avatar)
    db.close()
    return new_avatar
