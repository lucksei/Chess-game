package com.mygdx.chess;

import com.badlogic.gdx.utils.Array;

import java.util.HashMap;
import java.util.Map;

public class MovementStrategy {
    public static Array<Square> getLegalMoves(ChessPiece chessPiece) {
        switch(chessPiece.getType()){
            case BISHOP:
                return bishopMovement(chessPiece);
            case KING:
                return kingMovement(chessPiece);
            case KNIGHT:
                return knightMovement(chessPiece);
            case PAWN:
                if(chessPiece.getPlayer() == ChessPiece.Player.WHITE)
                    return whitePawnMovement(chessPiece);
                else
                    return blackPawnMovement(chessPiece);
            case QUEEN:
                return queenMovement(chessPiece);
            case ROOK:
                return rookMovement(chessPiece);
            default:
                return null;
        }

    }
    public static Array<Square> whitePawnMovement(ChessPiece chessPiece) {

        Map<String, Square> moves = new HashMap<>();
        moves.put("moveup", new Square(chessPiece.getGame(), chessPiece.getGridX(), chessPiece.getGridY()+1));
        moves.put("moveupup", new Square(chessPiece.getGame(), chessPiece.getGridX(), chessPiece.getGridY()+2));
        moves.put("moveupleft", new Square(chessPiece.getGame(), chessPiece.getGridX()-1,chessPiece.getGridY()+1));
        moves.put("moveupright", new Square(chessPiece.getGame(), chessPiece.getGridX()+1,chessPiece.getGridY()+1));

        Array<Square> legalMoves = new Array<>();

        // move one square up
        if(moves.get("moveup").isInBounds() && moves.get("moveup").isEmpty()) {
            legalMoves.add(moves.get("moveup"));

            // move two squares up
            if(chessPiece.getGridY() == 1 && moves.get("moveupup").isEmpty()) {
                legalMoves.add(moves.get("moveupup"));
            }
        }
        // capture moves
        if (moves.get("moveupleft").isEnemy(chessPiece.getPlayer())) {
            legalMoves.add(moves.get("moveupleft"));
        }
        if (moves.get("moveupright").isEnemy(chessPiece.getPlayer())) {
            legalMoves.add(moves.get("moveupright"));
        }

        chessPiece.getGame().sceneEntities.removeEntityFromScene(Square.class);
        return legalMoves;
    }
    public static Array<Square> blackPawnMovement(ChessPiece chessPiece) {

        Map<String, Square> moves = new HashMap<>();
        moves.put("movedown", new Square(chessPiece.getGame(), chessPiece.getGridX(), chessPiece.getGridY()-1));
        moves.put("movedowndown", new Square(chessPiece.getGame(), chessPiece.getGridX(), chessPiece.getGridY()-2));
        moves.put("movedownleft", new Square(chessPiece.getGame(), chessPiece.getGridX()-1,chessPiece.getGridY()-1));
        moves.put("movedownright", new Square(chessPiece.getGame(), chessPiece.getGridX()+1,chessPiece.getGridY()-1));

        Array<Square> legalMoves = new Array<>();

        // move one square down
        if(moves.get("movedown").isInBounds() && moves.get("movedown").isEmpty()) {
            legalMoves.add(moves.get("movedown"));

            // move two squares down
            if(chessPiece.getGridY() == 6 && moves.get("movedowndown").isEmpty()) {
                legalMoves.add(moves.get("movedowndown"));
            }
        }
        // capture moves
        if (moves.get("movedownleft").isEnemy(chessPiece.getPlayer())) {
            legalMoves.add(moves.get("movedownleft"));
        }
        if (moves.get("movedownright").isEnemy(chessPiece.getPlayer())) {
            legalMoves.add(moves.get("movedownright"));
        }

        chessPiece.getGame().sceneEntities.removeEntityFromScene(Square.class);
        return legalMoves;
    }
    public static Array<Square> rookMovement(ChessPiece chessPiece) {
        Array<Square> legalMoves = new Array<>();
        legalMoves.addAll(genericMovement(chessPiece, -1, 0)); // check left
        legalMoves.addAll(genericMovement(chessPiece, +1, 0)); // check right
        legalMoves.addAll(genericMovement(chessPiece, 0, 1)); // check up
        legalMoves.addAll(genericMovement(chessPiece, 0, -1)); // check down
        return legalMoves;
    }
    public static Array<Square> knightMovement(ChessPiece chessPiece) {
        Array<Square> legalMoves = new Array<>();
        legalMoves.addAll(genericMovement(chessPiece, -1, 2, 1)); // up-up-left
        legalMoves.addAll(genericMovement(chessPiece, 1, 2, 1)); // up-up-right
        legalMoves.addAll(genericMovement(chessPiece, -1, -2, 1)); // down-down-left
        legalMoves.addAll(genericMovement(chessPiece, 1, -2, 1)); // down-down-right
        legalMoves.addAll(genericMovement(chessPiece, 2, 1, 1)); // right-right-up
        legalMoves.addAll(genericMovement(chessPiece, 2, -1, 1)); // right-right-down
        legalMoves.addAll(genericMovement(chessPiece, -2, 1, 1)); // left-left-up
        legalMoves.addAll(genericMovement(chessPiece, -2, -1, 1)); // left-left-down
        return legalMoves;
    }
    public static Array<Square> bishopMovement(ChessPiece chessPiece) {
        Array<Square> legalMoves = new Array<>();
        legalMoves.addAll(genericMovement(chessPiece, -1, 1)); // check up-left
        legalMoves.addAll(genericMovement(chessPiece, 1, 1)); // check up-right
        legalMoves.addAll(genericMovement(chessPiece, -1, -1)); // check down-left
        legalMoves.addAll(genericMovement(chessPiece, 1, -1)); // check down-right
        return legalMoves;
    }
    public static Array<Square> queenMovement(ChessPiece chessPiece) {
        Array<Square> legalMoves = new Array<>();
        legalMoves.addAll(genericMovement(chessPiece, -1, 0)); // check left
        legalMoves.addAll(genericMovement(chessPiece, +1, 0)); // check right
        legalMoves.addAll(genericMovement(chessPiece, 0, 1)); // check up
        legalMoves.addAll(genericMovement(chessPiece, 0, -1)); // check down
        legalMoves.addAll(genericMovement(chessPiece, -1, 1)); // check up-left
        legalMoves.addAll(genericMovement(chessPiece, 1, 1)); // check up-right
        legalMoves.addAll(genericMovement(chessPiece, -1, -1)); // check down-left
        legalMoves.addAll(genericMovement(chessPiece, 1, -1)); // check down-right
        return legalMoves;
    }
    public static Array<Square> kingMovement(ChessPiece chessPiece) {
        Array<Square> legalMoves = new Array<>();
        legalMoves.addAll(genericMovement(chessPiece, -1, 0,  1)); // check left
        legalMoves.addAll(genericMovement(chessPiece, +1, 0,  1)); // check right
        legalMoves.addAll(genericMovement(chessPiece, 0, 1,  1)); // check up
        legalMoves.addAll(genericMovement(chessPiece, 0, -1,  1)); // check down
        legalMoves.addAll(genericMovement(chessPiece, -1, 1, 1)); // check up-left
        legalMoves.addAll(genericMovement(chessPiece, 1, 1, 1)); // check up-right
        legalMoves.addAll(genericMovement(chessPiece, -1, -1, 1)); // check down-left
        legalMoves.addAll(genericMovement(chessPiece, 1, -1,1)); // check down-right

        // since the king cant be captured, this has to be searched
        Array<Square> legalMovesNotUnderAttack = new Array<>();
        for (Square legalMove : legalMoves) {
            if (!legalMove.isUnderAttack(chessPiece.getPlayer())) legalMovesNotUnderAttack.add(legalMove);
        }
        return legalMovesNotUnderAttack;
    }
    public static Array<Square> genericMovement(ChessPiece chessPiece, int dirX, int dirY) {
        Array<Square> legalMoves = new Array<>();
        Square leftSquare = new Square(chessPiece.getGame(), chessPiece.getGridX() + dirX, chessPiece.gridY + dirY);

        int maxIterations = 25;
        int i = 0;
        while (leftSquare.isInBounds() && leftSquare.isEmpty() && i < maxIterations) {
            legalMoves.add(leftSquare);
            leftSquare = new Square(chessPiece.getGame(), leftSquare.getGridX() + dirX, leftSquare.getGridY() + dirY);
            i++;
        }
        if (leftSquare.isEnemy(chessPiece.getPlayer()) && i < maxIterations) {
            legalMoves.add(leftSquare);
        }
        return legalMoves;
}
    public static Array<Square> genericMovement(ChessPiece chessPiece, int dirX, int dirY, int maxIterations) {
        Array<Square> legalMoves = new Array<>();
        Square leftSquare = new Square(chessPiece.getGame(), chessPiece.getGridX() + dirX, chessPiece.gridY + dirY);

        int i = 0;
        while (leftSquare.isInBounds() && leftSquare.isEmpty() && i < maxIterations) {
            legalMoves.add(leftSquare);
            leftSquare = new Square(chessPiece.getGame(), leftSquare.getGridX() + dirX, leftSquare.getGridY() + dirY);
            i++;
        }
        if (leftSquare.isEnemy(chessPiece.getPlayer()) && i < maxIterations) {
            legalMoves.add(leftSquare);
        }
        return legalMoves;
    }
}

