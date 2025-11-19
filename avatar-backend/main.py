from fastapi import FastAPI
from sqlmodel import SQLModel, Field, Session, create_engine, select
from typing import Optional, List

class Avatar(SQLModel, table=True):
    id: Optional[int] = Field(default=None, primary_key=True)
    name: str
    description: str
    imageUrl: str

sqlite_file_name = "avatars.db"
sqlite_url = f"sqlite:///{sqlite_file_name}"
engine = create_engine(sqlite_url, echo=True)

app = FastAPI()

@app.on_event("startup")
def on_startup():
    SQLModel.metadata.create_all(engine)

@app.get("/avatars", response_model=List[Avatar])
def get_avatars():
    with Session(engine) as session:
        return session.exec(select(Avatar)).all()

@app.post("/avatars", response_model=Avatar)
def add_avatar(new_avatar: Avatar):
    with Session(engine) as session:
        session.add(new_avatar)
        session.commit()
        session.refresh(new_avatar)
        return new_avatar
