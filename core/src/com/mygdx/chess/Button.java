package com.mygdx.chess;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class Button {

    private Chess game;
    public Button (Chess game) {
        this.game = game;
    }

    public boolean click (Rectangle rect) {
        if(Gdx.input.justTouched()){

            // TODO move this to the main loop, ugly here
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            game.camera.unproject(touchPos);

            if(rect.contains(touchPos.x, touchPos.y)){
                return true;
            }
        }
        return false;
    }
}
