package com.mygdx.chess;

public class ChessPiece extends GameObj {

    public enum PieceType { BISHOP, KING, KNIGHT, PAWN, QUEEN, ROOK }

    private PieceType type;

    public ChessPiece (Chess game, PieceType type, int x, int y) {
        super(game, x, y);
        this.type = type;
        switch (type) {
            case BISHOP:
                setTexture(game.textureList.get(TextureList.Key.BISHOP));
                break;
            case KING:
                setTexture(game.textureList.get(TextureList.Key.KING));
                break;
            case KNIGHT:
                setTexture(game.textureList.get(TextureList.Key.KNIGHT));
                break;
            case PAWN:
                setTexture(game.textureList.get(TextureList.Key.PAWN));
                break;
            case QUEEN:
                setTexture(game.textureList.get(TextureList.Key.QUEEN));
                break;
            case ROOK:
                setTexture(game.textureList.get(TextureList.Key.ROOK));
                break;
            default:
                setTexture(game.textureList.get(TextureList.Key.ERR));
                break;
        }
    }
    @Override
    public void update () {
        if(isClicked()) {
            // Check if it's possible to move somewhere
            // Create the buttons on the screen to move
            game.chessScene.createMoveIndicator(this, getX(),getY()+1);
        }
    }
}
