package com.mygdx.chess;

public class Entity {
    public Chess game;
    public EntityController entityController;
    private int x, y, w, h;

    public Entity(Chess game) {
        this.game = game;
        this.entityController = null;
        game.sceneEntities.addEntityToScene(this);
        this.x = 0;
        this.y = 0;
        this.w = 0;
        this.h = 0;
    }

    public void render () {} // override this function
    public void update () {} // override this function

    public void remove () {
        game.sceneEntities.removeEntityFromScene(this);
        if(entityController != null) {
            entityController.remove();
        }
    }

    // get and set methods
    public int getX () { return this.x; }
    public void setX (int x) { this.x = x; }
    public int getY () { return this.y; }
    public void setY (int y) { this.y = y; }
    public int getW () { return this.w; }
    public void setW (int w) { this.w = w; }
    public int getH () { return this.h; }
    public void setH (int h) { this.h = h; }
    public Chess getGame () { return this.game; }
}
