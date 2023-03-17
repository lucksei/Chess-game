package com.mygdx.chess;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MoveIndicator extends BoardEntity {

    ChessPiece parent;

    public MoveIndicator(Chess game, int gridX, int gridY, ChessPiece parent) {
        super(game, gridX, gridY);
        this.setTag("moveIndicator");
        this.setClickeable(true);
        this.parent = parent;
        this.setTexture(game.textureList.get(TextureList.Key.INDICATOR));
    }
    @Override
    public void update () {
        super.update();
        if(isClicked()) {
            Square currentSquare = new Square(game, this.getGridX(), this.getGridY());
            if(!currentSquare.isEmpty() ) {
                game.sceneEntities.removeEntity(currentSquare.getChessPiece());
            }

            parent.setGridX(this.gridX);
            parent.setGridY(this.gridY);
            game.sceneEntities.removeEntity(MoveIndicator.class);
            game.sceneEntities.removeEntity(Square.class);
        }
    }
}
