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
	static final int WORLD_HEIGHT = SQUARE_SIZE*8;

	public OrthographicCamera camera;
	public Vector3 mousePos;
	public SpriteBatch batch;
	public TextureList textureList;
	public ChessScene chessScene;
	
	@Override
	public void create () {

		camera = new OrthographicCamera(WORLD_WIDTH,WORLD_HEIGHT);
		camera.position.set(WORLD_WIDTH/2,WORLD_HEIGHT/2,0);
		mousePos = new Vector3(); // TODO goes in an input handler thingy

		batch = new SpriteBatch();
		textureList = new TextureList();

		chessScene = new ChessScene(this);
		chessScene.createChessPiece(Type.PAWN, 1, 2);
		chessScene.createChessPiece(Type.PAWN, 3, 4);
		chessScene.createChessPiece(Type.PAWN, 2,1);
		chessScene.createChessPiece(Type.PAWN, 3,1);
		chessScene.createChessPiece(Type.BISHOP1, 2,2);
		chessScene.addEntity(new UIEntity(this,100,100, 42, 42));
		chessScene.addEntity(new UIEntity(this,100,200, 42, 42));
	}

	@Override
	public void render () {
		// handle logic stuff
		// TODO handle input
		chessScene.update();

		// handle camera
		camera.update();
		batch.setProjectionMatrix(camera.combined);

		// handle mouse TODO move this to an input handler of some sort / observer pattern
		mousePos.set(Gdx.input.getX(),Gdx.input.getY(), 0f);
		camera.unproject(mousePos);

		// rendering stuff
		ScreenUtils.clear(Color.valueOf("23272a"));
		batch.begin();
		batch.draw(textureList.get(TextureList.Key.BOARD),0,0); // TODO move to ChessScene later
		chessScene.render();
		batch.end();


	}
	
	@Override
	public void dispose () {
		textureList.disposeAll();
		batch.dispose();
	}
}
