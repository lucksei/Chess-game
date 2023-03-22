package com.mygdx.chess;

import com.badlogic.gdx.utils.Array;

public class SceneEntities {

    private Array<Entity> entities;
    private Chess game;

    public SceneEntities (Chess game) {
        this.game = game;
        entities = new Array<>();
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
    }
    public void addEntityToScene (Entity entity) {
        entities.add(entity);
        if(entity.entityController != null) entities.add(entity.entityController);
    }
    public void removeEntityFromScene (Entity entity) {
        entities.removeValue(entity,true);
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
    private Array<Entity> getEntities () {
        Array<Entity> entitiesCopy = new Array<>(this.entities);
        return entitiesCopy;
    }

    public boolean contains (Entity entity) {
        return entities.contains(entity, true);
    }

}
