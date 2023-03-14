package com.mygdx.chess;

import com.badlogic.gdx.utils.Array;

/* tool to check the properties of a certain square */
public class Square {
    public int x,y;
    private Array<Entity> entities;
    public Square (Array<Entity> entities, int absoluteX, int absoluteY) {
        this.x = absoluteX;
        this.y = absoluteY;
        this.entities = entities;
    }

    public Square (Array<Entity> entities, int currentX, int currentY, int relativeX, int relativeY) {
        this.x = currentX+relativeX;
        this.y = currentY+relativeY;
        this.entities = getEntitiesFromSquare(entities);
    }

    private Array<Entity> getEntitiesFromSquare (Array<Entity> entities) {
        Array<Entity> squareEntities = new Array<>();
        for (Entity entity : entities) {
            if(entity.getX() == this.x && entity.getY() == this.y) squareEntities.add(entity);
        }
        return squareEntities;
    }

    private Array<ChessPiece> getChessPieces () {
        Array<ChessPiece> chessPieces = new Array<>();
        for (Entity entity : entities) {
            if (entity instanceof ChessPiece) chessPieces.add((ChessPiece) entity);
        }
        return chessPieces;
    }

    public boolean isEmpty(){
        return getChessPieces().isEmpty();
    }
    private boolean isChessPieceColor (ChessPiece.Player color) {
        for (ChessPiece chessPiece : this.getChessPieces()) {
            if(chessPiece.getPlayer() == color) return true;
        }
        return false;
    }
    public boolean isChessPieceWhite () { return isChessPieceColor(ChessPiece.Player.WHITE); }
    public boolean isChessPieceBlack () { return isChessPieceColor(ChessPiece.Player.BLACK); }
}
