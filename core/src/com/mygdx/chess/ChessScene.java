package com.mygdx.chess;

import com.badlogic.gdx.utils.Array;

public class ChessScene {

    private final Chess game;

//    private boolean isChessPieceActive;
//    private ChessPiece activeChessPiece;
    private Array<GameObj> objects;
    private Array<GameObj> objectsToRemove;
    public ChessScene (Chess game) {
        this.game = game;
//        this.isChessPieceActive = false;
//        this.activeChessPiece = null;
        objects = new Array<>();
        objectsToRemove = new Array<>();
    }

    private void addObject (GameObj object) {
        objects.add(object);
    }

    public void removeObject (GameObj object) {
        objectsToRemove.add(object);
    }
    public void removeObject (String tag) {
        objectsToRemove.addAll(searchTag(tag));
    }

    /* very inefficient CPU wise, use it as little as possible.
    It requires a copy of the original since this is usually called in update,
    and since i cant nest iterators, i have to iterate on top of a new array,
    cloning this takes a lot of memory so in paper is pretty intensive */
    public Array<GameObj> searchTag (String tag) {
        Array<GameObj> objectsCopy = new Array<>(objects); // like using temp.addAll(objects);
        Array<GameObj> objectsTagged = new Array<>();
        for (GameObj object : objectsCopy) {
            if(object.getTag() == tag) objectsTagged.add(object);
        }
        return objectsTagged;

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
        for (GameObj object : objects) {
            object.render();
        }
    }

    public void update () {
        for (GameObj object : objects) {
            object.update();
        }
        for (GameObj object : objectsToRemove) {
            objects.removeValue(object,true);
        }
        objectsToRemove.clear();
    }

    public void movePiece(GameObj obj, int x, int y) {
        obj.setX(x); obj.setY(y);
    }
}
