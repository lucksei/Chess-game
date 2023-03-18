package com.mygdx.chess;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class InputHandler {
    private Vector3 mousePos;
    public InputHandler () {
        mousePos = new Vector3();
    }

    public void update (OrthographicCamera camera) {
        mousePos.set(Gdx.input.getX(),Gdx.input.getY(), 0f);
        camera.unproject(mousePos);
    }

    // get and set methods
//    public Vector3 getMousePos () { return this.mousePos; }

    public Vector2 getMousePos () { return new Vector2(this.mousePos.x, this.mousePos.y); }

//    public boolean getMouseTouchDown () { return Gdx.input.isTouched(); }

}
