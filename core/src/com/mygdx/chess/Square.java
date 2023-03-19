package com.mygdx.chess;

import com.badlogic.gdx.utils.Array;

/* tool to check the properties of a certain square */
public class Square extends BoardEntity {
    public Square (Chess game, int gridX, int gridY) {
        super(game, gridX, gridY);
    }

    private Array<ChessPiece> getChessPieces (Array<BoardEntity> entities) {
        Array<ChessPiece> chessPieces = new Array<>();
        for (BoardEntity entity : entities) {
            if (entity instanceof ChessPiece) chessPieces.add((ChessPiece) entity);
        }
        return chessPieces;
    }
    private Array<BoardEntity> getBoardEntities () {
        return game.sceneEntities.getFromSquare(gridX, gridY);
    }
    public ChessPiece getChessPiece () {
        return getChessPieces(getBoardEntities()).first();
    }
    public boolean isEmpty(){
        return getChessPieces(getBoardEntities()).isEmpty();
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
    public boolean isUnderAttack (ChessPiece king) {
        for (ChessPiece chessPiece : game.sceneEntities.findEntities(ChessPiece.class)) {
            if (chessPiece.getPlayer() != king.getPlayer()) {
                for (Square square : chessPiece.getLegalMovesNoCheck()) {
                    if(square.getGridX() == king.getGridX() && square.getGridY() == king.getGridY()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public boolean hasMoveIndicator () {
        for (BoardEntity entity : getBoardEntities()) {
            if (entity instanceof MoveIndicator) return true;
        }
        return false;
    }
}
