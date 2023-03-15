package com.mygdx.chess;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import java.util.HashMap;
import java.util.Map;

public class ChessPiece extends BoardEntity implements MovementStrategy {

    public enum Type { BISHOP, BISHOP1, KING, KING1, KNIGHT, KNIGHT1, PAWN, PAWN1, QUEEN, QUEEN1, ROOK, ROOK1 }
    public enum Player { WHITE, BLACK }

    private Type type;
    private Player player;

    public ChessPiece (SceneEntities sceneEntities, int gridX, int gridY, Type type) {
        super(sceneEntities, gridX, gridY);
        setTag("chessPiece");
        this.type = type;
        setClickeable(true);
        switch (type) {
            case BISHOP:
                setTexture(sceneEntities.textureList.get(TextureList.Key.BISHOP));
                this.player = Player.WHITE;
                break;
            case BISHOP1:
                setTexture(sceneEntities.textureList.get(TextureList.Key.BISHOP1));
                this.player = Player.BLACK;
                break;
            case KING:
                setTexture(sceneEntities.textureList.get(TextureList.Key.KING));
                this.player = Player.WHITE;
                break;
            case KING1:
                setTexture(sceneEntities.textureList.get(TextureList.Key.KING1));
                this.player = Player.BLACK;
                break;
            case KNIGHT:
                setTexture(sceneEntities.textureList.get(TextureList.Key.KNIGHT));
                this.player = Player.WHITE;
                break;
            case KNIGHT1:
                setTexture(sceneEntities.textureList.get(TextureList.Key.KNIGHT1));
                this.player = Player.BLACK;
                break;
            case PAWN:
                setTexture(sceneEntities.textureList.get(TextureList.Key.PAWN));
                this.player = Player.WHITE;
                break;
            case PAWN1:
                setTexture(sceneEntities.textureList.get(TextureList.Key.PAWN1));
                this.player = Player.BLACK;
                break;
            case QUEEN:
                setTexture(sceneEntities.textureList.get(TextureList.Key.QUEEN));
                this.player = Player.WHITE;
                break;
            case QUEEN1:
                setTexture(sceneEntities.textureList.get(TextureList.Key.QUEEN1));
                this.player = Player.BLACK;
                break;
            case ROOK:
                setTexture(sceneEntities.textureList.get(TextureList.Key.ROOK));
                this.player = Player.WHITE;
                break;
            case ROOK1:
                setTexture(sceneEntities.textureList.get(TextureList.Key.ROOK1));
                this.player = Player.BLACK;
                break;
            default:
                setTexture(sceneEntities.textureList.get(TextureList.Key.ERR));
                this.player = Player.WHITE;
                break;
        }
    }
    @Override
    public void update () {
        super.update();
        if(isClicked()) {
            // activate so to speak, now this ChessPiece is the "active" one on the board, tell that to ChessScene
            // tell ChessScene to delete any other indicators on the board (might have to use TAGS)
            sceneEntities.removeEntity("moveIndicator");
            // create indicators (where ChessScene lets me) to move the piece
            for (Square legalMove : new Array<Square>(movementStrategy())) {
                sceneEntities.createMoveIndicator(this, legalMove.getGridX(), legalMove.getGridY());
            }

            // if the piece deactivates for some reason it should remove its indicators
        }
    }

    // get and set methods
    public Type getPieceType(){
        return this.type;
    }
    public Player getPlayer(){
        return this.player;
    }

    // TODO muy tosco, arreglar
    public Array<Square> movementStrategy () {

        Map<String, Square> moves = new HashMap<>();
        moves.put("moveup", sceneEntities.createSquare(this.getGridX(),this.getGridY()+1));
        moves.put("moveupup", sceneEntities.createSquare(this.getGridX(),this.getGridY()+2));
        moves.put("moveupleft", sceneEntities.createSquare(this.getGridX()-1,this.getGridY()+1));
        moves.put("moveupright", sceneEntities.createSquare(this.getGridX()+1,this.getGridY()+1));

        // Piece logic here
        Array<Square> legalMove = new Array<>();

        if(moves.get("moveup").isEmpty()) { // is the next square empty?
            legalMove.add(moves.get("moveup"));

            if(this.getY() == 1 && moves.get("moveupup").isEmpty() || moves.get("moveupup").isChessPieceBlack()) { //is this pawn at Y=3 and 2 squares ahead is empty or is there a black piece?
                legalMove.add(moves.get("moveupup"));

            } else if (moves.get("moveup").isChessPieceBlack()) { // is the next square a black piece?
                legalMove.add(moves.get("moveup"));
            }
        }

        if (moves.get("moveupleft").isChessPieceBlack()) {
            legalMove.add(moves.get("moveupleft"));
        }
        if (moves.get("moveupright").isChessPieceBlack()) {
            legalMove.add(moves.get("moveupright"));
        }
        return legalMove; //this might make the entities null in the squares so might trow error
    }
}