package com.mygdx.chess;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Entity {
    public Chess game;
    public EntityView entityView;
    private int x, y, w, h;
    private String tag;

    public Entity(Chess game) {
        this.game = game;
        this.entityView = null;
        game.sceneEntities.addEntity(this);
        this.x = 0;
        this.y = 0;
        this.w = 0;
        this.h = 0;
        this.tag = null;
    }

    public void render () {} // override this function
    public void update () {} // override this function

    public void remove () {
        game.sceneEntities.removeEntity(this);
        if(entityView != null) {
            entityView.remove();
        }
    }

    // get and set methods
    public String getTag () { return this.tag; }
    public void setTag (String tag) { this.tag = tag; }
    public int getX () { return this.x; }
    public void setX (int x) { this.x = x; }
    public int getY () { return this.y; }
    public void setY (int y) { this.y = y; }
    public int getW () { return this.w; }
    public void setW (int w) { this.w = w; }
    public int getH () { return this.h; }
    public void setH (int h) { this.h = h; }
}
