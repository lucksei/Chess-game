package com.mygdx.chess;

import com.badlogic.gdx.utils.Array;

public class GameLogic {
    private Chess game;
    private Array<Move> moveHistory;

    public GameLogic (Chess game) {
        this.game = game;
        moveHistory = new Array<>();
    }

    /**
     * Undoes the last move made in the game.
     */
    public void undo () {
        if(!moveHistory.isEmpty()) {
            Move lastMove = moveHistory.get(moveHistory.size - 1);
            lastMove.getChessPiece().setGridX(lastMove.getOldGridX());
            lastMove.getChessPiece().setGridY(lastMove.getOldGridY());
            if(lastMove.getChessPieceCaptured() != null) {
                game.sceneEntities.addEntityToScene(lastMove.getChessPieceCaptured());
            }
            // Remove the last move from moveHistory
            moveHistory.removeIndex(moveHistory.size - 1);
        }
    }


    public void redo () {

    }

    public void logNewMove(Move move) {
        this.moveHistory.add(move);
    }
}
