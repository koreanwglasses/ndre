package com.ndrenderdemo.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.ndrender.math.VectorN;
import com.ndrender.polytope.PerspectiveCameraN;
import com.ndrender.polytope.Polytope;
import com.ndrender.polytope.PolytopeRenderer;

public class FiveTess implements Screen {
	PerspectiveCamera camera;

	PolytopeRenderer edgescene;

	Polytope polytope;
	Polytope polytope2;

	public FiveTess() {
		camera = new PerspectiveCamera();

		camera.fieldOfView = 45F;

		camera.position.x = -2F;
		camera.position.y = 1;
		camera.position.z = 1;

		camera.update();
	}

	@Override
	public void show() {
		polytope = Polytope.nCube(5);
		polytope.scale(4).translate(new VectorN(-2, -2, -2, -2, -2));

		edgescene = new PolytopeRenderer(5);

		edgescene.leftColor = Main.leftColor;
		edgescene.rightColor = Main.rightColor;

		edgescene.eyeSeparation = 0.04F;

		PerspectiveCameraN camera4D = new PerspectiveCameraN(4);
		camera4D.position = new VectorN(0, 0, 0, -2.5);
		camera4D.rotate(3, 1, Math.PI / 6);
		camera4D.update();

		PerspectiveCameraN camera5D = new PerspectiveCameraN(5);
		camera5D.position = new VectorN(0, 0, 0, 0, -3);
		camera5D.rotate(4, 1, Math.PI / 6);
		camera5D.update();

		edgescene.cameras[0] = camera4D;
		edgescene.cameras[1] = camera5D;
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(Main.backgroundColor.r, Main.backgroundColor.g,
				Main.backgroundColor.b, Main.backgroundColor.a);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glLineWidth(5);

		polytope.rotate(0, 1, delta / 3);
		 polytope.rotate(1, 2, -delta / 3.5);

		edgescene.cameras[0].rotate(2, 3, delta / 2.5);
		edgescene.cameras[0].update();
		
		edgescene.cameras[1].rotate(3, 4, delta / 2);
		edgescene.cameras[1].update();

		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_ONE, GL20.GL_ONE);

		edgescene.begin(PolytopeRenderer.ANAGLYPH_BIT);
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
