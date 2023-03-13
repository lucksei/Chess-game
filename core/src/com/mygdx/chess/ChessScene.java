package com.mygdx.chess;

import com.badlogic.gdx.utils.Array;

public class ChessScene {

    private Chess game;
    private Array<GameObj> objects;
    public ChessScene (Chess game) {
        this.game = game;
        objects = new Array<GameObj>();
    }

    public void addObject (GameObj object) {
        objects.add(object);
    }

    public void removeObject (GameObj object) {
        // TODO logic
    }

    public void createChessPiece (ChessPiece.PieceType type, int x, int y) {
        ChessPiece chessPiece = new ChessPiece(game, type, x, y);
        addObject(chessPiece);
    }

    public void createMoveIndicator (ChessPiece chessPiece, int x, int y) {
        MoveIndicator moveIndicator = new MoveIndicator (game, chessPiece, x, y);
        addObject(moveIndicator);
    }

    public void render () {
        for (GameObj object : objects) object.render();
    }

    public void update () {
        for (GameObj object : objects) object.update();
    }

    public void movePiece(GameObj obj, int x, int y) {
        obj.setX(x); obj.setY(y);
    }
}
