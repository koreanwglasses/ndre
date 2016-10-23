package com.ndrenderdemo.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.ndrender.math.ParametricEq;
import com.ndrender.math.VectorN;
import com.ndrender.polytope.PerspectiveCameraN;
import com.ndrender.polytope.Polytope;
import com.ndrender.polytope.PolytopeRenderer;
import com.ndrender.render.Gradient;

public class Equation implements Screen {
	int dim;

	PerspectiveCamera camera;

	PolytopeRenderer edgescene;

	Polytope polytope;
	Polytope polytope2;

	Gradient gradient;

	public Equation() {
		camera = new PerspectiveCamera();

		camera.fieldOfView = 45F;

		camera.position.x = -4F;
		camera.position.y = 2;
		camera.position.z = 2;

		camera.update();
	}

	@Override
	public void show() {
		// @formatter:off

		// Hyperboloid
		// polytope = new ParametricEq(
		// (t) -> t[0],
		// (t) -> t[1],
		// (t) -> 3 * (Math.sqrt(t[0] * t[0] + t[1] * t[1] + 1) - 1.366)
		// ).bake(2, .1, new double[] { -1, -1 }, new double[] { 1 , 1});
		// polytope.scale(1);

		// Mobius Strip
//		 polytope = new ParametricEq(
//		 (t) -> (1 + t[1] / 2 * Math.cos(t[0] / 2)) * Math.cos(t[0]),
//		 (t) -> (1 + t[1] / 2 * Math.cos(t[0] / 2)) * Math.sin(t[0]),
//		 (t) -> t[1] / 2 * Math.sin(t[0] / 2)
//		 ).bake(2, new double[] {Math.PI / 30, 0.2}, new double[] {0, -1}, new
//		 double[] {2 * Math.PI, 1});
//		 polytope.scale(1.2);

		// Torus
//		 double R = 2;
//		 double r = 1;
//		 polytope = new ParametricEq(
//		 (t) -> (R + r * Math.cos(t[0])) * Math.cos(t[1]),
//		 (t) -> (R + r * Math.cos(t[0])) * Math.sin(t[1]),
//		 (t) -> r * Math.sin(t[0])
//		 ).bake(2, new double[] {Math.PI / 6, Math.PI / 12}, new double[] {0,
//		 0}, new double[] {2 * Math.PI, 2 * Math.PI});
//		 polytope.scale(0.5);

		// 3-Torus
//		 double r0 = 1;
//		 double r1 = 2;
//		 double r2 = 4;
//		 polytope = new ParametricEq(
//		 (t) -> (r2 + (r1 + r0 * Math.cos(t[0])) * Math.cos(t[1])) *
//		 Math.cos(t[2]),
//		 (t) -> (r2 + (r1 + r0 * Math.cos(t[0])) * Math.cos(t[1])) *
//		 Math.sin(t[2]),
//		 (t) -> (r1 + r0 * Math.cos(t[0])) * Math.sin(t[1]),
//		 (t) -> r0 * Math.sin(t[0])
//		 ).bake(3, new double[] {Math.PI / 3, Math.PI / 4, Math.PI / 5}, new
//		 double[] {0, 0, 0}, new double[] {2 * Math.PI, 2 * Math.PI, 2 *
//		 Math.PI});
//		 polytope.scale(0.5);

		// 2-Sphere
//		gradient = new Gradient(2);
//		gradient.setAxisGradient(0, Color.RED, Color.CYAN, Color.CYAN, Color.RED);
//		gradient.setAxisGradient(1, Color.GREEN, Color.MAGENTA, Color.MAGENTA, Color.GREEN);
//		 polytope = new ParametricEq(
//		 (t) -> Math.sin(t[0]) * Math.cos(t[1]),
//		 (t) -> Math.cos(t[0]) * Math.cos(t[1]),
//		 (t) -> Math.sin(t[1])
//		 ).bake(2, new double[]{Math.PI / 6, Math.PI / 6}, new double[] { 0, Math.PI / -2 }, new double[]
//		 { 2 * Math.PI , Math.PI / 2}, gradient);
//		 polytope.scale(1.5);

//		polytope = new ParametricEq(
//  		 (t) -> t[0] + t[1],
//		 (t) -> t[0] * t[0] + t[1] * t[1],
//		 (t) -> t[0] * t[0] - t[1] * t[1]
//		 ).bake(2, new double[]{.5, .5}, new double[] { -1, -1 }, new double[]
//		 { 1, 1});
//		 polytope.translate(new VectorN(-1, -1, -1));
//		 polytope.scale(1);

		 // Klein Bottle
		gradient = new Gradient(2);
		gradient.setAxisGradient(0, Color.RED, Color.CYAN, Color.CYAN, Color.RED);
		gradient.setAxisGradient(1, Color.GREEN, Color.MAGENTA, Color.MAGENTA, Color.GREEN);
		 double R = 1.3;
		 double P = 1.7;
		 double e = 0.4;
		 polytope = new ParametricEq(
		 (t) -> R * (Math.cos(t[0] / 2) * Math.cos(t[1]) - Math.sin(t[0] / 2)
		 * Math.sin(2 * t[1])),
		 (t) -> R * (Math.sin(t[0] / 2) * Math.cos(t[1]) - Math.cos(t[0] / 2)
		 * Math.sin(2 * t[1])),
		 (t) -> P * Math.cos(t[0]) * (1 + e * Math.sin(t[1])),
		 (t) -> P * Math.sin(t[0]) * (1 + e * Math.sin(t[1]))
		 ).bake(2, new double[] {Math.PI / 9, Math.PI / 9}, new double[] { 0 , 0 }, new double[] { 2 *
		 Math.PI , 2 * Math.PI}, gradient);
		 polytope.scale(.8);

		// 3-Sphere
//		 gradient = new Gradient(3);
//		 gradient.setAxisGradient(0, Color.RED, Color.CYAN);
//		 gradient.setAxisGradient(1, Color.GREEN, Color.MAGENTA);
//		 gradient.setAxisGradient(2, Color.BLUE, Color.YELLOW);
//		 polytope = new ParametricEq((t) -> Math.cos(t[0]),
//		 (t) -> Math.sin(t[0]) * Math.cos(t[1]), (t) -> Math.sin(t[0])
//		 * Math.sin(t[1]) * Math.cos(t[2]),
//		 (t) -> Math.sin(t[0]) * Math.sin(t[1]) * Math.sin(t[2])).bake(
//		 3, new double[] { Math.PI / 6, Math.PI / 6, Math.PI / 6 },
//		 new double[] { 0, 0, 0 }, new double[] { Math.PI, Math.PI,
//		 2 * Math.PI }, gradient);
//		 polytope.scale(2);

//		 Flat-Torus
//		gradient = new Gradient(2);
//		gradient.setAxisGradient(0, Color.RED, Color.CYAN, Color.CYAN, Color.RED);
//		gradient.setAxisGradient(1, Color.GREEN, Color.MAGENTA, Color.MAGENTA, Color.GREEN);
//		polytope = new ParametricEq(
//				(t) -> Math.cos(t[0] + t[1]),
//				(t) -> Math.sin(t[0] + t[1]),
//				(t) -> Math.cos(t[0] - t[1]),
//				(t) -> Math.sin(t[0] - t[1])
//		).bake(2, new double[] { Math.PI / 12,
//				Math.PI / 12 }, new double[] { 0, 0 }, new double[] {
//				2 * Math.PI, Math.PI }, gradient);
//		polytope.scale(1.5);

		// @formatter:on

		dim = polytope.getDimension();

		edgescene = new PolytopeRenderer(dim);

		edgescene.leftColor = Main.leftColor;
		edgescene.rightColor = Main.rightColor;

		edgescene.eyeSeparation = 0.08F;

		for (int i = 4; i <= dim; i++) {
			PerspectiveCameraN camera = new PerspectiveCameraN(i);
			camera.position = new VectorN(i);
			camera.position.q[i - 1] = 8;
			camera.rotate(i - 1, 1, Math.PI / 6);
			camera.update();

			edgescene.cameras[i - 4] = camera;
		}
	}

	@Override
	public void render(float delta) {

		Gdx.gl.glClearColor(Main.backgroundColor.r, Main.backgroundColor.g, Main.backgroundColor.b,
				Main.backgroundColor.a);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glLineWidth(Main.lineWidth);

		polytope.rotate(0, 1, delta / 3);
		polytope.rotate(1, 2, -delta / 3.5);

		for (int i = 4; i <= dim; i++) {
			edgescene.cameras[i - 4].rotate(i - 2, i - 1, delta / (.5 * i));
			edgescene.cameras[i - 4].update();
		}

		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_ONE, GL20.GL_ONE);

		edgescene.begin();// PolytopeRenderer.ANAGLYPH_BIT);
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
