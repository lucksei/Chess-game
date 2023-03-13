package com.mygdx.chess;

public class MoveIndicator extends GameObj {

    private Button button;
    ChessPiece chessPiece;

    public MoveIndicator(Chess game, ChessPiece chessPiece, int x, int y) {
        super(game, x, y);
        this.chessPiece = chessPiece;
        this.button = new Button(game);
        setTexture(game.textureList.get(TextureList.Key.INDICATOR));
    }
    @Override
    public void update () {
        super.update();
        if(button.click(getRect())) {
            game.chessScene.movePiece(chessPiece, getX()+1, getY());
        }
    }
}
