package com.mygdx.chess;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import java.util.HashMap;
import java.util.Map;

public class TextureList {

    public enum Key{ BOARD, ERR, BISHOP, BISHOP1, KING, KING1, KNIGHT, KNIGHT1, PAWN, PAWN1, QUEEN, QUEEN1, ROOK, ROOK1, INDICATOR }
    private Map<Key, Texture> textureMap;

    public TextureList () {
        textureMap = new HashMap<>();
        set(Key.BOARD, "board.png");
        set(Key.ERR, "err.png");
        set(Key.BISHOP, "bishop.png");
        set(Key.BISHOP1, "bishop1.png");
        set(Key.KING, "king.png");
        set(Key.KING1, "king1.png");
        set(Key.KNIGHT, "knight.png");
        set(Key.KNIGHT1, "knight1.png");
        set(Key.PAWN, "pawn.png");
        set(Key.PAWN1, "pawn1.png");
        set(Key.QUEEN, "queen.png");
        set(Key.QUEEN1, "queen1.png");
        set(Key.ROOK, "rook.png");
        set(Key.ROOK1, "rook1.png");
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
