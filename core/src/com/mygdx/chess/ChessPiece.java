package com.mygdx.chess;

import com.badlogic.gdx.utils.Array;

public class ChessPiece extends BoardEntity {

    public enum Type { BISHOP, BISHOP1, KING, KING1, KNIGHT, KNIGHT1, PAWN, PAWN1, QUEEN, QUEEN1, ROOK, ROOK1 }
    public enum Player { WHITE, BLACK }

    private Type type;
    private Player player;

    public ChessPiece (Chess game, int gridX, int gridY, Type type) {
        super(game, gridX, gridY);
        setTag("chessPiece");
        this.type = type;

        setClickeable(true);
        switch (type) {
            case BISHOP:
                setTexture(game.textureList.get(TextureList.Key.BISHOP));
                this.player = Player.WHITE;
                break;
            case BISHOP1:
                setTexture(game.textureList.get(TextureList.Key.BISHOP1));
                this.player = Player.BLACK;
                break;
            case KING:
                setTexture(game.textureList.get(TextureList.Key.KING));
                this.player = Player.WHITE;
                break;
            case KING1:
                setTexture(game.textureList.get(TextureList.Key.KING1));
                this.player = Player.BLACK;
                break;
            case KNIGHT:
                setTexture(game.textureList.get(TextureList.Key.KNIGHT));
                this.player = Player.WHITE;
                break;
            case KNIGHT1:
                setTexture(game.textureList.get(TextureList.Key.KNIGHT1));
                this.player = Player.BLACK;
                break;
            case PAWN:
                setTexture(game.textureList.get(TextureList.Key.PAWN));
                this.player = Player.WHITE;
                break;
            case PAWN1:
                setTexture(game.textureList.get(TextureList.Key.PAWN1));
                this.player = Player.BLACK;
                break;
            case QUEEN:
                setTexture(game.textureList.get(TextureList.Key.QUEEN));
                this.player = Player.WHITE;
                break;
            case QUEEN1:
                setTexture(game.textureList.get(TextureList.Key.QUEEN1));
                this.player = Player.BLACK;
                break;
            case ROOK:
                setTexture(game.textureList.get(TextureList.Key.ROOK));
                this.player = Player.WHITE;
                break;
            case ROOK1:
                setTexture(game.textureList.get(TextureList.Key.ROOK1));
                this.player = Player.BLACK;
                break;
            default:
                setTexture(game.textureList.get(TextureList.Key.ERR));
                this.player = Player.WHITE;
                break;
        }
    }
    @Override
    public void update () {
        super.update();
        if(isClicked()) {
            // activate so to speak, now this ChessPiece is the "active" one on the board, tell that to ChessScene
            // tell ChessScene to delete any other indicators on the board (might have to use TAGS)
            game.sceneEntities.removeEntity(MoveIndicator.class);
            // create indicators (where ChessScene lets me) to move the piece
            Array<Square> legalMoves = MovementStrategy.getLegalMoves(game, this.gridX, this.gridY, getPlayer(), getType());
            for (Square legalMove : legalMoves) {
                new MoveIndicator(game, legalMove.getGridX(), legalMove.getGridY(), this);
            }

            // if the piece deactivates for some reason it should remove its indicators
        }
    }

    // get and set methods
    public Type getType(){
        return this.type;
    }
    public Player getPlayer(){
        return this.player;
    }
}