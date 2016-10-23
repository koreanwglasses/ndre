package com.ndrenderdemo.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.ndrender.math.VectorN;
import com.ndrender.polytope.PerspectiveCameraN;
import com.ndrender.polytope.Polytope;
import com.ndrender.polytope.PolytopeRenderer;

public class FourTess implements Screen {
	PerspectiveCamera camera;

	PolytopeRenderer edgescene;

	Polytope polytope;
	Polytope polytope2;

	public FourTess() {
		camera = new PerspectiveCamera();

		camera.fieldOfView = 45F;

		camera.position.x = -2F;
		camera.position.y = 1;
		camera.position.z = 1;

		camera.update();
	}

	@Override
	public void show() {
		polytope = Polytope.tesseract();
		polytope.scale(2).translate(new VectorN(-1, -1, -1, -1));

		edgescene = new PolytopeRenderer(4);

		edgescene.leftColor = Main.leftColor;
		edgescene.rightColor = Main.rightColor;

		edgescene.eyeSeparation = 0.04F;

		PerspectiveCameraN camera4D = new PerspectiveCameraN(4);
		camera4D.position = new VectorN(0, 0, 0, -3);
		camera4D.update();

		edgescene.cameras[0] = camera4D;
	}

	double wscale = 0;
	double time = 0;
	double turnSpeed = 0;

	@Override
	public void render(float delta) {
		time += delta;

		Gdx.gl.glClearColor(Main.backgroundColor.r, Main.backgroundColor.g,
				Main.backgroundColor.b, Main.backgroundColor.a);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glLineWidth(5);

		polytope.rotate(0, 1, delta / 3);

		polytope2 = new Polytope(polytope);
		if (time <= 2) {
			polytope2.scale(2, 0);
			polytope2.scale(3, 0);
		} else if (time <= 4) {
			polytope2.scale(2, (time - 2) / 2);
			polytope2.scale(3, 0);
		} else if (time <= 8) {
			polytope2.scale(3, 0);
		} else if (time <= 10) {
			polytope2.scale(3, (time - 8) / 2);
		} else {
			turnSpeed = turnSpeed * (1 - delta / 2) + (delta / 2) * .5;
			polytope.rotate(2, 3, -delta * turnSpeed);
		}
		// edgescene.cameras[0].rotate(2, 3, delta / 2);
		edgescene.cameras[0].update();

		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_ONE, GL20.GL_ONE);

		edgescene.begin(PolytopeRenderer.ANAGLYPH_BIT);
		edgescene.render(polytope2);
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
