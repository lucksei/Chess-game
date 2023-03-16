package com.mygdx.chess;

import com.badlogic.gdx.utils.Array;

public class SceneEntities {

    private Array<Entity> entities;
    private Array<Entity> entitiesToRemove;
    private Chess game;

    public SceneEntities (Chess game) {
        this.game = game;
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

    public void addEntity (Entity entity) { entities.add(entity); }
    public void removeEntity(Entity entity) {
        entitiesToRemove.add(entity);
    }
    public void removeEntity (String tag) {
        entitiesToRemove.addAll(findEntities(tag));
    }
    public <T extends Entity> void removeEntity (Class<T> type) {
        entitiesToRemove.addAll(findEntities(type));
    }
    public Array<Entity> findEntities (String tag) {
        Array<Entity> entitiesFound = new Array<>();
        for (Entity entity : this.getEntities()) {
            if(entity.getTag() == tag) entitiesFound.add(entity);
        }
        return entitiesFound;
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
    public Array<Entity> getEntities () {
        Array<Entity> entitiesCopy = new Array<>(this.entities); // like using temp.addAll(objects);
        return entitiesCopy;
    }

    public Array<BoardEntity> getBoardEntities() {
//        Array<Entity> entitiesCopy = new Array<>(this.entities);
        Array<BoardEntity> boardEntities = new Array<>();
        for (Entity entity : this.getEntities()) {
            if (entity instanceof BoardEntity) boardEntities.add((BoardEntity) entity);
        }
        return boardEntities;
    }

    public void movePiece(BoardEntity boardEntity, int gridX, int gridY) {
        boardEntity.setGridX(gridX);
        boardEntity.setGridY(gridY);
    }


}
