package com.mygdx.chess;

import com.badlogic.gdx.utils.Array;

public class ChessScene {

    private final Chess game;

//    private boolean isChessPieceActive;
//    private ChessPiece activeChessPiece;
    private Array<Entity> entities;
    private Array<Entity> entitiesToRemove;
    public ChessScene (Chess game) {
        this.game = game;
//        this.isChessPieceActive = false;
//        this.activeChessPiece = null;
        entities = new Array<>();
        entitiesToRemove = new Array<>();
    }

    public void render () {
        for (Entity entity : entities) {
            entity.render();
        }
    }

    public void update () {
        for (Entity entity : entities) {
            entity.update();
        }
        for (Entity entity : entitiesToRemove) {
            entities.removeValue(entity,true);
        }
        entitiesToRemove.clear();
    }

    public void addEntity (Entity entity) {
        entities.add(entity);
    }

    public void removeEntity(Entity entity) {
        entitiesToRemove.add(entity);
    }
    public void removeEntity (String tag) {
        entitiesToRemove.addAll(findEntities(tag));
    }

    public <T extends Entity> void removeEntity (Class<T> type) {
        entitiesToRemove.addAll(findEntities(type));
    }

    public Array<Entity> getEntitiesCopy () {
        Array<Entity> entitiesCopy = new Array<>(this.entities); // like using temp.addAll(objects);
        return entitiesCopy;
    }

    /* very inefficient CPU wise, use it as little as possible.
    It requires a copy of the original since this is usually called in update,
    and since i cant nest iterators, i have to iterate on top of a new array,
    cloning this takes a lot of memory so in paper is pretty intensive */
    public Array<Entity> findEntities (String tag) {
        Array<Entity> entitiesFound = new Array<>();
        for (Entity entity : this.getEntitiesCopy()) {
            if(entity.getTag() == tag) entitiesFound.add(entity);
        }
        return entitiesFound;
    }

    public <T extends Entity> Array<T> findEntities (Class<T> type) {
        Array<T> entitiesOfTypeFound = new Array<>();
        for (Entity entity : this.getEntitiesCopy()) {
            if(type.isInstance(entity)) {
                entitiesOfTypeFound.add(type.cast(entity));
            }
        }
        return entitiesOfTypeFound;
    }

    public void createChessPiece (ChessPiece.Type type, int x, int y) {
        ChessPiece chessPiece = new ChessPiece(game, type, x, y);
        addEntity(chessPiece);
    }

    public void createMoveIndicator (ChessPiece chessPiece, int x, int y) {
        MoveIndicator moveIndicator = new MoveIndicator (game, chessPiece, x, y);
        addEntity(moveIndicator);
    }

    public void movePiece(Entity entity, int x, int y) {
        entity.setX(x); entity.setY(y);
    }
}
