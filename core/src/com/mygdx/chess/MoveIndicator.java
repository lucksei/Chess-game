package com.mygdx.chess;

public class MoveIndicator extends Entity {

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
            Square currentSquare = new Square(game.chessScene.getEntitiesCopy(), this.getX(), this.getY(), 0, 0);
            if(!currentSquare.isEmpty() ) {
                game.chessScene.removeEntity(currentSquare.getChessPiece());
            }

            game.chessScene.movePiece(chessPiece, getX(), getY());
            game.chessScene.removeEntity(MoveIndicator.class);
        }
    }
}
