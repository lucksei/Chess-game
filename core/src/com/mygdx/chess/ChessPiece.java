package com.mygdx.chess;

public class ChessPiece extends GameObj {

    public enum PieceType { BISHOP, KING, KNIGHT, PAWN, QUEEN, ROOK }

    private PieceType type;

    public ChessPiece (Chess game, PieceType type, int x, int y) {
        super(game, x, y);
        setTag("chessPiece");
        this.type = type;
        setClickeable(true);
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
            // activate so to speak, now this ChessPiece is the "active" one on the board, tell that to ChessScene
            // tell ChessScene to delete any other indicators on the board (might have to use TAGS)
            game.chessScene.removeObject("moveIndicator");
            // create indicators (where ChessScene lets me) to move the piece
            game.chessScene.createMoveIndicator(this,this.getX()+1,this.getY());
            game.chessScene.createMoveIndicator(this,this.getX()-1,this.getY());
            game.chessScene.createMoveIndicator(this,this.getX(),this.getY()+1);
            game.chessScene.createMoveIndicator(this,this.getX(),this.getY()-1);
            // if the piece deactivates for some reason it should remove its indicators
        }
    }
}
