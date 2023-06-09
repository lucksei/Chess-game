package com.mygdx.chess;

public class BoardEntity extends Entity {
    EntityController chessBoard;
    static int SQUARE_SIZE = 42;
    int gridX, gridY;

    public BoardEntity (Chess game, int gridX, int gridY) {
        super(game);
        this.chessBoard = game.chessBoard;
        this.gridX = gridX;
        this.gridY = gridY;
        this.setW(SQUARE_SIZE);
        this.setH(SQUARE_SIZE);
    }
    @Override
    public void update () {
        super.update();
        this.setX(SQUARE_SIZE * this.gridX + chessBoard.getX());
        this.setY(SQUARE_SIZE * this.gridY + chessBoard.getY());
    }

    // get and set methods
    public int getGridX () { return this.gridX; }
    public void setGridX ( int gridX ) {
        this.gridX = gridX;
        this.setX(SQUARE_SIZE * this.gridX + chessBoard.getX());
    }
    public int getGridY () { return this.gridY; }
    public void setGridY ( int gridY ) {
        this.gridY = gridY;
        this.setY(SQUARE_SIZE * this.gridY + chessBoard.getY());
    }
}
