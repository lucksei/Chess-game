package com.mygdx.chess;

public class MoveIndicator extends BoardEntity {
    ChessPiece parent;

    public MoveIndicator(Chess game, int gridX, int gridY, ChessPiece parent) {
        super(game, gridX, gridY);
        this.parent = parent;
        entityController = new EntityController(game, this);
        entityController.setClickeable(true);
        entityController.setTexture(game.textureList.get(TextureList.Key.INDICATOR));
    }
    @Override
    public void update () {
        super.update();
        if(entityController.isClicked()) {
            parent.movePiece(game.gameLogic.getCurrentBoardState(), this.getGridX(),this.getGridY());
            parent.setActive(false);
            // remove this and all the other squares
            game.sceneEntities.removeEntityFromScene(MoveIndicator.class);
        }
    }
}
