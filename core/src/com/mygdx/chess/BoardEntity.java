package com.mygdx.chess;

public class BoardEntity extends Entity {
    ChessBoard chessBoard;
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
        this.setX(SQUARE_SIZE * this.gridX);
        this.setY(SQUARE_SIZE * this.gridY);
    }

    // get and set methods
    public int getGridX () { return this.gridX; }
    public void setGridX ( int gridX ) { this.gridX = gridX; }
    public int getGridY () { return this.gridY; }
    public void setGridY ( int gridY ) { this.gridY = gridY; }
}
