package com.mygdx.chess;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MoveIndicator extends BoardEntity {

    ChessPiece parent;

    public MoveIndicator(SceneEntities sceneEntities, int gridX, int gridY, ChessPiece parent) {
        super(sceneEntities, gridX, gridY);
        this.setTag("moveIndicator");
        this.setClickeable(true);
        this.parent = parent;
        this.setTexture(sceneEntities.textureList.get(TextureList.Key.INDICATOR));
    }
    @Override
    public void update () {
        super.update();
        if(isClicked()) {
            Square currentSquare = sceneEntities.createSquare(this.getGridX(), this.getGridY());
            if(!currentSquare.isEmpty() ) {
                sceneEntities.removeEntity(currentSquare.getChessPiece());
            }

            sceneEntities.movePiece(parent, getGridX(), getGridY());
            sceneEntities.removeEntity(MoveIndicator.class);
            sceneEntities.removeEntity(Square.class);
        }
    }
}
