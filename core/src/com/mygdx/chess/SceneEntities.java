package com.mygdx.chess;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

public class SceneEntities {

    private Array<Entity> entities;
    private Array<Entity> entitiesToRemove;

    private Chess game;
    public InputHandler inputHandler;
    public SpriteBatch batch;
    public TextureList textureList;

    ChessBoard chessBoard;

    public SceneEntities (Chess game) {
        this.game = game;
        this.inputHandler = game.inputHandler;
        this.batch = game.batch;
        this.textureList = game.textureList;

        entities = new Array<>();
        entitiesToRemove = new Array<>();

        this.chessBoard = new ChessBoard(this);
        addEntity(chessBoard);
    }

    public void render () {
        for (Entity entity : entities) {
            entity.render();
        }
    }
    public void update () {
        for (Entity entity : entities) {
            entity.update();
        }
        for (Entity entity : entitiesToRemove) {
            entities.removeValue(entity,true);
        }
        entitiesToRemove.clear();
    }

    private void addEntity (Entity entity) { entities.add(entity); }
    public void removeEntity(Entity entity) {
        entitiesToRemove.add(entity);
    }
    public void removeEntity (String tag) {
        entitiesToRemove.addAll(findEntities(tag));
    }
    public <T extends Entity> void removeEntity (Class<T> type) {
        entitiesToRemove.addAll(findEntities(type));
    }
    public Array<Entity> findEntities (String tag) {
        Array<Entity> entitiesFound = new Array<>();
        for (Entity entity : this.getEntitiesCopy()) {
            if(entity.getTag() == tag) entitiesFound.add(entity);
        }
        return entitiesFound;
    }
    public <T extends Entity> Array<T> findEntities (Class<T> type) {
        Array<T> entitiesOfTypeFound = new Array<>();
        for (Entity entity : this.getEntitiesCopy()) {
            if(type.isInstance(entity)) {
                entitiesOfTypeFound.add(type.cast(entity));
            }
        }
        return entitiesOfTypeFound;
    }
    public Array<Entity> getEntitiesCopy () {
        Array<Entity> entitiesCopy = new Array<>(this.entities); // like using temp.addAll(objects);
        return entitiesCopy;
    }

    public Array<BoardEntity> getBoardEntitiesCopy () {
        Array<Entity> entitiesCopy = new Array<>(this.entities);
        Array<BoardEntity> boardEntities = new Array<>();
        for (Entity entity : entitiesCopy) {
            if (entity instanceof BoardEntity) boardEntities.add((BoardEntity) entity);
        }
        return boardEntities;
    }

    public void createChessBoard() {
        ChessBoard chessBoard = new ChessBoard(this);
        addEntity(chessBoard);
    }
    public void createChessPiece (ChessPiece.Type type, int gridX, int gridY) {
        ChessPiece chessPiece = new ChessPiece(this, gridX, gridY, type);
        addEntity(chessPiece);
    }
    public void createMoveIndicator (ChessPiece parent, int gridX, int gridY) {
        MoveIndicator moveIndicator = new MoveIndicator(this, gridX, gridY, parent);
        addEntity(moveIndicator);
    }
    public Square createSquare (int gridX, int gridY) {
        Square square = new Square(this, gridX, gridY, getBoardEntitiesCopy());
        addEntity(square);
        return square;

    }

    public void movePiece(BoardEntity boardEntity, int gridX, int gridY) {
        boardEntity.setGridX(gridX);
        boardEntity.setGridY(gridY);
    }


}
