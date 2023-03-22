package com.mygdx.chess;

import com.badlogic.gdx.utils.Array;

public class ChessPiece extends BoardEntity {

    public enum Type { BISHOP, KING, KNIGHT, PAWN, QUEEN, ROOK }
    private Type type;

    public enum Player { WHITE, BLACK }
    private Player player;

    private boolean isActive;
    private boolean isTurn;
    private boolean wasMoved;

    public ChessPiece (Chess game, int gridX, int gridY, Type type, Player player) {
        super(game, gridX, gridY);
        entityController = new EntityController(game, this);
        entityController.setClickeable(true);
        entityController.setDraggeable(true);
        this.type = type;
        this.player = player;
        isActive = false;
        isTurn = true;
        wasMoved = false;

        switch (type) {
            case BISHOP:
                if(player == Player.WHITE)
                    entityController.setTexture(game.textureList.get(TextureList.Key.BISHOP));
                else
                    entityController.setTexture(game.textureList.get(TextureList.Key.BISHOP1));
                break;
            case KNIGHT:
                if(player == Player.WHITE)
                    entityController.setTexture(game.textureList.get(TextureList.Key.KNIGHT));
                else
                    entityController.setTexture(game.textureList.get(TextureList.Key.KNIGHT1));
                break;
            case PAWN:
                if (player == Player.WHITE)
                    entityController.setTexture(game.textureList.get(TextureList.Key.PAWN));
                else
                    entityController.setTexture(game.textureList.get(TextureList.Key.PAWN1));
                break;
            case ROOK:
                if(player == Player.WHITE)
                    entityController.setTexture(game.textureList.get(TextureList.Key.ROOK));
                else
                    entityController.setTexture(game.textureList.get(TextureList.Key.ROOK1));
                break;
            case QUEEN:
                if(player == Player.WHITE)
                    entityController.setTexture(game.textureList.get(TextureList.Key.QUEEN));
                else
                    entityController.setTexture(game.textureList.get(TextureList.Key.QUEEN1));
                break;
            case KING:
                if(player == Player.WHITE)
                    entityController.setTexture(game.textureList.get(TextureList.Key.KING));
                else
                    entityController.setTexture(game.textureList.get(TextureList.Key.KING1));
                break;
            default:
                break;
        }
    }
    @Override
    public void update () {
        super.update();
        // if its clicked and its its turn and there is no indicator on top of this piece
        if(entityController.isClicked() && this.isTurn() && new Square(this.getGridX(),this.getGridY()).hasMoveIndicator(game.sceneEntities) == null) {
            if (!this.isActive()) {
                // deactivate any other piece and activate this one
                for (ChessPiece chessPiece : game.sceneEntities.findEntities(ChessPiece.class)) chessPiece.setActive(false);
                this.setActive(true);
                getLegalMoves(); // create all possible legal moves
            } else {
                // deactivate and delete legal moves
                setActive(false);
                game.sceneEntities.removeEntityFromScene(MoveIndicator.class);
            }
        }

        if(entityController.startDragging() && this.isTurn()) {
            // deactivate any other piece and activate this one
            for (ChessPiece chessPiece : game.sceneEntities.findEntities(ChessPiece.class)) chessPiece.setActive(false);
            this.setActive(true);
            // create all possible legal moves
            getLegalMoves();
        }
        if(entityController.endDragging() && this.isTurn()) {
            setActive(false); // deactivate the piece
            // get square where the piece was dragged
            int endPositionX = (int) ((this.entityController.getX() + chessBoard.getX() + SQUARE_SIZE/2)/SQUARE_SIZE);
            int endPositionY = (int) ((this.entityController.getY() + chessBoard.getY() + SQUARE_SIZE/2)/SQUARE_SIZE);
            // get move indicator on the place the piece was dragged and if there is one, activate it
            MoveIndicator moveIndicator = new Square(endPositionX,endPositionY).hasMoveIndicator(game.sceneEntities);
            if (moveIndicator != null) {
                moveIndicator.activateIndicator();
            }
            game.sceneEntities.removeEntityFromScene(MoveIndicator.class); //delete legal moves
        }

    }
    public void getLegalMoves() {
        Array<Square> legalMoves = new Array<>();
        switch (this.player) {
            case WHITE:
                legalMoves = game.gameLogic.checkForCheckAlg(this, game.whiteKing);
                break;
            case BLACK:
                legalMoves = game.gameLogic.checkForCheckAlg(this, game.blackKing);
                break;
            default:
                break;
        }
        // delete all other moves on the board
        game.sceneEntities.removeEntityFromScene(MoveIndicator.class);
        for (Square legalMove : legalMoves) {
            new MoveIndicator(game, legalMove.getGridX(), legalMove.getGridY(), this);
        }

        // castling moves
        if (this.getType() == Type.KING) {
            int gridY = (getPlayer() == Player.WHITE) ? 0 : 7;
            if (GameLogic.checkCastlingLeft(game.gameLogic.getCurrentBoardState(), this)) {
                MoveIndicator castling = new MoveIndicator(game, 0, gridY, this);
                castling.setCastlingLeft(true);
            }
            if (GameLogic.checkCastlingRight(game.gameLogic.getCurrentBoardState(), this)) {
                MoveIndicator castling = new MoveIndicator(game, 7, gridY, this);
                castling.setCastlingRight(true);
            }
        }
    }

    // used only for the check for checks algorithm!!!
    public void movePiece (BoardState boardState, int gridX, int gridY) {
        game.gameLogic.capturePiece(boardState, gridX, gridY);
        setGridX(gridX);
        setGridY(gridY);
    }
    public void movePiece (int gridX, int gridY) {
        setActive(false); // deactivate the piece
        game.sceneEntities.removeEntityFromScene(MoveIndicator.class); // remove this and all the other squares
        game.gameLogic.capturePiece(game.gameLogic.getCurrentBoardState(), gridX, gridY);
        setGridX(gridX);
        setGridY(gridY);
        wasMoved = true;
        game.gameLogic.nextTurn();
    }

    // get and set methods
    public Type getType(){
        return this.type;
    }
    public Player getPlayer(){
        return this.player;
    }
    public boolean wasMoved() { return this.wasMoved; }
    public boolean isActive () { return this.isActive; }
    public void setActive (boolean isActive) { this.isActive = isActive; }
    public boolean isTurn () { return this.isTurn; }
    public void setTurn (boolean isTurn) { this.isTurn = isTurn; }
}