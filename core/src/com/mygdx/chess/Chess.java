package com.mygdx.chess;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import static com.mygdx.chess.ChessPiece.Type;

public class Chess extends ApplicationAdapter {

	static final int SQUARE_SIZE = 42; //pixels
	static final int WORLD_WIDTH = SQUARE_SIZE*9;
	static final int WORLD_HEIGHT = SQUARE_SIZE*9;

	public OrthographicCamera camera;
	public SpriteBatch batch;
	public TextureList textureList;
	public InputHandler inputHandler;
	public SceneEntities sceneEntities;
	public GameLogic gameLogic;

	EntityController chessBoard;
	public ChessPiece blackKing;
	public ChessPiece whiteKing;
	@Override
	public void create () {

		camera = new OrthographicCamera(WORLD_WIDTH,WORLD_HEIGHT);
		camera.position.set(WORLD_WIDTH/2,WORLD_HEIGHT/2,0);
		inputHandler = new InputHandler();

		batch = new SpriteBatch();
		textureList = new TextureList();

		sceneEntities = new SceneEntities(this);
		gameLogic = new GameLogic(this);

		// start of the game
		chessBoard = new EntityController(this, new Entity(this));
		chessBoard.setTexture(textureList.get(TextureList.Key.BOARD));
//		chessBoard.setY(WORLD_HEIGHT-SQUARE_SIZE*8);

		new ChessPiece(this, 0, 0, Type.ROOK, ChessPiece.Player.WHITE);
		new ChessPiece(this, 1, 0, Type.KNIGHT, ChessPiece.Player.WHITE);
		new ChessPiece(this, 2, 0, Type.BISHOP, ChessPiece.Player.WHITE);
		new ChessPiece(this, 3, 0, Type.QUEEN, ChessPiece.Player.WHITE);
		whiteKing = new ChessPiece(this, 4, 0, Type.KING, ChessPiece.Player.WHITE);
		new ChessPiece(this, 5, 0, Type.BISHOP, ChessPiece.Player.WHITE);
		new ChessPiece(this, 6, 0, Type.KNIGHT, ChessPiece.Player.WHITE);
		new ChessPiece(this, 7, 0, Type.ROOK, ChessPiece.Player.WHITE);

		for (int x = 0; x < 8; x++) {
			new ChessPiece(this, x, 1, Type.PAWN, ChessPiece.Player.WHITE);
		}

		new ChessPiece(this, 0, 7, Type.ROOK, ChessPiece.Player.BLACK);
		new ChessPiece(this, 1, 7, Type.KNIGHT, ChessPiece.Player.BLACK);
		new ChessPiece(this, 2, 7, Type.BISHOP, ChessPiece.Player.BLACK);
		new ChessPiece(this, 3, 7, Type.QUEEN, ChessPiece.Player.BLACK);
		blackKing = new ChessPiece(this, 4, 7, Type.KING, ChessPiece.Player.BLACK);
		new ChessPiece(this, 5, 7, Type.BISHOP, ChessPiece.Player.BLACK);
		new ChessPiece(this, 6, 7, Type.KNIGHT, ChessPiece.Player.BLACK);
		new ChessPiece(this, 7, 7, Type.ROOK, ChessPiece.Player.BLACK);

		for (int x = 0; x < 8; x++) {
			new ChessPiece(this, x, 6, Type.PAWN, ChessPiece.Player.BLACK);
		}

		gameLogic.storeCurrentBoardState();
	}

	@Override
	public void render () {
		// handle logic stuff
		camera.update();
		batch.setProjectionMatrix(camera.combined);

		inputHandler.update(camera);
		sceneEntities.update();

		// rendering stuff
		ScreenUtils.clear(Color.valueOf("23272a"));
		batch.begin();
		sceneEntities.render();
		batch.end();

	}
	
	@Override
	public void dispose () {
		textureList.disposeAll();
		batch.dispose();
	}
}
