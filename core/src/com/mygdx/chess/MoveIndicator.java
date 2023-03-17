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
            // check if the move is also a capture, in that case remove it
            Square currentSquare = new Square(game, this.getGridX(), this.getGridY());
            if(!currentSquare.isEmpty()) {
                currentSquare.getChessPiece().remove();
            }
            currentSquare.remove();

            // deactivate the piece and move it to the new square
            parent.setActive(false);
            parent.setGridX(this.gridX);
            parent.setGridY(this.gridY);
            // remove this and all the other squares
            game.sceneEntities.removeEntity(MoveIndicator.class);
        }
    }
}
