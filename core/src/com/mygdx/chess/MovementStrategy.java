package com.mygdx.chess;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public interface MovementStrategy {
    public Array<Vector2> movementStrategy (int x, int y);

}
