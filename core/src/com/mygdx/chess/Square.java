package com.mygdx.chess;

import com.badlogic.gdx.utils.Array;

/* tool to check the properties of a certain square */
public class Square extends BoardEntity {
    private Array<BoardEntity> boardEntities;
    public Square (Chess game, int gridX, int gridY) {
        super(game, gridX, gridY);
        this.boardEntities = new Array<>();
        this.boardEntities = getFromSquare(game.sceneEntities.findEntities(BoardEntity.class));
    }

    private Array<ChessPiece> getChessPieces (Array<BoardEntity> entities) {
        Array<ChessPiece> chessPieces = new Array<>();
        for (BoardEntity entity : entities) {
            if (entity instanceof ChessPiece) chessPieces.add((ChessPiece) entity);
        }
        return chessPieces;
    }
    private Array<BoardEntity> getFromSquare (Array<BoardEntity> boardEntities) {
        Array<BoardEntity> boardEntitiesInSquare = new Array<>();
        for (BoardEntity boardEntity : boardEntities) {
            if(boardEntity.getGridX() == this.gridX && boardEntity.getGridY() == this.gridY) boardEntitiesInSquare.add(boardEntity);
        }
        return boardEntitiesInSquare;
    }
    public ChessPiece getChessPiece () {
        if (!getChessPieces(boardEntities).isEmpty()) {
            return getChessPieces(boardEntities).first();
        } else {
            return null;
        }
    }
    public boolean isEmpty(){
        return getChessPieces(boardEntities).isEmpty();
    }
    public boolean isEnemy (ChessPiece.Player color) {
        if (!isEmpty() && this.getChessPiece().getPlayer() != color) {
            return true;
        } else {
            return false;
        }
    }
    public boolean isInBounds () {
        if (this.getGridX() < 0 || this.getGridX() > 7 || this.getGridY() < 0 || this.getGridY() > 7) {
            return false;
        }
        return true;
    }
    public boolean isUnderAttack (ChessPiece.Player color) {
        for (ChessPiece chessPiece : game.sceneEntities.findEntities(ChessPiece.class)) {
            if (chessPiece.getPlayer() != color) {
                for (Square square : chessPiece.getLegalMoves()) {
                    if(square.getGridX() == this.gridX && square.getGridY() == this.gridY) return true;
                }
            }
        }
        return false;
    }
    public boolean hasMoveIndicator () {
        for (BoardEntity entity : boardEntities) {
            if (entity instanceof MoveIndicator) return true;
        }
        return false;
    }
}
