package com.mygdx.chess;

import com.badlogic.gdx.utils.Array;

public class BoardState {
    private Chess game;
    private Array<ChessPiece> boardStateArray;

    public BoardState (Chess game) {
        this.game = game;
        this.boardStateArray = new Array<>();
    }
    public BoardState storeCurrentState() {
        boardStateArray.clear();
        for (ChessPiece chessPiece : this.game.sceneEntities.findEntities(ChessPiece.class)) {
            boardStateArray.add(chessPiece);
        }
        return this;
    }
    public BoardState copyCurrentState () {
        boardStateArray.clear();
        for (ChessPiece chessPiece : this.game.sceneEntities.findEntities(ChessPiece.class)) {
            ChessPiece chessPieceCopy = new ChessPiece(game, chessPiece.getGridX(), chessPiece.gridY, chessPiece.getType(), chessPiece.getPlayer());
            chessPieceCopy.remove();
            boardStateArray.add(chessPieceCopy);
        }
        return this;
    }
    public Array<ChessPiece> getPieces () {
        Array<ChessPiece> chessPieces = new Array<>();
        for (ChessPiece chessPiece : boardStateArray) {
            chessPieces.add(chessPiece);
        }
        return chessPieces;
    }
    public Array<ChessPiece> getFromSquare(Square square) {
        Array<ChessPiece> chessPieces = new Array<>();
        for (ChessPiece chessPiece : boardStateArray) {
            if(chessPiece.getGridX() == square.getGridX() && chessPiece.getGridY() == square.getGridY()) {
                chessPieces.add(chessPiece);
            }
        }
        return chessPieces;
    }

    public void remove(ChessPiece chessPiece) {
        boardStateArray.removeValue(chessPiece,true);
    }
}
