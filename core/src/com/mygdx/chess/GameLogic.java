package com.mygdx.chess;

import com.badlogic.gdx.utils.Array;

public class GameLogic {
    private Chess game;
    private Array<MoveLog> moveHistory;

    public GameLogic(Chess game) {
        this.game = game;
        moveHistory = new Array<>();
    }

    public void undo() {
        if (!moveHistory.isEmpty()) {
            MoveLog lastMoveLog = moveHistory.get(moveHistory.size - 1);
            lastMoveLog.getChessPiece().setGridX(lastMoveLog.getOldGridX());
            lastMoveLog.getChessPiece().setGridY(lastMoveLog.getOldGridY());
            if (lastMoveLog.getChessPieceCaptured() != null) {
                game.sceneEntities.addEntityToScene(lastMoveLog.getChessPieceCaptured());
            }
            // Remove the last move from moveHistory
            moveHistory.removeIndex(moveHistory.size - 1);
        }
    }

    public void logNewMove(MoveLog moveLog) {
        this.moveHistory.add(moveLog);
    }

    // movement strategy TODO move this to his own class

    public static Array<Square> getPieceMovement(BoardState boardState, ChessPiece chessPiece) {
        return  kingMovement(boardState, chessPiece);
    }
    public static Array<Square> whitePawnMovement(BoardState boardState, ChessPiece chessPiece) {
        return  null;
    }
    public static Array<Square> blackPawnMovement(BoardState boardState, ChessPiece chessPiece) {
        return  null;
    }
    public static Array<Square> rookMovement(BoardState boardState, ChessPiece chessPiece) {
        return  null;
    }
    public static Array<Square> knightMovement(BoardState boardState, ChessPiece chessPiece) {
        return  null;
    }
    public static Array<Square> bishopMovement(BoardState boardState, ChessPiece chessPiece) {
        return  null;
    }
    public static Array<Square> queenMovement(BoardState boardState, ChessPiece chessPiece) {
        return  null;
    }
    public static Array<Square> kingMovement(BoardState boardState, ChessPiece chessPiece) {
        Array<Square> legalMoves = new Array<>();
        legalMoves.addAll(genericMovement(boardState, chessPiece, -1, 0,  1)); // check left
        legalMoves.addAll(genericMovement(boardState, chessPiece, +1, 0,  1)); // check right
        legalMoves.addAll(genericMovement(boardState, chessPiece, 0, 1,  1)); // check up
        legalMoves.addAll(genericMovement(boardState, chessPiece, 0, -1,  1)); // check down
        legalMoves.addAll(genericMovement(boardState, chessPiece, -1, 1, 1)); // check up-left
        legalMoves.addAll(genericMovement(boardState, chessPiece, 1, 1, 1)); // check up-right
        legalMoves.addAll(genericMovement(boardState, chessPiece, -1, -1, 1)); // check down-left
        legalMoves.addAll(genericMovement(boardState, chessPiece, 1, -1,1)); // check down-right
        return legalMoves;
    }
    public static Array<Square> genericMovement(BoardState boardState, ChessPiece chessPiece, int dirX, int dirY) {
        Array<Square> legalMoves = new Array<>();
        Square square = new Square(chessPiece.getGridX() + dirX, chessPiece.getGridY() + dirY);

        int maxIterations = 25;
        int i = 0;
        while (square.isInBounds() && square.isEmpty(boardState) && i < maxIterations) {
            legalMoves.add(square);
            square = new Square(square.getGridX() + dirX, square.getGridY() + dirY);
            i++;
        }
        if (square.isEnemy(boardState, chessPiece) && i < maxIterations) {
            legalMoves.add(square);
        }
        return legalMoves;
    }
    public static Array<Square> genericMovement(BoardState boardState, ChessPiece chessPiece, int dirX, int dirY, int maxIterations) {
        Array<Square> legalMoves = new Array<>();
        Square square = new Square(chessPiece.getGridX() + dirX, chessPiece.getGridY() + dirY);

        int i = 0;
        while (square.isInBounds() && square.isEmpty(boardState) && i < maxIterations) {
            legalMoves.add(square);
            square = new Square(square.getGridX() + dirX, square.getGridY() + dirY);
            i++;
        }
        if (square.isEnemy(boardState, chessPiece) && i < maxIterations) {
            legalMoves.add(square);
        }
        return legalMoves;
    }
}

