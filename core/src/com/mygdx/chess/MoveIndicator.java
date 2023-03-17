package com.mygdx.chess;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MoveIndicator extends BoardEntity {
    ChessPiece parent;

    public MoveIndicator(Chess game, int gridX, int gridY, ChessPiece parent) {
        super(game, gridX, gridY);
        this.parent = parent;
        this.setTag("moveIndicator");
        entityView = new EntityView(game, this);
        entityView.setClickeable(true);
        entityView.setTexture(game.textureList.get(TextureList.Key.INDICATOR));
    }
    @Override
    public void update () {
        super.update();
        if(entityView.isClicked()) {
            Square currentSquare = new Square(game, this.getGridX(), this.getGridY());
            if(!currentSquare.isEmpty()) {
                currentSquare.getChessPiece().remove();
            }

            parent.setGridX(this.gridX);
            parent.setGridY(this.gridY);
            game.sceneEntities.removeEntity(MoveIndicator.class);
            game.sceneEntities.removeEntity(Square.class);
        }
    }
}
