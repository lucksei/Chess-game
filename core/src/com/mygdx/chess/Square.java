package com.mygdx.chess;

import com.badlogic.gdx.utils.Array;

/* tool to check the properties of a certain square */
public class Square extends BoardEntity {
    private Array<ChessPiece> chessPieces;
    public Square (SceneEntities sceneEntities, int gridX, int gridY, Array<BoardEntity> boardEntitiesCopy) {
        super(sceneEntities, gridX, gridY);
        this.chessPieces = new Array<>();
        this.chessPieces = getFromSquare(getChessPieces(boardEntitiesCopy));
    }

    private Array<ChessPiece> getChessPieces (Array<BoardEntity> entitiesCopy) {
        Array<ChessPiece> chessPieces = new Array<>();
        for (BoardEntity entity : entitiesCopy) {
            if (entity instanceof ChessPiece) chessPieces.add((ChessPiece) entity);
        }
        return chessPieces;
    }
    private Array<ChessPiece> getFromSquare (Array<ChessPiece> chessPieces) {
        Array<ChessPiece> chessPiecesInSquare = new Array<>();
        for (ChessPiece chessPiece : chessPieces) {
            if(chessPiece.getGridX() == this.gridX && chessPiece.getGridY() == this.gridY) chessPiecesInSquare.add(chessPiece);
        }
        return chessPiecesInSquare;
    }
    public ChessPiece getChessPiece () {
        if (!chessPieces.isEmpty()) {
            return chessPieces.first();
        } else {
            return null;
        }
    }
    public boolean isEmpty(){
        return chessPieces.isEmpty();
    }
    private boolean isChessPieceColor (ChessPiece.Player color) {
        if (!isEmpty() && this.getChessPiece().getPlayer() == color) {
            return true;
        } else {
            return false;
        }
    }
    public boolean isChessPieceWhite () { return isChessPieceColor(ChessPiece.Player.WHITE); }
    public boolean isChessPieceBlack () { return isChessPieceColor(ChessPiece.Player.BLACK); }
}
