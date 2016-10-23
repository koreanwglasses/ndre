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
import com.ndrender.polytope.chromadepth.ChromaDepth;

public class HypercubeDemo implements Screen {
	PerspectiveCamera camera;

	PolytopeRenderer edgescene;

	Polytope polytope;

	public HypercubeDemo() {

	}

	@Override
	public void show() {
		camera = new PerspectiveCamera();

		camera.fieldOfView = 45F;

		camera.position.x = 0;
		camera.position.y = 0;
		camera.position.z = -4F;

		camera.lookAt(new Vector3(0, 0, 0));

		camera.update();

		polytope = Polytope.nCube(8);
		polytope.scale(2);
		polytope.translate(new VectorN(-1, -1, -1, -1, -1, -1, -1, -1));
		polytope.origin = new VectorN(8);

		edgescene = new PolytopeRenderer(8);

		for (int i = 8; i >= 4; i--) {
			PerspectiveCameraN camera = new PerspectiveCameraN(i);
			camera.origin = new VectorN(i);
			camera.position = new VectorN(i);
			camera.setFOV(Math.PI / 4);
			camera.position.q[i - 1] = 1 / Math.tan(camera.FOV / 2);
			camera.update();

			edgescene.cameras[i - 4] = camera;
		}
		edgescene.cameras[8 - 4].position.q[8 - 1] = (1 + 1)
				/ Math.tan(((PerspectiveCameraN) edgescene.cameras[8 - 4]).FOV / 2) + Math.sqrt(8);
		edgescene.cameras[8 - 4].update();

		camera.position.z = (float) ((1+1) / Math.tan(camera.fieldOfView / 2));
	}

	float time = 0;
	boolean forward = true;
	
	@Override
	public void render(float delta) {

		//if(time > 28 || time < 0) forward = !forward;
		time += forward ? delta: -delta;

		Polytope render = new Polytope(polytope);

		if (time < 2.5) {
			render.scale(7, 0);
			render.scale(6, 0);
			render.scale(5, 0);
			render.scale(4, 0);
			render.scale(3, 0);
			render.scale(2, 0);
			render.scale(1, 0);
		} else if (time < 3.5) {
			render.scale(7, 0);
			render.scale(6, 0);
			render.scale(5, 0);
			render.scale(4, 0);
			render.scale(3, 0);
			render.scale(2, 0);
			render.scale(1, time - 2.5);
		} else if (time < 6) {
			render.scale(7, 0);
			render.scale(6, 0);
			render.scale(5, 0);
			render.scale(4, 0);
			render.scale(3, 0);
			render.scale(2, 0);

			polytope.rotate(0, 1, delta / 1.5);
		} else if (time < 7) {
			render.scale(7, 0);
			render.scale(6, 0);
			render.scale(5, 0);
			render.scale(4, 0);
			render.scale(3, 0);
			render.scale(2, time - 6);

			polytope.rotate(0, 1, delta / 1.5);
		} else if (time < 9.5) {
			render.scale(7, 0);
			render.scale(6, 0);
			render.scale(5, 0);
			render.scale(4, 0);
			render.scale(3, 0);

			polytope.rotate(0, 1, delta / 1.5);
			polytope.rotate(1, 2, delta / 1.5);
		} else if (time < 10.5) {
			render.scale(7, 0);
			render.scale(6, 0);
			render.scale(5, 0);
			render.scale(4, 0);
			render.scale(3, time - 9.5);

			polytope.rotate(0, 1, delta / 1.5);
			polytope.rotate(1, 2, delta / 1.5);
		} else if (time < 13) {
			render.scale(7, 0);
			render.scale(6, 0);
			render.scale(5, 0);
			render.scale(4, 0);

			polytope.rotate(0, 1, delta / 1.5);
			polytope.rotate(1, 2, delta / 1.5);
			polytope.rotate(2, 3, delta / 1.5);
		} else if (time < 14) {
			render.scale(7, 0);
			render.scale(6, 0);
			render.scale(5, 0);
			render.scale(4, time - 13);

			polytope.rotate(0, 1, delta / 1.5);
			polytope.rotate(1, 2, delta / 1.5);
			polytope.rotate(2, 3, delta / 1.5);
		} else if (time < 16.5) {
			render.scale(7, 0);
			render.scale(6, 0);
			render.scale(5, 0);

			polytope.rotate(0, 1, delta / 1.5);
			polytope.rotate(1, 2, delta / 1.5);
			polytope.rotate(2, 3, delta / 1.5);
			polytope.rotate(3, 4, delta / 1.5);
		} else if (time < 17.5) {
			render.scale(7, 0);
			render.scale(6, 0);
			render.scale(5, time - 16.5);

			polytope.rotate(0, 1, delta / 1.5);
			polytope.rotate(1, 2, delta / 1.5);
			polytope.rotate(2, 3, delta / 1.5);
			polytope.rotate(3, 4, delta / 1.5);
		} else if (time < 20) {
			render.scale(7, 0);
			render.scale(6, 0);

			polytope.rotate(0, 1, delta / 1.5);
			polytope.rotate(1, 2, delta / 1.5);
			polytope.rotate(2, 3, delta / 1.5);
			polytope.rotate(3, 4, delta / 1.5);
			polytope.rotate(4, 5, delta / 1.5);
		} else if (time < 21) {
			render.scale(7, 0);
			render.scale(6, time - 20);

			polytope.rotate(0, 1, delta / 1.5);
			polytope.rotate(1, 2, delta / 1.5);
			polytope.rotate(2, 3, delta / 1.5);
			polytope.rotate(3, 4, delta / 1.5);
			polytope.rotate(4, 5, delta / 1.5);
		} else if (time < 23.5) {
			render.scale(7, 0);

			polytope.rotate(0, 1, delta / 1.5);
			polytope.rotate(1, 2, delta / 1.5);
			polytope.rotate(2, 3, delta / 1.5);
			polytope.rotate(3, 4, delta / 1.5);
			polytope.rotate(4, 5, delta / 1.5);
			polytope.rotate(5, 6, delta / 1.5);
		} else if (time < 24.5) {
			render.scale(7, time - 23.5);

			polytope.rotate(0, 1, delta / 1.5);
			polytope.rotate(1, 2, delta / 1.5);
			polytope.rotate(2, 3, delta / 1.5);
			polytope.rotate(3, 4, delta / 1.5);
			polytope.rotate(4, 5, delta / 1.5);
			polytope.rotate(5, 6, delta / 1.5);
		} else {
			polytope.rotate(0, 1, delta / 1.5);
			polytope.rotate(1, 2, delta / 1.5);
			polytope.rotate(2, 3, delta / 1.5);
			polytope.rotate(3, 4, delta / 1.5);
			polytope.rotate(4, 5, delta / 1.5);
			polytope.rotate(5, 6, delta / 1.5);
			polytope.rotate(6, 7, delta / 1.5);
		}
		
		Gdx.gl.glClearColor(Main.backgroundColor.r, Main.backgroundColor.g, Main.backgroundColor.b,
				Main.backgroundColor.a);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glLineWidth(Main.lineWidth);

		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_ONE, GL20.GL_ONE);

		edgescene.begin();//PolytopeRenderer.ANAGLYPH_BIT);
		edgescene.render(render);
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
