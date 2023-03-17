package com.mygdx.chess;

import com.badlogic.gdx.utils.Array;

public class ChessPiece extends BoardEntity {

    public enum Type { BISHOP, BISHOP1, KING, KING1, KNIGHT, KNIGHT1, PAWN, PAWN1, QUEEN, QUEEN1, ROOK, ROOK1 }
    private Type type;

    public enum Player { WHITE, BLACK }
    private Player player;

    private boolean isActive;
    private boolean isTurn;

    public ChessPiece (Chess game, int gridX, int gridY, Type type) {
        super(game, gridX, gridY);
        setTag("chessPiece");
        entityController = new EntityController(game, this);
        this.type = type;

        entityController.setClickeable(true);
        switch (type) {
            case BISHOP:
                entityController.setTexture(game.textureList.get(TextureList.Key.BISHOP));
                this.player = Player.WHITE;
                break;
            case BISHOP1:
                entityController.setTexture(game.textureList.get(TextureList.Key.BISHOP1));
                this.player = Player.BLACK;
                break;
            case KING:
                entityController.setTexture(game.textureList.get(TextureList.Key.KING));
                this.player = Player.WHITE;
                break;
            case KING1:
                entityController.setTexture(game.textureList.get(TextureList.Key.KING1));
                this.player = Player.BLACK;
                break;
            case KNIGHT:
                entityController.setTexture(game.textureList.get(TextureList.Key.KNIGHT));
                this.player = Player.WHITE;
                break;
            case KNIGHT1:
                entityController.setTexture(game.textureList.get(TextureList.Key.KNIGHT1));
                this.player = Player.BLACK;
                break;
            case PAWN:
                entityController.setTexture(game.textureList.get(TextureList.Key.PAWN));
                this.player = Player.WHITE;
                break;
            case PAWN1:
                entityController.setTexture(game.textureList.get(TextureList.Key.PAWN1));
                this.player = Player.BLACK;
                break;
            case QUEEN:
                entityController.setTexture(game.textureList.get(TextureList.Key.QUEEN));
                this.player = Player.WHITE;
                break;
            case QUEEN1:
                entityController.setTexture(game.textureList.get(TextureList.Key.QUEEN1));
                this.player = Player.BLACK;
                break;
            case ROOK:
                entityController.setTexture(game.textureList.get(TextureList.Key.ROOK));
                this.player = Player.WHITE;
                break;
            case ROOK1:
                entityController.setTexture(game.textureList.get(TextureList.Key.ROOK1));
                this.player = Player.BLACK;
                break;
            default:
                entityController.setTexture(game.textureList.get(TextureList.Key.ERR));
                this.player = Player.WHITE;
                break;
        }
    }
    @Override
    public void update () {
        super.update();
        if(entityController.isClicked()) {
            if (!this.isActive()) {
                // activate this piece and deactivate all others
                this.setActive(true);
                for (ChessPiece chessPiece : game.sceneEntities.findEntities(ChessPiece.class)) chessPiece.setActive(false);
                // delete all other moves on the board
                game.sceneEntities.removeEntity(MoveIndicator.class);
                // create all possible legal moves
                Array<Square> legalMoves = MovementStrategy.getLegalMoves(
                        game, this.gridX, this.gridY, this.getPlayer(), this.getType());
                for (Square legalMove : legalMoves) {
                    new MoveIndicator(game, legalMove.getGridX(), legalMove.getGridY(), this);
                }
            } else {
                // deactivate and delete legal moves
                setActive(false);
                game.sceneEntities.removeEntity(MoveIndicator.class);
            }
        }
    }

    // get and set methods
    public Type getType(){
        return this.type;
    }
    public Player getPlayer(){
        return this.player;
    }

    public boolean isActive () { return this.isActive; }
    public void setActive (boolean isActive) { this.isActive = isActive; }
    public boolean isTurn () { return this.isTurn; }
    public void setTurn (boolean isTurn) { this.isTurn = isTurn; }
}