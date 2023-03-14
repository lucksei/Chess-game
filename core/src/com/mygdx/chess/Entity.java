package com.mygdx.chess;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Entity {

    static final int SQUARE_SIZE = 42; //pixels

    Chess game;
    private Texture myTexture;
    private Rectangle rect;

    private String tag;
    private int x, y;
    private boolean isClickeable;

    public Entity(Chess game, int x, int y) {
        this.game = game;
        this.tag = null;
        this.x = x;
        this.y = y;
        this.isClickeable = false;
        this.rect = new Rectangle(x*SQUARE_SIZE,y*SQUARE_SIZE,SQUARE_SIZE,SQUARE_SIZE);
        setTexture(game.textureList.get(TextureList.Key.ERR));
    }

    public void setTag (String tag) { this.tag = tag; }

    public String getTag () { return tag; }

    public void setTexture (Texture texture) {
        myTexture = texture;
    }

    public void render () {
        game.batch.draw(myTexture,x*SQUARE_SIZE,y*SQUARE_SIZE);
    }

    public void update () {} // override this function

    public int getX () { return x; }
    public int getY () { return y; }
    public void setX (int x) { this.x = x; }
    public void setY (int y) { this.y = y; }

    public Rectangle getRect () {
        rect.setPosition(x*SQUARE_SIZE,y*SQUARE_SIZE); // Updates position first
        return rect;
    }

    public void setClickeable(boolean clickeable) { this.isClickeable = clickeable; }
    public boolean isClicked () {
        if(this.isClickeable && Gdx.input.justTouched() && this.getRect().contains(game.mousePos.x,game.mousePos.y)) {
            return true;
        }
        return false;
    }

}
