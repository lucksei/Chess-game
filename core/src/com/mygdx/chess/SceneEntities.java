package com.mygdx.chess;

import com.badlogic.gdx.utils.Array;

public class SceneEntities {

    private Array<Entity> entities;
//    private Array<Entity> entitiesToRemove;
    private Chess game;

    public SceneEntities (Chess game) {
        this.game = game;
        entities = new Array<>();
//        entitiesToRemove = new Array<>();
    }

    public void render () {
        for (Entity entity : entities) {
            entity.render();
        }
    }
    public void update () {
        for (Entity entity : entities) {
            entity.update();
            entity.render();
        }
/*
        for (Entity entity : entitiesToRemove) {
            entities.removeValue(entity,true);
        }
        entitiesToRemove.clear();
*/
        for (Square square : findEntities(Square.class))
            square.remove();
    }
    public void addEntityToScene (Entity entity) {
        entities.add(entity);
        if(entity.entityController != null) entities.add(entity.entityController);
    }
    public void removeEntityFromScene (Entity entity) {
        entities.removeValue(entity,true);
//        entitiesToRemove.add(entity);
    }
    public <T extends Entity> void removeEntityFromScene(Class<T> type) {
        for (Entity entity : findEntities(type)){
            entity.remove();
        }
    }
    public <T extends Entity> Array<T> findEntities (Class<T> type) {
        Array<T> entitiesOfTypeFound = new Array<>();
        for (Entity entity : this.getEntities()) {
            if(type.isInstance(entity)) {
                entitiesOfTypeFound.add(type.cast(entity));
            }
        }
        return entitiesOfTypeFound;
    }
    public Array<BoardEntity> getFromSquare (int gridX, int gridY) {
        Array<BoardEntity> entitiesInSquare = new Array<>();
        for (BoardEntity entity : findEntities(BoardEntity.class)) {
            if(entity.getGridX() == gridX && entity.getGridY() == gridY) {
                entitiesInSquare.add(entity);
            }
        }
        return entitiesInSquare;
    }
    private Array<Entity> getEntities () {
        Array<Entity> entitiesCopy = new Array<>(this.entities); // like using temp.addAll(objects);
        return entitiesCopy;
    }

}
