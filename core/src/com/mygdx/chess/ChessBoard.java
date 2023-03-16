package com.mygdx.chess;

public class ChessBoard extends Entity {

    public ChessBoard(Chess game) {
        super(game);
        setTexture(game.textureList.get(TextureList.Key.BOARD));
    }
}
