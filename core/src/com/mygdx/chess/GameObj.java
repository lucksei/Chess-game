package com.mygdx.chess;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class GameObj {

    static final int SQUARE_SIZE = 42;

    Chess game;
    private int x, y;
    private Texture myTexture;

    private Rectangle rect;

    public GameObj (Chess game, int x, int y) {
        this.game = game;
        this.x = x;
        this.y = y;
        rect = new Rectangle(x*SQUARE_SIZE,y*SQUARE_SIZE,SQUARE_SIZE,SQUARE_SIZE);
        setTexture(game.textureList.get(TextureList.Key.ERR));
    }

    // TODO very inneficent, clean this up for bigger game
    public void setTexture (Texture texture) {
        myTexture = texture;
    }

    public void render () {
        game.batch.draw(myTexture,x*SQUARE_SIZE,y*SQUARE_SIZE);
    }

    public void update () {
        rect.setPosition(x*SQUARE_SIZE,y*SQUARE_SIZE); // keeps the rect updated...
    }

    public int getX () {return x;}
    public int getY () {return y;}
    public void setX (int x) {this.x = x;}
    public void setY (int y) {this.y = y;}

    public Rectangle getRect() {return rect;}

}
