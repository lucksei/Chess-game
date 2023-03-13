package com.mygdx.chess;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import java.util.HashMap;
import java.util.Map;

public class TextureList {

    public enum Key{ BOARD, ERR, BISHOP, KING, KNIGHT, PAWN, QUEEN, ROOK, INDICATOR, }
    private Map<Key, Texture> textureMap;

    public TextureList () {
        textureMap = new HashMap<>();
        set(Key.BOARD, "board.png");
        set(Key.ERR, "err.png");
        set(Key.BISHOP, "bishop.png");
        set(Key.KING, "king.png");
        set(Key.KNIGHT, "knight.png");
        set(Key.PAWN, "pawn.png");
        set(Key.QUEEN, "queen.png");
        set(Key.ROOK, "rook.png");
        set(Key.INDICATOR, "indicator.png");
    }

    public void set (Key key, String filepath) {
        if(textureMap.containsKey(key)){
            Gdx.app.log("TextureList", "Overwriting texture for key " + key);
            dispose(key);
        }
        textureMap.put(key, new Texture(filepath));
    }
    public Texture get (Key key) {
        return textureMap.get(key);
    }
    public void dispose (Key key) {
        textureMap.get(key).dispose();
    }
    public void disposeAll () {
        for (Key key  : textureMap.keySet()) {
            this.dispose(key);
        }
    }
}
