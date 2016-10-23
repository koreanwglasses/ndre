package com.ndrenderdemo.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.ndrender.math.VectorN;
import com.ndrender.polytope.*;

import java.util.ArrayList;
import java.util.List;

public class NTess implements Screen, InputProcessor{
	int dim;

	public int lineWidth;
	public int renderMode;
	public Color backgroundColor;
	public Color leftColor;
	public Color rightColor;
	
	PerspectiveCamera camera;

	PolytopeRenderer edgescene;

	Polytope polytope;
	Polytope polytope2;

	int turnXY = 0;
	int turnXZ = 0;
	int turnXW = 0;
	int turnYZ = 0;
	int turnYW = 0;
	int turnZW = 0;

	boolean paused = true;

	public NTess(int dim) {
		this.dim = dim;

		camera = new PerspectiveCamera();

		camera.fieldOfView = 45F;

		camera.position.x = -6F;
		camera.position.y = 0;
		camera.position.z = 0;

		camera.update();

		lineWidth = Main.lineWidth;
		backgroundColor = Main.backgroundColor;
		
		renderMode = Main.renderMode;
		
		leftColor = Main.leftColor;
		rightColor = Main.rightColor;

		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void show() {
		polytope = Polytope.nCube(dim);

		polytope.scale(2);
		VectorN displace = new VectorN(dim);
		for (int i = 0; i < dim; i++)
			displace.q[i] = -1;
		polytope.translate(displace);

		edgescene = new PolytopeRenderer(dim);

		edgescene.leftColor = leftColor;
		edgescene.rightColor = rightColor;

		edgescene.eyeSeparation = 0.05F;

		for (int i = dim; i >= 4; i--) {
			PerspectiveCameraN camera = new PerspectiveCameraN(i);
			camera.origin = new VectorN(i);
			camera.position = new VectorN(i);
			camera.setFOV(Math.PI / 2);
			camera.position.q[i - 1] = 1 / Math.tan(camera.FOV / 2) + 0.5;
			camera.update();

			edgescene.cameras[i - 4] = camera;
		}
		edgescene.cameras[dim - 4].position.q[dim - 1] = (1 + 1) / Math.tan(((PerspectiveCameraN)edgescene.cameras[dim - 4]).FOV / 2) + 1;
		edgescene.cameras[dim - 4].update();

		camera.fieldOfView = 45;
		camera.position.x = (float) ((.5+1) / Math.tan(camera.fieldOfView / 2));

		polytope.rotate(1,3,Math.PI / 4);
	}

	@Override
	public void render(float delta) {

		Gdx.gl.glClearColor(backgroundColor.r, backgroundColor.g,
				backgroundColor.b, backgroundColor.a);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glLineWidth(lineWidth);

//		polytope.rotate(0, 1, delta / 3);
//		polytope.rotate(1, 2, -delta / 3.5);

		if(!paused)
			for(int i = 0; i < dim - 1; i++)
				polytope.rotate(i, i + 1, delta / 2.5);

		polytope.rotate(0, 1, turnXY * delta / 2.5);
		polytope.rotate(0, 2, turnXZ * delta / 2.5);
		polytope.rotate(0, 3, turnXW * delta / 2.5);
		polytope.rotate(1, 2, turnYZ * delta / 2.5);
		polytope.rotate(1, 3, turnYW * delta / 2.5);
		polytope.rotate(2, 3, turnZW * delta / 2.5);
		
//		for (int i = 4; i <= dim; i++) {
//			edgescene.cameras[i - 4].rotate(i - 2, i - 1, delta / (.5 * i));
//			edgescene.cameras[i - 4].update();
//		}

		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_ONE, GL20.GL_ONE);

		edgescene.begin(renderMode);
		edgescene.render(polytope);
		edgescene.end();
	}

	@Override
	public void resize(int width, int height) {
		camera.lookAt(0, 0, 0);

		camera.viewportWidth = width;
		camera.viewportHeight = height;
		camera.update();

		edgescene.camera3d = camera;
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean keyDown(int keycode) {
		switch(keycode) {
			case Input.Keys.Q:
				turnXY = 1;
				break;
			case Input.Keys.A:
				turnXY = -1;
				break;

			case Input.Keys.W:
				turnXZ = 1;
				break;
			case Input.Keys.S:
				turnXZ = -1;
				break;

			case Input.Keys.E:
				turnXW = 1;
				break;
			case Input.Keys.D:
				turnXW = -1;
				break;

			case Input.Keys.R:
				turnYZ = 1;
				break;
			case Input.Keys.F:
				turnYZ = -1;
				break;

			case Input.Keys.T:
				turnYW = 1;
				break;
			case Input.Keys.G:
				turnYW = -1;
				break;

			case Input.Keys.Y:
				turnZW = 1;
				break;
			case Input.Keys.H:
				turnZW = -1;
				break;
			default:
				return false;
		}
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		switch(keycode) {
			case Input.Keys.SPACE:
				paused = !paused;
				break;

			case Input.Keys.Q:
				turnXY = 0;
				break;
			case Input.Keys.A:
				turnXY = 0;
				break;

			case Input.Keys.W:
				turnXZ = 0;
				break;
			case Input.Keys.S:
				turnXZ = 0;
				break;

			case Input.Keys.E:
				turnXW = 0;
				break;
			case Input.Keys.D:
				turnXW = 0;
				break;

			case Input.Keys.R:
				turnYZ = 0;
				break;
			case Input.Keys.F:
				turnYZ = 0;
				break;

			case Input.Keys.T:
				turnYW = 0;
				break;
			case Input.Keys.G:
				turnYW = 0;
				break;

			case Input.Keys.Y:
				turnZW = 0;
				break;
			case Input.Keys.H:
				turnZW = 0;
				break;
			default:
				return false;
		}
		return true;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
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
}
