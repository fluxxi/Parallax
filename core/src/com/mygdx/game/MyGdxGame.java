package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;

public class MyGdxGame extends ApplicationAdapter implements InputProcessor{
	private SpriteBatch batch;
	private ParallaxCamera camera;
	private Texture bgClose;
	private Texture bgMid;
	private Texture bgFar;
	private Texture bgFar2;
	private Texture bgFar3;
	private Texture bgFar4;
	private Texture bgFar5;
	final Vector3 curr = new Vector3();
	final Vector3 last = new Vector3(-1, -1, -1);
	final Vector3 delta = new Vector3();
	int sourceX = 0;
	int zaehler = 0;

	@Override
	public void create () {
		bgClose = new Texture(Gdx.files.internal("layer-7.png"));
		bgMid = new Texture(Gdx.files.internal("layer-6.png"));
		bgFar = new Texture(Gdx.files.internal("layer-5.png"));
		bgFar2 = new Texture(Gdx.files.internal("layer-4.png"));
		bgFar3 = new Texture(Gdx.files.internal("layer-3.png"));
		bgFar4 = new Texture(Gdx.files.internal("layer-2 the sun.png"));
		bgFar5 = new Texture(Gdx.files.internal("layer-1.png"));
		camera = new ParallaxCamera(1920,1080);
		batch = new SpriteBatch();
		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void render () {
		//clear screen
		batch.begin();



		//Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
		//Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// background layer, no parallax, centered around origin
		//batch.setProjectionMatrix(camera.calculateParallaxMatrix(0, 0));
		//batch.disableBlending();
		//batch.begin();
		//batch.draw(bgFar2, -(int)(bgFar2.getWidth() / 2), -(int)(bgFar2.getHeight() / 2));
		//batch.draw(bgFar3, -(int)(bgFar3.getWidth() / 2), -(int)(bgFar3.getHeight() / 2));
		//batch.draw(bgFar4, -(int)(bgFar4.getWidth() / 2), -(int)(bgFar4.getHeight() / 2));
		//batch.draw(bgFar5, -(int)(bgFar5.getWidth() / 2), -(int)(bgFar5.getHeight() / 2));
		//batch.draw(bgFar, -(int)(bgFar.getWidth() / 2), -(int)(bgFar.getHeight() / 2));
		//batch.end();
		//batch.enableBlending();

		batch.setProjectionMatrix(camera.calculateParallaxMatrix(.5f, .5f));
		/*//batch.begin();
		for (int i = 0; i < 9; i++) {
			batch.draw(bgFar5, i * bgFar4.getWidth() - 512, -512);
		}


		//batch.setProjectionMatrix(camera.calculateParallaxMatrix(0.25f, 0.25f));
		//batch.begin();
		for (int i = 0; i < 9; i++) {
			batch.draw(bgFar4, i * bgFar3.getWidth() - 512, -512);
		}
		//batch.end();

		//batch.setProjectionMatrix(camera.calculateParallaxMatrix(0.25f, 0.25f));
		//batch.begin();
		for (int i = 0; i < 9; i++) {
			batch.draw(bgFar3, i * bgFar2.getWidth() - 512, -712);
		}
		//batch.end();

		//batch.setProjectionMatrix(camera.calculateParallaxMatrix(0.25f, 0.25f));
		//batch.begin();
		for (int i = 0; i < 9; i++) {
			batch.draw(bgFar2, i * bgFar.getWidth() - 512, -512);
		}
		//batch.end();

		//batch.setProjectionMatrix(camera.calculateParallaxMatrix(0.25f, 0.25f));
		//batch.begin();
		for (int i = 0; i < 9; i++) {
			batch.draw(bgFar, i * bgClose.getWidth() - 512, -512);
		}
		//batch.end();


		//batch.setProjectionMatrix(camera.calculateParallaxMatrix(0.25f, 0.25f));
		//batch.begin();
		for (int i = 0; i < 9; i++) {
			batch.draw(bgMid, i * bgClose.getWidth() - 512, -512);


		}
		//batch.end();*/


		batch.setProjectionMatrix(camera.calculateParallaxMatrix(.5f, .5f));
		//batch.begin();
		for (int i = 0; i < 1; i++) {
			batch.draw(bgClose, sourceX  , -580);
			batch.draw(bgClose,  i * bgClose.getWidth() - 500, -100);
			sourceX = sourceX - 5;

			zaehler++;
		}

		if (zaehler==1000) {
			sourceX = 0;

		}



		batch.end();

	}


	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	//.. omitted empty methods ..//
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		last.set(-1, -1, -1);
		return false;
	}
	@Override
	public boolean touchDragged(int x, int y, int pointer) {
		camera.unproject(curr.set(x, y, 0));
		if (!(last.x == -1 && last.y == -1 && last.z == -1)) {
			camera.unproject(delta.set(last.x, last.y, 0));
			delta.sub(curr);
			camera.position.add(delta.x, delta.y, 0);
		}
		last.set(x, y, 0);
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}


	private class ParallaxCamera extends OrthographicCamera {
		Matrix4 parallaxView = new Matrix4();
		Matrix4 parallaxCombined = new Matrix4();
		Vector3 tmp = new Vector3();
		Vector3 tmp2 = new Vector3();

		public ParallaxCamera (float viewportWidth, float viewportHeight) {
			super(viewportWidth, viewportHeight);
		}

		public Matrix4 calculateParallaxMatrix (float parallaxX, float parallaxY) {
			update();
			tmp.set(position);
			tmp.x *= parallaxX;
			tmp.y *= parallaxY;

			parallaxView.setToLookAt(tmp, tmp2.set(tmp).add(direction), up);
			parallaxCombined.set(projection);
			Matrix4.mul(parallaxCombined.val, parallaxView.val);
			return parallaxCombined;
		}
	}
}