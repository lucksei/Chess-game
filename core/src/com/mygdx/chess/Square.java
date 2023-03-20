package com.mygdx.chess;

import com.badlogic.gdx.utils.Array;

/* tool to check the properties of a certain square */
public class Square {
    private int gridX, gridY;
    public Square (int gridX, int gridY) {
        this.gridX = gridX;
        this.gridY = gridY;
    }

    // set and get methods

    public void setGridX(int gridX) {
        this.gridX = gridX;
    }
    public int getGridX() {
        return gridX;
    }
    public void setGridY(int gridY) {
        this.gridY = gridY;
    }
    public int getGridY() {
        return gridY;
    }

    public boolean isEmpty (BoardState boardState) {
        return boardState.getFromSquare(this).isEmpty();
    }
    public boolean isEnemy (BoardState boardState, ChessPiece chessPiece) {
        if (!boardState.getFromSquare(this).isEmpty() &&
                chessPiece.getPlayer() != boardState.getFromSquare(this).first().getPlayer()) {
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
/*    public boolean isUnderAttack (ChessPiece king) {
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
    }*/
/*    public boolean hasMoveIndicator () {
        Array<BoardEntity> temp = getBoardEntities();
        for (BoardEntity entity : temp) {
            if (entity instanceof MoveIndicator) return true;
        }
        return false;
    }*/
}
