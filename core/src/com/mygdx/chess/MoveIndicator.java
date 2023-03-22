package com.mygdx.chess;

public class MoveIndicator extends BoardEntity {
    ChessPiece parent;
    private boolean isCastlingLeft;
    private boolean isCastlingRight;

    public MoveIndicator(Chess game, int gridX, int gridY, ChessPiece parent) {
        super(game, gridX, gridY);
        this.parent = parent;
        this.isCastlingLeft = false;
        this.isCastlingRight = false;
        entityController = new EntityController(game, this);
        entityController.setClickeable(true);
        entityController.setTexture(game.textureList.get(TextureList.Key.INDICATOR));
    }
    @Override
    public void update () {
        super.update();
        if (entityController.isClicked()) {
            activateIndicator();
        }
    }
    public void activateIndicator () {
        if (isCastlingLeft) {
            ChessPiece leftRook = game.gameLogic.getCurrentBoardState().getFromSquare(new Square(this.getGridX(),this.getGridY()));
            leftRook.setGridX(leftRook.getGridX()+3);
            parent.movePiece(parent.getGridX()-2, parent.getGridY());
        } else if (isCastlingRight) {
            ChessPiece rightRook = game.gameLogic.getCurrentBoardState().getFromSquare(new Square(this.getGridX(),this.getGridY()));
            rightRook.setGridX(rightRook.getGridX()-2);
            parent.movePiece(parent.getGridX()+2, parent.getGridY());
        } else {
            parent.movePiece(this.getGridX(),this.getGridY());
        }
        game.sceneEntities.removeEntityFromScene(MoveIndicator.class); //delete legal moves
    }
    public void setCastlingLeft (boolean castlingLeft) { this.isCastlingLeft = castlingLeft; }
    public void setCastlingRight (boolean castlingRight) { this.isCastlingRight = castlingRight; }
}
