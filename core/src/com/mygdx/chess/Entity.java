package com.mygdx.chess;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Entity {

    public Chess game;

    private int x, y, w, h;
    private Rectangle rect = new Rectangle();
    private Texture texture;

    private String tag;
    private boolean isClickeable;
    private boolean isDraggeable;

    public Entity(Chess game) {
        this.game = game;
        this.x = 0;
        this.y = 0;
        this.w = 0;
        this.h = 0;
        this.tag = null;
        this.isClickeable = false;
        this.isDraggeable = false;
        this.rect = new Rectangle();
        this.texture = game.textureList.get(TextureList.Key.ERR);
        game.sceneEntities.addEntity(this);
    }

    public void render () {
        game.batch.draw(this.texture,this.x,this.y);
    }
    public void update () {} // override this function

    public void remove () {
        game.sceneEntities.removeEntity(this);
    }

    public boolean isClicked () {
        this.setRect(); //updates where the rect is
        if(this.isClickeable && Gdx.input.justTouched() && this.getRect().contains(game.inputHandler.getMousePos().x,game.inputHandler.getMousePos().y)) {
            return true;
        }
        return false;
    }

    // get and set methods
    public String getTag () { return this.tag; }
    public void setTag (String tag) { this.tag = tag; }
    public Texture getTexture () { return texture; }
    public void setTexture (Texture texture) {
        this.texture = texture;
    }
    public int getX () { return this.x; }
    public void setX (int x) { this.x = x; }
    public int getY () { return this.y; }
    public void setY (int y) { this.y = y; }
    public int getW () { return this.w; }
    public void setW (int w) { this.w = w; }
    public int getH () { return this.h; }
    public void setH (int h) { this.h = h; }
    public Rectangle getRect () {
        return this.rect;
    }
    public void setRect () {
        this.rect.set(this.x,this.y,this.w,this.h);
    }
    public void setClickeable(boolean clickeable) { this.isClickeable = clickeable; }
    public void setDraggeable(boolean draggeable) { this.isDraggeable = draggeable; }


}
