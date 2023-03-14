package com.mygdx.chess;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import java.util.HashMap;
import java.util.Map;

public class ChessPiece extends Entity implements MovementStrategy {

    public enum Type { BISHOP, BISHOP1, KING, KING1, KNIGHT, KNIGHT1, PAWN, PAWN1, QUEEN, QUEEN1, ROOK, ROOK1 }
    public enum Player { WHITE, BLACK }

    private Type type;
    private Player player;

    public ChessPiece (Chess game, Type type, int x, int y) {
        super(game, x, y);
        setTag("chessPiece");
        this.type = type;
        setClickeable(true);
        switch (type) {
            case BISHOP:
                setTexture(game.textureList.get(TextureList.Key.BISHOP));
                this.player = Player.WHITE;
                break;
            case BISHOP1:
                setTexture(game.textureList.get(TextureList.Key.BISHOP1));
                this.player = Player.BLACK;
                break;
            case KING:
                setTexture(game.textureList.get(TextureList.Key.KING));
                this.player = Player.WHITE;
                break;
            case KING1:
                setTexture(game.textureList.get(TextureList.Key.KING1));
                this.player = Player.BLACK;
                break;
            case KNIGHT:
                setTexture(game.textureList.get(TextureList.Key.KNIGHT));
                this.player = Player.WHITE;
                break;
            case KNIGHT1:
                setTexture(game.textureList.get(TextureList.Key.KNIGHT1));
                this.player = Player.BLACK;
                break;
            case PAWN:
                setTexture(game.textureList.get(TextureList.Key.PAWN));
                this.player = Player.WHITE;
                break;
            case PAWN1:
                setTexture(game.textureList.get(TextureList.Key.PAWN1));
                this.player = Player.BLACK;
                break;
            case QUEEN:
                setTexture(game.textureList.get(TextureList.Key.QUEEN));
                this.player = Player.WHITE;
                break;
            case QUEEN1:
                setTexture(game.textureList.get(TextureList.Key.QUEEN1));
                this.player = Player.BLACK;
                break;
            case ROOK:
                setTexture(game.textureList.get(TextureList.Key.ROOK));
                this.player = Player.WHITE;
                break;
            case ROOK1:
                setTexture(game.textureList.get(TextureList.Key.ROOK1));
                this.player = Player.BLACK;
                break;
            default:
                setTexture(game.textureList.get(TextureList.Key.ERR));
                this.player = Player.WHITE;
                break;
        }
    }

    public Type getPieceType(){
        return this.type;
    }
    public Player getPlayer(){
        return this.player;
    }
    public Player getPlayer (Array<ChessPiece> chessPieces) {
        for (ChessPiece piece : chessPieces) {
            return piece.getPlayer(); // should only return the first thing it finds i think...
        }
        return null;
    }
    /* while stupid, since no more than 1 piece can be in the same spot,
i left it like it is since i didn't want to complicate the code too much,
getting info about it is for the other methods, this one is just for searching
so its done only once */
    public Array<ChessPiece> getPieceFromSquare(Array<ChessPiece> chessPieces, Vector2 position) {
        Array<ChessPiece> chessPiecesInSquare = new Array<>();
        for (ChessPiece piece : chessPieces) {
            if (piece.getX() == position.x && piece.getY() == position.y) {
                chessPiecesInSquare.add(piece);
            }
        }
        return chessPiecesInSquare;
    }

    public Array<Square> movementStrategy () {
        Array<Entity> entities = game.chessScene.getEntitiesCopy();

        Map<String, Square> moves = new HashMap<>();
        moves.put("moveup", new Square(entities,this.getX(),this.getY(),0,1));
        moves.put("moveupup", new Square(entities,this.getX(),this.getY(),0,2));
        moves.put("moveupleft", new Square(entities,this.getX(),this.getY(),-1,1));
        moves.put("moveupright", new Square(entities,this.getX(),this.getY(),1,1));

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



    @Override
    public void update () {
        if(isClicked()) {
            // activate so to speak, now this ChessPiece is the "active" one on the board, tell that to ChessScene
            // tell ChessScene to delete any other indicators on the board (might have to use TAGS)
            game.chessScene.removeEntity("moveIndicator");
            // create indicators (where ChessScene lets me) to move the piece
            for (Square legalMove : new Array<Square>(movementStrategy())) {
                game.chessScene.createMoveIndicator(this, legalMove.x, legalMove.y);
            }

            // if the piece deactivates for some reason it should remove its indicators
        }
    }



}