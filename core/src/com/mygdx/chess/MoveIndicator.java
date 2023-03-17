package com.mygdx.chess;

public class MoveIndicator extends BoardEntity {
    ChessPiece parent;

    public MoveIndicator(Chess game, int gridX, int gridY, ChessPiece parent) {
        super(game, gridX, gridY);
        this.parent = parent;
        this.setTag("moveIndicator");
        entityController = new EntityController(game, this);
        entityController.setClickeable(true);
        entityController.setTexture(game.textureList.get(TextureList.Key.INDICATOR));
    }
    @Override
    public void update () {
        super.update();
        if(entityController.isClicked()) {
            Square currentSquare = new Square(game, this.getGridX(), this.getGridY());
            if(!currentSquare.isEmpty()) {
                currentSquare.getChessPiece().remove();
            }
            currentSquare.remove();

            parent.setGridX(this.gridX);
            parent.setGridY(this.gridY);
            game.sceneEntities.removeEntity(MoveIndicator.class);
        }
    }
}
