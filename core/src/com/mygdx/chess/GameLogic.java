package com.mygdx.chess;

import com.badlogic.gdx.utils.Array;

import java.util.HashMap;
import java.util.Map;

public class GameLogic {
    private Chess game;
    private BoardState currentBoardState;
    private Array<MoveLog> moveHistory;
    private ChessPiece.Player turn;


    public GameLogic(Chess game) {
        this.game = game;
        this.currentBoardState = new BoardState(game).storeCurrentState();
        moveHistory = new Array<>();
        turn = ChessPiece.Player.WHITE;
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

    // game states (win,lose,draw) and turns
    public void nextTurn() {
        if(turn == ChessPiece.Player.WHITE) {
            turn = ChessPiece.Player.BLACK;
        } else {
            turn = ChessPiece.Player.WHITE;
        }
        for (ChessPiece chessPiece : currentBoardState.getPieces()) {
            chessPiece.setTurn(chessPiece.getPlayer() == turn);
        }
    }

    // board states and chess piece magery
    public ChessPiece capturePiece(BoardState boardState, int gridX, int gridY) {
        // check if the move is also a capture move, in that case remove the enemy piece
        Square square = new Square(gridX, gridY);
        ChessPiece capturedPiece = boardState.getFromSquare(square);
        if (capturedPiece != null) {
            capturedPiece.remove(); // remove it from the Scene Entities
            boardState.remove(capturedPiece); // remove it from the board state
        }
        return capturedPiece;
    }
    public BoardState getCurrentBoardState () {
        return this.currentBoardState;
    }
    public void storeCurrentBoardState() {
        this.currentBoardState.storeCurrentState();
    }
    /**
     * Determines the legal moves for a chess piece to prevent the king from being in check.
     *
     * @param chessPiece The chess piece to move.
     * @param king The king to protect.
     * @return An Array of Squares representing legal moves that will not leave the king in check.
     */
    public Array<Square> checkForCheckAlg (ChessPiece chessPiece, ChessPiece king) {
        // Create a new array to hold the legal moves that will not leave the king in check
        Array<Square> KingNotUnderAttack = new Array<>();

        // Create a copy of the current game state
        BoardState futureBoardState = new BoardState(game);

        // For each possible move the chess piece can make...
        for (Square legalMove : getPieceMovement(getCurrentBoardState(), chessPiece)) {
            // Create a copy of the board state, move the dummy piece to the hypothetical square
            futureBoardState.copyCurrentState(); // copy the current board state
            ChessPiece dummy = futureBoardState.getFromSquare(new Square(chessPiece.getGridX(), chessPiece.getGridY()));
            ChessPiece dummyKing = futureBoardState.getFromSquare(new Square(king.getGridX(), king.getGridY()));
            dummy.movePiece(futureBoardState, legalMove.getGridX(), legalMove.getGridY());

            // If the king square is not under attack after the move, add the legal move to the array
            if (!legalMove.isUnderAttack(futureBoardState, dummyKing)) {
                KingNotUnderAttack.add(legalMove);
            }
        }
        // Return the array of legal moves
        return KingNotUnderAttack;
    }

    // movement strategy TODO move this to his own class
    public static boolean checkCastlingLeft(BoardState boardState, ChessPiece king) {
        int gridY = (king.getPlayer() == ChessPiece.Player.WHITE) ? 0 : 7;
        Square kingSquare = new Square(4,gridY);
        Square leftRookSquare = new Square(0,gridY);
        ChessPiece leftRook = boardState.getFromSquare(leftRookSquare);
        if(!king.wasMoved() && leftRook != null && leftRook.getType() == ChessPiece.Type.ROOK && !leftRook.wasMoved()) {
            if(new Square(3,gridY).isEmpty(boardState) && new Square(2,gridY).isEmpty(boardState) && new Square(1,gridY).isEmpty(boardState)) {
                // check if any of the squares the king passes through are under attack
                Square[] checkSquares = {new Square(4,gridY), new Square(3,gridY), new Square(2,gridY), new Square(1,gridY)};
                for (Square square : checkSquares) {
                    if (square.isUnderAttack(boardState,square,king.getPlayer())) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }
    public static boolean checkCastlingRight(BoardState boardState, ChessPiece king) {
        int gridY = (king.getPlayer() == ChessPiece.Player.WHITE) ? 0 : 7;
        Square kingSquare = new Square(4,gridY);
        Square rightRookSquare = new Square(7,gridY);
        ChessPiece rightRook = boardState.getFromSquare(rightRookSquare);
        if(!king.wasMoved() && rightRook != null && rightRook.getType() == ChessPiece.Type.ROOK && !rightRook.wasMoved()) {
            if(new Square(5,gridY).isEmpty(boardState) && new Square(6,gridY).isEmpty(boardState)) {
                // check if any of the squares the king passes through are under attack
                Square[] checkSquares = {new Square(4,gridY), new Square(5,gridY), new Square(6,gridY)};
                for (Square square : checkSquares) {
                    if (square.isUnderAttack(boardState,square,king.getPlayer())) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }
    public static Array<Square> getPieceMovement(BoardState boardState, ChessPiece chessPiece) {
        switch (chessPiece.getType()) {
            case PAWN:
                if (chessPiece.getPlayer() == ChessPiece.Player.WHITE)
                    return whitePawnMovement(boardState, chessPiece);
                else
                    return blackPawnMovement(boardState, chessPiece);
            case ROOK:
                return rookMovement(boardState, chessPiece);
            case KNIGHT:
                return knightMovement(boardState, chessPiece);
            case BISHOP:
                return bishopMovement(boardState, chessPiece);
            case QUEEN:
                return queenMovement(boardState, chessPiece);
            case KING:
                return kingMovement(boardState, chessPiece);
            default:
                return kingMovement(boardState, chessPiece);
        }
    }
    private static Array<Square> whitePawnMovement(BoardState boardState, ChessPiece chessPiece) {
        Square moveUp = new Square(chessPiece.getGridX(), chessPiece.getGridY()+1);
        Square moveUpUp = new Square(chessPiece.getGridX(), chessPiece.getGridY()+2);
        Square moveUpLeft = new Square(chessPiece.getGridX()-1,chessPiece.getGridY()+1);
        Square moveUpRight = new Square(chessPiece.getGridX()+1,chessPiece.getGridY()+1);

        Array<Square> legalMoves = new Array<>();

        // move one square up
        if(moveUp.isInBounds() && moveUp.isEmpty(boardState)) {
            legalMoves.add(moveUp);

            // move two squares up
            if(chessPiece.getGridY() == 1 && moveUpUp.isEmpty(boardState)) {
                legalMoves.add(moveUpUp);
            }
        }
        // capture moves
        if (moveUpLeft.isEnemy(boardState, chessPiece)) {
            legalMoves.add(moveUpLeft);
        }
        if (moveUpRight.isEnemy(boardState, chessPiece)) {
            legalMoves.add(moveUpRight);
        }

        return legalMoves;
    }
    private static Array<Square> blackPawnMovement(BoardState boardState, ChessPiece chessPiece) {
        Square moveDown = new Square(chessPiece.getGridX(), chessPiece.getGridY()-1);
        Square moveDownDown = new Square(chessPiece.getGridX(), chessPiece.getGridY()-2);
        Square moveDownLeft = new Square(chessPiece.getGridX()-1,chessPiece.getGridY()-1);
        Square moveDownRight = new Square(chessPiece.getGridX()+1,chessPiece.getGridY()-1);

        Array<Square> legalMoves = new Array<>();

        // move one square down
        if(moveDown.isInBounds() && moveDown.isEmpty(boardState)) {
            legalMoves.add(moveDown);

            // move two squares down
            if(chessPiece.getGridY() == 6 && moveDownDown.isEmpty(boardState)) {
                legalMoves.add(moveDownDown);
            }
        }
        // capture moves
        if (moveDownLeft.isEnemy(boardState, chessPiece)) {
            legalMoves.add(moveDownLeft);
        }
        if (moveDownRight.isEnemy(boardState, chessPiece)) {
            legalMoves.add(moveDownRight);
        }

        return legalMoves;
    }
    private static Array<Square> rookMovement(BoardState boardState, ChessPiece chessPiece) {
        Array<Square> legalMoves = new Array<>();
        legalMoves.addAll(genericMovement(boardState, chessPiece, -1, 0)); // check left
        legalMoves.addAll(genericMovement(boardState, chessPiece, +1, 0)); // check right
        legalMoves.addAll(genericMovement(boardState, chessPiece, 0, 1)); // check up
        legalMoves.addAll(genericMovement(boardState, chessPiece, 0, -1)); // check down
        return legalMoves;
    }
    private static Array<Square> knightMovement(BoardState boardState, ChessPiece chessPiece) {
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
    private static Array<Square> bishopMovement(BoardState boardState, ChessPiece chessPiece) {
        Array<Square> legalMoves = new Array<>();
        legalMoves.addAll(genericMovement(boardState, chessPiece, -1, 1)); // check up-left
        legalMoves.addAll(genericMovement(boardState, chessPiece, 1, 1)); // check up-right
        legalMoves.addAll(genericMovement(boardState, chessPiece, -1, -1)); // check down-left
        legalMoves.addAll(genericMovement(boardState, chessPiece, 1, -1)); // check down-right
        return legalMoves;
    }
    private static Array<Square> queenMovement(BoardState boardState, ChessPiece chessPiece) {
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
    private static Array<Square> kingMovement(BoardState boardState, ChessPiece chessPiece) {
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
    private static Array<Square> genericMovement(BoardState boardState, ChessPiece chessPiece, int dirX, int dirY) {
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
    private static Array<Square> genericMovement(BoardState boardState, ChessPiece chessPiece, int dirX, int dirY, int maxIterations) {
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

