package com.mygdx.chess;

import com.badlogic.gdx.utils.Array;

import java.util.HashMap;
import java.util.Map;

public class MovementStrategy {
    public static Array<Square> getLegalMoves(Chess game, int currentX, int currentY, ChessPiece.Player color, ChessPiece.Type type) {
        switch(type){
            case BISHOP:
                return null;
            case KING:
                return null;
            case KNIGHT:
                return null;
            case PAWN:
                return whitePawnMovement(game, currentX, currentY, color);
            case PAWN1:
                return blackPawnMovement(game, currentX, currentY, color);
            case QUEEN:
                return null;
            case ROOK:
                return null;
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

        Array<Square> legalMove = new Array<>();

        // move one square up
        if(moves.get("moveup").isEmpty()) {
            legalMove.add(moves.get("moveup"));

            // move two squares up
            if(currentY == 1 && moves.get("moveupup").isEmpty()) {
                legalMove.add(moves.get("moveupup"));
            }
        }
        // capture moves
        if (moves.get("moveupleft").isEnemy(color)) {
            legalMove.add(moves.get("moveupleft"));
        }
        if (moves.get("moveupright").isEnemy(color)) {
            legalMove.add(moves.get("moveupright"));
        }

        game.sceneEntities.removeEntity(Square.class);
        return legalMove;
    }
    public static Array<Square> blackPawnMovement(Chess game, int currentX, int currentY, ChessPiece.Player color) {

        Map<String, Square> moves = new HashMap<>();
        moves.put("movedown", new Square(game, currentX, currentY - 1));
        moves.put("movedowndown", new Square(game, currentX, currentY - 2));
        moves.put("movedownleft", new Square(game, currentX - 1, currentY - 1));
        moves.put("movedownright", new Square(game, currentX + 1, currentY - 1));

        Array<Square> legalMove = new Array<>();

        // move one square down
        if (moves.get("movedown").isEmpty()) {
            legalMove.add(moves.get("movedown"));

            // move two squares down
            if (currentY == 6 && moves.get("movedowndown").isEmpty()) {
                legalMove.add(moves.get("movedowndown"));
            }
        }

        // capture moves
        if (moves.get("movedownleft").isEnemy(color)) {
            legalMove.add(moves.get("movedownleft"));
        }
        if (moves.get("movedownright").isEnemy(color)) {
            legalMove.add(moves.get("movedownright"));
        }

        game.sceneEntities.removeEntity(Square.class);
        return legalMove;
    }
    public static Array<Square> rookMovement() {
        return null;
    }
    public static Array<Square> knightMovement() {
        return null;
    }
    public static Array<Square> bishopMovement() {
        return null;
    }
    public static Array<Square> queenMovement() {
        return null;
    }
    public static Array<Square> kingMovement() {
        return null;
    }
}
