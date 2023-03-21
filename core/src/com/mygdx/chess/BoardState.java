package com.mygdx.chess;

import com.badlogic.gdx.utils.Array;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class BoardState {
    private Chess game;
    private Map<ChessPiece, ChessPiece> boardState;

    public BoardState (Chess game) {
        this.game = game;
        this.boardState = new HashMap<>();
    }
    public void copyCurrentState () {
        for (ChessPiece original : this.game.sceneEntities.findEntities(ChessPiece.class)) {
            ChessPiece dummy = new ChessPiece(game, original.getGridX(), original.gridY, original.getType(), original.getPlayer());
            dummy.remove();
            boardState.put(original,dummy);
        }
    }
    public void updateCurrentState () {
        // removes old pieces and updates existing ones
        Array<ChessPiece> toRemove = new Array<ChessPiece>();
        for (ChessPiece original : boardState.keySet()) {
            if (game.sceneEntities.contains(original)) {
                boardState.get(original).setGridX(original.getGridX());
                boardState.get(original).setGridY(original.getGridY());
            } else {
                toRemove.add(original);
            }
        }

        // remove entities here
        for (ChessPiece original : toRemove) {
            boardState.remove(original);
        }

        // add new entry if there is a new chesspiece for some reason...
        for (ChessPiece chessPiece : game.sceneEntities.findEntities(ChessPiece.class)) {
            if (!boardState.containsKey(chessPiece)) {
                ChessPiece dummy = new ChessPiece(game, chessPiece.getGridX(), chessPiece.gridY, chessPiece.getType(), chessPiece.getPlayer());
                dummy.remove();
                boardState.put(chessPiece,dummy);
            }
        }

    }
    public Array<ChessPiece> getFromSquare(Square square) {
        Array<ChessPiece> chessPieces = new Array<>();
        for (ChessPiece dummy : boardState.values()) {
            if(dummy.getGridX() == square.getGridX() && dummy.getGridY() == square.getGridY()) {
                chessPieces.add(dummy);
            }
        }
        return chessPieces;
    }
    public ChessPiece getOriginal (ChessPiece dummy) {
        for (Map.Entry<ChessPiece, ChessPiece> entry : boardState.entrySet()) {
            if (Objects.equals(dummy, entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }
    public ChessPiece getDummy (ChessPiece original) {
        return boardState.get(original);
    }
}
