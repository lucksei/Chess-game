package com.mygdx.chess;

import com.badlogic.gdx.utils.Array;

import java.util.HashMap;
import java.util.Map;

public class MovementStrategy {
    public static Array<Square> getLegalMoves(Chess game, int currentX, int currentY, ChessPiece.Player color, ChessPiece.Type type) {
        switch(type){
            case BISHOP:
                return bishopMovement(game, currentX, currentY, color);
            case KING:
                return kingMovement(game, currentX, currentY, color);
            case KNIGHT:
                return knightMovement(game, currentX, currentY, color);
            case PAWN:
                if(color == ChessPiece.Player.WHITE)
                    return whitePawnMovement(game, currentX, currentY, color);
                else
                    return blackPawnMovement(game, currentX, currentY, color);
            case QUEEN:
                return queenMovement(game, currentX, currentY, color);
            case ROOK:
                return rookMovement(game, currentX, currentY, color);
            default:
                return null;
        }

    }
    public static Array<Square> whitePawnMovement(Chess game, int currentX, int currentY, ChessPiece.Player color) {

        Map<String, Square> moves = new HashMap<>();
        moves.put("moveup", new Square(game, currentX,currentY+1));
        moves.put("moveupup", new Square(game, currentX,currentY+2));
        moves.put("moveupleft", new Square(game, currentX-1,currentY+1));
        moves.put("moveupright", new Square(game, currentX+1,currentY+1));

        Array<Square> legalMoves = new Array<>();

        // move one square up
        if(moves.get("moveup").isInBounds() && moves.get("moveup").isEmpty()) {
            legalMoves.add(moves.get("moveup"));

            // move two squares up
            if(currentY == 1 && moves.get("moveupup").isEmpty()) {
                legalMoves.add(moves.get("moveupup"));
            }
        }
        // capture moves
        if (moves.get("moveupleft").isEnemy(color)) {
            legalMoves.add(moves.get("moveupleft"));
        }
        if (moves.get("moveupright").isEnemy(color)) {
            legalMoves.add(moves.get("moveupright"));
        }

        game.sceneEntities.removeEntity(Square.class);
        return legalMoves;
    }
    public static Array<Square> blackPawnMovement(Chess game, int currentX, int currentY, ChessPiece.Player color) {

        Map<String, Square> moves = new HashMap<>();
        moves.put("movedown", new Square(game, currentX, currentY - 1));
        moves.put("movedowndown", new Square(game, currentX, currentY - 2));
        moves.put("movedownleft", new Square(game, currentX - 1, currentY - 1));
        moves.put("movedownright", new Square(game, currentX + 1, currentY - 1));

        Array<Square> legalMoves = new Array<>();

        // move one square down
        if (moves.get("movedown").isEmpty()) {
            legalMoves.add(moves.get("movedown"));

            // move two squares down
            if (currentY == 6 && moves.get("movedowndown").isEmpty()) {
                legalMoves.add(moves.get("movedowndown"));
            }
        }

        // capture moves
        if (moves.get("movedownleft").isEnemy(color)) {
            legalMoves.add(moves.get("movedownleft"));
        }
        if (moves.get("movedownright").isEnemy(color)) {
            legalMoves.add(moves.get("movedownright"));
        }

        game.sceneEntities.removeEntity(Square.class);
        return legalMoves;
    }
    public static Array<Square> rookMovement(Chess game, int currentX, int currentY, ChessPiece.Player color) {
        Array<Square> legalMoves = new Array<>();
        legalMoves.addAll(genericMovement(game, currentX, currentY, -1, 0, color)); // check left
        legalMoves.addAll(genericMovement(game, currentX, currentY, +1, 0, color)); // check right
        legalMoves.addAll(genericMovement(game, currentX, currentY, 0, 1, color)); // check up
        legalMoves.addAll(genericMovement(game, currentX, currentY, 0, -1, color)); // check down
        return legalMoves;
    }
    public static Array<Square> knightMovement(Chess game, int currentX, int currentY, ChessPiece.Player color) {
        Array<Square> legalMoves = new Array<>();
        legalMoves.addAll(genericMovement(game, currentX, currentY, -1, 2, color, 1)); // up-up-left
        legalMoves.addAll(genericMovement(game, currentX, currentY, 1, 2, color, 1)); // up-up-right
        legalMoves.addAll(genericMovement(game, currentX, currentY, -1, -2, color, 1)); // down-down-left
        legalMoves.addAll(genericMovement(game, currentX, currentY, 1, -2, color, 1)); // down-down-right
        legalMoves.addAll(genericMovement(game, currentX, currentY, 2, 1, color, 1)); // right-right-up
        legalMoves.addAll(genericMovement(game, currentX, currentY, 2, -1, color, 1)); // right-right-down
        legalMoves.addAll(genericMovement(game, currentX, currentY, -2, 1, color, 1)); // left-left-up
        legalMoves.addAll(genericMovement(game, currentX, currentY, -2, -1, color, 1)); // left-left-down
        return legalMoves;
    }
    public static Array<Square> bishopMovement(Chess game, int currentX, int currentY, ChessPiece.Player color) {
        Array<Square> legalMoves = new Array<>();
        legalMoves.addAll(genericMovement(game, currentX, currentY, -1, 1, color)); // check up-left
        legalMoves.addAll(genericMovement(game, currentX, currentY, 1, 1, color)); // check up-right
        legalMoves.addAll(genericMovement(game, currentX, currentY, -1, -1, color)); // check down-left
        legalMoves.addAll(genericMovement(game, currentX, currentY, 1, -1, color)); // check down-right
        return legalMoves;
    }
    public static Array<Square> queenMovement(Chess game, int currentX, int currentY, ChessPiece.Player color) {
        Array<Square> legalMoves = new Array<>();
        legalMoves.addAll(genericMovement(game, currentX, currentY, -1, 0, color)); // check left
        legalMoves.addAll(genericMovement(game, currentX, currentY, +1, 0, color)); // check right
        legalMoves.addAll(genericMovement(game, currentX, currentY, 0, 1, color)); // check up
        legalMoves.addAll(genericMovement(game, currentX, currentY, 0, -1, color)); // check down
        legalMoves.addAll(genericMovement(game, currentX, currentY, -1, 1, color)); // check up-left
        legalMoves.addAll(genericMovement(game, currentX, currentY, 1, 1, color)); // check up-right
        legalMoves.addAll(genericMovement(game, currentX, currentY, -1, -1, color)); // check down-left
        legalMoves.addAll(genericMovement(game, currentX, currentY, 1, -1, color)); // check down-right
        return legalMoves;
    }
    public static Array<Square> kingMovement(Chess game, int currentX, int currentY, ChessPiece.Player color) {
        Array<Square> legalMoves = new Array<>();
        legalMoves.addAll(genericMovement(game, currentX, currentY, -1, 0, color, 1)); // check left
        legalMoves.addAll(genericMovement(game, currentX, currentY, +1, 0, color, 1)); // check right
        legalMoves.addAll(genericMovement(game, currentX, currentY, 0, 1, color, 1)); // check up
        legalMoves.addAll(genericMovement(game, currentX, currentY, 0, -1, color, 1)); // check down
        legalMoves.addAll(genericMovement(game, currentX, currentY, -1, 1, color,1)); // check up-left
        legalMoves.addAll(genericMovement(game, currentX, currentY, 1, 1, color,1)); // check up-right
        legalMoves.addAll(genericMovement(game, currentX, currentY, -1, -1, color,1)); // check down-left
        legalMoves.addAll(genericMovement(game, currentX, currentY, 1, -1, color,1)); // check down-right

        // since the king cant be captured, this has to be searched
        Array<Square> legalMovesNotUnderAttack = new Array<>();
        for (Square legalMove : legalMoves) {
            if (!legalMove.isUnderAttack(color)) legalMovesNotUnderAttack.add(legalMove);
        }
        return legalMovesNotUnderAttack;
    }
    public static Array<Square> genericMovement(Chess game, int currentX, int currentY, int dirX, int dirY, ChessPiece.Player color) {
        Array<Square> legalMoves = new Array<>();

        Square leftSquare = new Square(game, currentX + dirX, currentY + dirY);
        while (leftSquare.isInBounds() && leftSquare.isEmpty()) {
            legalMoves.add(leftSquare);
            leftSquare = new Square(game, leftSquare.getGridX() + dirX, leftSquare.getGridY() + dirY);
        }
        if (leftSquare.isEnemy(color)) {
            legalMoves.add(leftSquare);
        }
        return legalMoves;
    }
    public static Array<Square> genericMovement(Chess game, int currentX, int currentY, int dirX, int dirY, ChessPiece.Player color, int maxIterations) {
        Array<Square> legalMoves = new Array<>();
        Square leftSquare = new Square(game, currentX + dirX, currentY + dirY);

        int i = 0;
        while (leftSquare.isInBounds() && leftSquare.isEmpty() && i < maxIterations) {
            legalMoves.add(leftSquare);
            leftSquare = new Square(game, leftSquare.getGridX() + dirX, leftSquare.getGridY() + dirY);
            i++;
        }
        if (leftSquare.isEnemy(color) && i < maxIterations) {
            legalMoves.add(leftSquare);
        }
        return legalMoves;
    }
}

