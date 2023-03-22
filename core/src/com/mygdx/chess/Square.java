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
        return (boardState.getFromSquare(this) == null);
    }
    // checks if the current square in a certain gamestate is an enemy to the current chess piece
    public boolean isEnemy (BoardState boardState, ChessPiece chessPiece) {
        if (!this.isEmpty(boardState) &&
                chessPiece.getPlayer() != boardState.getFromSquare(this).getPlayer()) {
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
    public boolean isUnderAttack (BoardState boardState, ChessPiece king) {
        for (ChessPiece chessPiece : boardState.getPieces()) {
            if (chessPiece.getPlayer() != king.getPlayer()) {
                for (Square square : GameLogic.getPieceMovement(boardState, chessPiece)) {
                    if(square.getGridX() == king.getGridX() && square.getGridY() == king.getGridY()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public boolean isUnderAttack (BoardState boardState, Square squareUnderAttack, ChessPiece.Player color) {
        for (ChessPiece chessPiece : boardState.getPieces()) {
            if (chessPiece.getPlayer() != color) {
                for (Square square : GameLogic.getPieceMovement(boardState, chessPiece)) {
                    if(square.getGridX() == squareUnderAttack.getGridX() && square.getGridY() == squareUnderAttack.getGridY()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public MoveIndicator hasMoveIndicator (SceneEntities sceneEntities) {
        Array<MoveIndicator> temp = sceneEntities.findEntities(MoveIndicator.class);
        for (MoveIndicator moveIndicator : temp) {
            if (moveIndicator.getGridX() == this.gridX && moveIndicator.getGridY() == this.gridY) {
                return moveIndicator;
            }
        }
        return null;
    }
}
