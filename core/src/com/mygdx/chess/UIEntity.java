package com.mygdx.chess;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class UIEntity extends Entity {

    private int w, h;

    public UIEntity(Chess game, int x, int y, int w, int h) {
        super(game, x, y);
        this.w = w;
        this.h = h;
        this.setTag("UIEntity");
        this.setClickeable(true);
        this.setRect();
    }
    @Override
    public void setRect () {
        getRect().set(getX(),getY(), w, h);
    }

    @Override
    public void render () {
        game.batch.draw(getTexture(),getX(),getY());
    }

    @Override
    public void update () {
        if(isClicked()) {
            game.chessScene.removeEntity(this);
        }

    }
}
