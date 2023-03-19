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
    public void addEntityToScene (Entity entity) { entities.add(entity); }
    public void removeEntityFromScene (Entity entity) {
        entitiesToRemove.add(entity);
    }
    public <T extends Entity> void removeEntityFromScene(Class<T> type) {
        for (Entity entity : findEntities(type)){
            entitiesToRemove.add(entity);
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
        Array<Entity> entitiesCopy = new Array<>(this.entities); // like using temp.addAll(objects);
        return entitiesCopy;
    }

}
