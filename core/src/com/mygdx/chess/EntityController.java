package com.mygdx.chess;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class EntityController extends Entity {

    private Entity parent;
    private Rectangle rect = new Rectangle();
    private Texture texture;
    private boolean isClickeable = false;
    private boolean isDraggeable = false;
    private boolean isClicked = false;
    private boolean isDragging = false;
    private boolean startDragging = false;
    private boolean endDragging = false;
    private Vector2 endDraggingPosition = null;
    private boolean isJustPressed = false;
    private int pressedX, pressedY;

    public EntityController(Chess game, Entity parent) {
        super(game);
        this.parent = parent;
        this.setW(parent.getW());
        this.setH(parent.getH());
        this.rect = new Rectangle();
        this.texture = game.textureList.get(TextureList.Key.ERR);
    }
    public void render () {
        game.batch.draw(this.texture,getX(),getY());
    }
    public void update () {
        // move controller to mouse position if is dragging
        if (!this.isDragging) {
            this.setX(parent.getX());
            this.setY(parent.getY());
        } else {
            this.setX((int) game.inputHandler.getMousePos().x - this.getW()/2);
            this.setY((int) game.inputHandler.getMousePos().y - this.getH()/2);
        }

        this.setRect();
        // check if left mouse button is down
        if ((isClickeable || isDraggeable) && Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) && this.getRect().contains(game.inputHandler.getMousePos())) {
            this.isJustPressed = true;
            this.isClicked = false;
            this.isDragging = false;
            this.startDragging = false;
            this.endDragging = false;
            this.endDraggingPosition = null;
            this.pressedX = (int) game.inputHandler.getMousePos().x;
            this.pressedY = (int) game.inputHandler.getMousePos().y;
        }
        // check if the player starts dragging the Entity
        if(isDraggeable) {
            if (isJustPressed && !(new Rectangle(pressedX - 5, pressedY - 5, 10, 10).contains(game.inputHandler.getMousePos()))) {
                this.isJustPressed = false;
                this.isDragging = true;
                this.startDragging = true;
            }
            if (isDragging && !Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
                this.isDragging = false;
                this.startDragging = false;
                this.endDragging = true;
                this.endDraggingPosition = game.inputHandler.getMousePos();
            }
        }
        // check if it was just a click (mouse button up)
        if (isClickeable && isJustPressed && !Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            this.isJustPressed = false;
            // on top of the Entity
            if (this.rect.contains(game.inputHandler.getMousePos())) {
                this.isClicked = true;
            }
        }
    }

    public boolean isClicked () {
        boolean temp = this.isClicked;
        this.isClicked = false;
        return temp;
    }
    public boolean startDragging() {
        boolean temp = this.startDragging;
        this.startDragging = false;
        return temp;
    }
    public boolean endDragging() {
        boolean temp = this.endDragging;
        this.endDragging = false;
        return temp;
    }
    public Vector2 getEndDraggingPosition() {
        return this.endDraggingPosition;
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
}
