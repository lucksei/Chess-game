package com.mygdx.chess;

public class MoveLog {
//    GameLogic gameLogic;
    private ChessPiece chessPiece, chessPieceCaptured;
    private int oldGridX, oldGridY, newGridX, newGridY;

    public MoveLog(GameLogic gameLogic) {
//        this.gameLogic = gameLogic;
        gameLogic.logNewMove(this);
        this.chessPiece = null;
        this.chessPieceCaptured = null;
    }

    // get and set methods
    public void setChessPiece (ChessPiece chessPiece) {
        this.chessPiece = chessPiece;
        this.oldGridX = chessPiece.gridX;
        this.oldGridY = chessPiece.gridY;
    }
    public void setChessPieceCaptured (ChessPiece chessPieceCaptured) {
        this.chessPieceCaptured = chessPieceCaptured;
    }
    public ChessPiece getChessPiece () {
        return this.chessPiece;
    }
    public ChessPiece getChessPieceCaptured () {
        return this.chessPieceCaptured;
    }
    public void setOldPosition(int gridX, int gridY) {
        this.oldGridX = gridX;
        this.oldGridY = gridY;
    }
    public void setNewPosition(int gridX, int gridY) {
        this.newGridX = gridX;
        this.newGridY = gridY;
    }
    public int getOldGridX() {
        return this.oldGridX;
    }

    public int getOldGridY() {
        return this.oldGridY;
    }

    public int getNewGridX() {
        return this.newGridX;
    }

    public int getNewGridY() {
        return this.newGridY;
    }
}
