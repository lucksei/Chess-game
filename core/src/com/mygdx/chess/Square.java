package com.mygdx.chess;

import com.badlogic.gdx.utils.Array;

/* tool to check the properties of a certain square */
public class Square extends BoardEntity {
    private Array<ChessPiece> chessPieces;
    public Square (Chess game, int gridX, int gridY) {
        super(game, gridX, gridY);
        this.chessPieces = new Array<>();
        this.chessPieces = getFromSquare(getChessPieces(game.sceneEntities.getBoardEntities()));
    }

    private Array<ChessPiece> getChessPieces (Array<BoardEntity> entitiesCopy) {
        Array<ChessPiece> chessPieces = new Array<>();
        for (BoardEntity entity : entitiesCopy) {
            if (entity instanceof ChessPiece) chessPieces.add((ChessPiece) entity);
        }
        return chessPieces;
    }
    private Array<ChessPiece> getFromSquare (Array<ChessPiece> chessPieces) {
        Array<ChessPiece> chessPiecesInSquare = new Array<>();
        for (ChessPiece chessPiece : chessPieces) {
            if(chessPiece.getGridX() == this.gridX && chessPiece.getGridY() == this.gridY) chessPiecesInSquare.add(chessPiece);
        }
        return chessPiecesInSquare;
    }
    public ChessPiece getChessPiece () {
        if (!chessPieces.isEmpty()) {
            return chessPieces.first();
        } else {
            return null;
        }
    }
    public boolean isEmpty(){
        return chessPieces.isEmpty();
    }
    public boolean isEnemy (ChessPiece.Player color) {
        if (!isEmpty() && this.getChessPiece().getPlayer() != color) {
            return true;
        } else {
            return false;
        }
    }
    public boolean isInBounds () {
        if (this.getGridX() < 0 || this.getGridX() > 7 || this.getGridY() < 0 || this.getGridY() > 7) {
            return false;
        }
        return true;
    }
    public boolean isUnderAttack (ChessPiece.Player color) {
        for (ChessPiece chessPiece : game.sceneEntities.findEntities(ChessPiece.class)) {
            if (chessPiece.getPlayer() != color) {
                for (Square square : chessPiece.getLegalMoves()) {
                    if(square.getGridX() == this.gridX && square.getGridY() == this.gridY) return true;
                }
            }
        }
        return false;
    }
}
