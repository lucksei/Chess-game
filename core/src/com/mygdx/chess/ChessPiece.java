package com.mygdx.chess;

import com.badlogic.gdx.utils.Array;

public class ChessPiece extends BoardEntity {

    public enum Type { BISHOP, KING, KNIGHT, PAWN, QUEEN, ROOK }
    private Type type;

    public enum Player { WHITE, BLACK }
    private Player player;

    private boolean isActive;
    private boolean isTurn;

    public ChessPiece (Chess game, int gridX, int gridY, Type type, Player player) {
        super(game, gridX, gridY);
        setTag("chessPiece");
        entityController = new EntityController(game, this);
        entityController.setClickeable(true);
        entityController.setDraggeable(true);
        this.type = type;
        this.player = player;
        isActive = false;
        isTurn = true;

        switch (type) {
            case BISHOP:
                if(player == Player.WHITE)
                    entityController.setTexture(game.textureList.get(TextureList.Key.BISHOP));
                else
                    entityController.setTexture(game.textureList.get(TextureList.Key.BISHOP1));
                break;
            case KNIGHT:
                if(player == Player.WHITE)
                    entityController.setTexture(game.textureList.get(TextureList.Key.KNIGHT));
                else
                    entityController.setTexture(game.textureList.get(TextureList.Key.KNIGHT1));
                break;
            case PAWN:
                if (player == Player.WHITE)
                    entityController.setTexture(game.textureList.get(TextureList.Key.PAWN));
                else
                    entityController.setTexture(game.textureList.get(TextureList.Key.PAWN1));
                break;
            case ROOK:
                if(player == Player.WHITE)
                    entityController.setTexture(game.textureList.get(TextureList.Key.ROOK));
                else
                    entityController.setTexture(game.textureList.get(TextureList.Key.ROOK1));
                break;
            case QUEEN:
                if(player == Player.WHITE)
                    entityController.setTexture(game.textureList.get(TextureList.Key.QUEEN));
                else
                    entityController.setTexture(game.textureList.get(TextureList.Key.QUEEN1));
                break;
            case KING:
                if(player == Player.WHITE)
                    entityController.setTexture(game.textureList.get(TextureList.Key.KING));
                else
                    entityController.setTexture(game.textureList.get(TextureList.Key.KING1));
                break;
            default:
                break;
        }
    }
    @Override
    public void update () {
        super.update();
        if(entityController.isClicked() && this.isTurn()) {
            if (!this.isActive()) {
                // deactivate any other piece and activate this one
                for (ChessPiece chessPiece : game.sceneEntities.findEntities(ChessPiece.class)) chessPiece.setActive(false);
                this.setActive(true);
                // delete all other moves on the board
                game.sceneEntities.removeEntity(MoveIndicator.class);
                // create all possible legal moves
                for (Square legalMove : this.getLegalMoves()) {
                    new MoveIndicator(game, legalMove.getGridX(), legalMove.getGridY(), this);
                }
            } else {
                // deactivate and delete legal moves
                setActive(false);
                game.sceneEntities.removeEntity(MoveIndicator.class);
            }
        }
    }
    public Array<Square> getLegalMoves () {
        return MovementStrategy.getLegalMoves(game, this.gridX, this.gridY, this.getPlayer(), this.getType());
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