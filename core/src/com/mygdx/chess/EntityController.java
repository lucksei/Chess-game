package com.mygdx.chess;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class EntityController extends Entity {

    private Entity parent;
    private Rectangle rect = new Rectangle();
    private Texture texture;
    private boolean isClickeable;
    private boolean isDraggeable;
    private boolean isDragging;

    public EntityController(Chess game, Entity parent) {
        super(game);
        this.parent = parent;
        this.isClickeable = false;
        this.isDraggeable = false;
        this.isDragging = false;
        this.rect = new Rectangle();
        this.texture = game.textureList.get(TextureList.Key.ERR);
    }
    public void render () {
        game.batch.draw(this.texture,getX(),getY());
    }
    public void update () {
        this.setW(parent.getW());
        this.setH(parent.getH());

        // move controller to mouse position
        if (!this.isDragging()) {
            this.setX(parent.getX());
            this.setY(parent.getY());
        } else {
            this.setX((int) game.inputHandler.getMousePos().x + this.getW()/2);
            this.setY((int) game.inputHandler.getMousePos().y + this.getH()/2);
        }
    }

    public boolean isClicked () {
        this.setRect(); //updates where the rect is
        if(this.isClickeable && Gdx.input.justTouched() && this.getRect().contains(game.inputHandler.getMousePos().x,game.inputHandler.getMousePos().y)) {
            return true;
        }
        return false;
    }

    // get and set methods
    public Texture getTexture () { return texture; }
    public void setTexture (Texture texture) {
        this.texture = texture;
    }
    public Rectangle getRect () {
        return this.rect;
    }
    public void setRect () {
        this.rect.set(getX(),getY(),getH(),getW());
    }
    public void setClickeable(boolean clickeable) { this.isClickeable = clickeable; }
    public void setDraggeable(boolean draggeable) { this.isDraggeable = draggeable; }
    public boolean isDragging () { return this.isDragging; }
    public void setDragging (boolean isDragging) { this.isDragging = isDragging; }
}
