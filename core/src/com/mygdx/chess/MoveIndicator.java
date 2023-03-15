package com.mygdx.chess;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MoveIndicator extends BoardEntity {

    ChessPiece parent;

    public MoveIndicator(SceneEntities sceneEntities, int gridX, int gridY, ChessPiece parent) {
        super(sceneEntities, gridX, gridY);
        setTag("moveIndicator");
        setClickeable(true);
        this.parent = parent;
        setTexture(sceneEntities.textureList.get(TextureList.Key.INDICATOR));
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
        }
    }
}
