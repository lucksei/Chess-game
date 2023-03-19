package com.mygdx.chess;

import com.badlogic.gdx.utils.Array;

public class GameLogic {
    private Chess game;
    private Array<MoveLog> moveHistory;

    public GameLogic (Chess game) {
        this.game = game;
        moveHistory = new Array<>();
    }

    /**
     * Undoes the last move made in the game.
     */
    public void undo () {
        if(!moveHistory.isEmpty()) {
            MoveLog lastMoveLog = moveHistory.get(moveHistory.size - 1);
            lastMoveLog.getChessPiece().setGridX(lastMoveLog.getOldGridX());
            lastMoveLog.getChessPiece().setGridY(lastMoveLog.getOldGridY());
            if(lastMoveLog.getChessPieceCaptured() != null) {
                game.sceneEntities.addEntityToScene(lastMoveLog.getChessPieceCaptured());
            }
            // Remove the last move from moveHistory
            moveHistory.removeIndex(moveHistory.size - 1);
        }
    }


    public void redo () {

    }

    public void logNewMove(MoveLog moveLog) {
        this.moveHistory.add(moveLog);
    }
}
