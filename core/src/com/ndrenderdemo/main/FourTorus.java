package com.ndrenderdemo.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Vector3;
import com.ndrender.math.VectorN;
import com.ndrender.polytope.PerspectiveCameraN;
import com.ndrender.polytope.Polytope;
import com.ndrender.polytope.PolytopeRenderer;

public class FourTorus implements Screen {

	PerspectiveCamera camera;

	PolytopeRenderer edgescene;

	Polytope polytope;

	public FourTorus() {
		camera = new PerspectiveCamera();
	}

	@Override
	public void show() {
		polytope = Polytope.uvwTorus4(3, 1, 5, 5, 17);

		edgescene = new PolytopeRenderer(4);
		edgescene.eyeSeparation = 0.03F;

		PerspectiveCameraN camera4D = new PerspectiveCameraN(4);
		camera4D.position = new VectorN(0, 0, 0, -10);
		camera4D.rotate(1, 2, Math.PI/4);
		camera4D.update();
		
		edgescene.cameras[0] = camera4D;

		edgescene.leftColor = Main.leftColor;
		edgescene.rightColor = Main.rightColor;

		camera.near = 0.001F;

		camera.position.x = .2F;
		camera.position.y = -.2F;
		camera.position.z = -1.2F;

		camera.rotateAround(new Vector3(0, 0, 0), Vector3.Y, 65);

		camera.fieldOfView = 60;
	}

	@Override
	public void resize(int width, int height) {
		camera.lookAt(0, -.1F, 0);

		camera.viewportWidth = width;
		camera.viewportHeight = height;
		camera.update();

		// edgescene.set3DProjectionMatrix(camera.combined);
		edgescene.camera3d = camera;
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(Main.backgroundColor.r, Main.backgroundColor.g, Main.backgroundColor.b, Main.backgroundColor.a);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glLineWidth(2);

		polytope.rotate(0, 2, delta / 6);
		polytope.rotate(1, 3, -delta / 4);

		camera.rotateAround(new Vector3(0, 0, 0), Vector3.Y, delta * 3);

		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_ONE, GL20.GL_ONE);

		edgescene.begin(PolytopeRenderer.ANAGLYPH_BIT);
		edgescene.render(polytope);
		edgescene.end();
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

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
}
