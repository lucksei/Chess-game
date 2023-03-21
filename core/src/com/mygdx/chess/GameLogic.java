package com.mygdx.chess;

import com.badlogic.gdx.utils.Array;

import java.util.HashMap;
import java.util.Map;

public class GameLogic {
    private Chess game;
    private Array<MoveLog> moveHistory;
    private BoardState currentBoardState;

    public GameLogic(Chess game) {
        this.game = game;
        moveHistory = new Array<>();
        this.currentBoardState = new BoardState(game);
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

    public void capturePiece(BoardState boardState, int gridX, int gridY) {
        // check if the move is also a capture move, in that case remove the enemy piece
        Square square = new Square(gridX, gridY);
        if (!square.isEmpty(boardState)) {
            ChessPiece dummy = boardState.getFromSquare(square).first();
            boardState.getOriginal(dummy).remove();
        }
    }

    // movement strategy TODO move this to his own class

    public static Array<Square> getPieceMovement(BoardState boardState, ChessPiece chessPiece) {
        switch (chessPiece.getType()) {
            case BISHOP:
                return bishopMovement(boardState, chessPiece);
            case PAWN:
                if (chessPiece.getPlayer() == ChessPiece.Player.WHITE)
                    return whitePawnMovement(boardState, chessPiece);
                else
                    return whitePawnMovement(boardState, chessPiece);
            case QUEEN:
                return queenMovement(boardState, chessPiece);
            default:
                return kingMovement(boardState, chessPiece);
        }
    }
    public static Array<Square> whitePawnMovement(BoardState boardState, ChessPiece chessPiece) {
        Map<String, Square> moves = new HashMap<>();
        moves.put("moveup", new Square(chessPiece.getGridX(), chessPiece.getGridY()+1));
        moves.put("moveupup", new Square(chessPiece.getGridX(), chessPiece.getGridY()+2));
        moves.put("moveupleft", new Square(chessPiece.getGridX()-1,chessPiece.getGridY()+1));
        moves.put("moveupright", new Square(chessPiece.getGridX()+1,chessPiece.getGridY()+1));

        Array<Square> legalMoves = new Array<>();

        // move one square up
        if(moves.get("moveup").isInBounds() && moves.get("moveup").isEmpty(boardState)) {
            legalMoves.add(moves.get("moveup"));

            // move two squares up
            if(chessPiece.getGridY() == 1 && moves.get("moveupup").isEmpty(boardState)) {
                legalMoves.add(moves.get("moveupup"));
            }
        }
        // capture moves
        if (moves.get("moveupleft").isEnemy(boardState, chessPiece)) {
            legalMoves.add(moves.get("moveupleft"));
        }
        if (moves.get("moveupright").isEnemy(boardState, chessPiece)) {
            legalMoves.add(moves.get("moveupright"));
        }

        return legalMoves;
    }
    public static Array<Square> blackPawnMovement(BoardState boardState, ChessPiece chessPiece) {
        return  null;
    }
    public static Array<Square> rookMovement(BoardState boardState, ChessPiece chessPiece) {
        Array<Square> legalMoves = new Array<>();
        legalMoves.addAll(genericMovement(boardState, chessPiece, -1, 0)); // check left
        legalMoves.addAll(genericMovement(boardState, chessPiece, +1, 0)); // check right
        legalMoves.addAll(genericMovement(boardState, chessPiece, 0, 1)); // check up
        legalMoves.addAll(genericMovement(boardState, chessPiece, 0, -1)); // check down
        return legalMoves;
    }
    public static Array<Square> knightMovement(BoardState boardState, ChessPiece chessPiece) {
        Array<Square> legalMoves = new Array<>();
        legalMoves.addAll(genericMovement(boardState, chessPiece, -1, 2, 1)); // up-up-left
        legalMoves.addAll(genericMovement(boardState, chessPiece, 1, 2, 1)); // up-up-right
        legalMoves.addAll(genericMovement(boardState, chessPiece, -1, -2, 1)); // down-down-left
        legalMoves.addAll(genericMovement(boardState, chessPiece, 1, -2, 1)); // down-down-right
        legalMoves.addAll(genericMovement(boardState, chessPiece, 2, 1, 1)); // right-right-up
        legalMoves.addAll(genericMovement(boardState, chessPiece, 2, -1, 1)); // right-right-down
        legalMoves.addAll(genericMovement(boardState, chessPiece, -2, 1, 1)); // left-left-up
        legalMoves.addAll(genericMovement(boardState, chessPiece, -2, -1, 1)); // left-left-down
        return legalMoves;
    }
    public static Array<Square> bishopMovement(BoardState boardState, ChessPiece chessPiece) {
        Array<Square> legalMoves = new Array<>();
        legalMoves.addAll(genericMovement(boardState, chessPiece, -1, 1)); // check up-left
        legalMoves.addAll(genericMovement(boardState, chessPiece, 1, 1)); // check up-right
        legalMoves.addAll(genericMovement(boardState, chessPiece, -1, -1)); // check down-left
        legalMoves.addAll(genericMovement(boardState, chessPiece, 1, -1)); // check down-right
        return legalMoves;
    }
    public static Array<Square> queenMovement(BoardState boardState, ChessPiece chessPiece) {
        Array<Square> legalMoves = new Array<>();
        legalMoves.addAll(genericMovement(boardState, chessPiece, -1, 0)); // check left
        legalMoves.addAll(genericMovement(boardState, chessPiece, +1, 0)); // check right
        legalMoves.addAll(genericMovement(boardState, chessPiece, 0, 1)); // check up
        legalMoves.addAll(genericMovement(boardState, chessPiece, 0, -1)); // check down
        legalMoves.addAll(genericMovement(boardState, chessPiece, -1, 1)); // check up-left
        legalMoves.addAll(genericMovement(boardState, chessPiece, 1, 1)); // check up-right
        legalMoves.addAll(genericMovement(boardState, chessPiece, -1, -1)); // check down-left
        legalMoves.addAll(genericMovement(boardState, chessPiece, 1, -1)); // check down-right
        return legalMoves;
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

    // get and set methods

    public BoardState getCurrentBoardState () {
        return this.currentBoardState;
    }
    public void updateCurrentBoardState() {
        currentBoardState.updateCurrentState();
    }
}

