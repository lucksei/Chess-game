package com.mygdx.chess;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

import static com.mygdx.chess.ChessPiece.Type;

public class Chess extends ApplicationAdapter {

	static final int SQUARE_SIZE = 42; //pixels
	static final int WORLD_WIDTH = SQUARE_SIZE*8;
	static final int WORLD_HEIGHT = SQUARE_SIZE*9;

	public OrthographicCamera camera;
	public SpriteBatch batch;
	public TextureList textureList;
	public InputHandler inputHandler;
	public SceneEntities sceneEntities;

	ChessBoard chessBoard;
	@Override
	public void create () {

		camera = new OrthographicCamera(WORLD_WIDTH,WORLD_HEIGHT);
		camera.position.set(WORLD_WIDTH/2,WORLD_HEIGHT/2,0);
		inputHandler = new InputHandler();

		batch = new SpriteBatch();
		textureList = new TextureList();

		sceneEntities = new SceneEntities(this);

		// start of the game
		chessBoard = new ChessBoard(this);
		chessBoard.setY(WORLD_HEIGHT-SQUARE_SIZE*8);
		ChessPiece asdf = new ChessPiece(this, 1, 2, Type.PAWN);
		new ChessPiece(this, 3, 4, Type.PAWN);
		new ChessPiece(this, 2, 1, Type.PAWN);
		new ChessPiece(this, 3, 1, Type.PAWN);
		new ChessPiece(this, 2, 2, Type.BISHOP1);
		new MoveIndicator(this, 6, 6, asdf);
//		chessScene.addEntity(new UIEntity(this,100,100, 42, 42));
//		chessScene.addEntity(new UIEntity(this,100,200, 42, 42));
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
