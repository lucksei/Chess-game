package com.mygdx.chess;

public class MoveIndicator extends GameObj {

    ChessPiece chessPiece;

    public MoveIndicator(Chess game, ChessPiece chessPiece, int x, int y) {
        super(game, x, y);
        setTag("moveIndicator");
        setClickeable(true);
        this.chessPiece = chessPiece;
        setTexture(game.textureList.get(TextureList.Key.INDICATOR));
    }
    @Override
    public void update () {
        if(isClicked()) {
            game.chessScene.movePiece(chessPiece, getX(), getY());
            game.chessScene.removeObject("moveIndicator");
        }
    }
}
