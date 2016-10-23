package com.ndrender.polytope;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.glutils.ImmediateModeRenderer20;
import com.badlogic.gdx.math.Vector3;
import com.ndrender.render.CameraN;

public class PolytopeRenderer {

	public static final int ANAGLYPH_BIT = 1 << 0;

	transient final int dim;

	private int mode;

	ImmediateModeRenderer20 ir;

	/**
	 * The separation of the right and left cameras from the base camera
	 */
	public float eyeSeparation = .04F;

	/**
	 * The color of the left 3D glasses lens
	 */
	public Color leftColor = new Color(.1533F, .7098F, .3922F, .75F);

	/**
	 * The color of the right 3D glasses lens
	 */
	public Color rightColor = new Color(.8467F, .2902F, .6078F, .5F);

	public Camera camera3d;
	public CameraN[] cameras;

	public PolytopeRenderer(int dim) {
		this.dim = dim;
		cameras = new CameraN[dim - 3];
		camera3d = new PerspectiveCamera();

		ir = new ImmediateModeRenderer20(false, true, 0);
	}

	protected ArrayList<Edge3> projectedQueue;

	protected Polytope project(Polytope polytope) {
		// inter-phase polytope
		Polytope ipp = new Polytope(polytope);
		while (ipp.dim > 3) {
			ipp.project(cameras[ipp.dim - 4]);
		}
		return ipp;
	}

	protected ArrayList<Polytope> queue;

	public void begin() {
		begin(0);
	}

	public void begin(int mode) {
		this.mode = mode;
		queue = new ArrayList<Polytope>();
		projectedQueue = new ArrayList<Edge3>();
	}

	public void render(Polytope polytope) {
		queue.add(polytope);
	}

	public static final int vertCycle = 4096;

	protected void project() {
		for (Polytope polytope : queue) {
			Polytope ipp = project(polytope);
			for (Edge edge : ipp.edges) {
				projectedQueue.add(edge.toEdge3());
			}
		}
	}

	protected void draw() {
		// ANAGLYPH_BIT == 0
		if ((mode & ANAGLYPH_BIT) == 0) {
			ir.begin(camera3d.combined, GL20.GL_LINES);

			int vertexCount = 0;
			for (Edge3 edge : projectedQueue) {
				if (vertexCount > vertCycle) {
					ir.end();
					ir.begin(camera3d.combined, GL20.GL_LINES);
					vertexCount = 0;
				}
				ir.color(edge.end0.color);
				ir.vertex(edge.end0.position.x, edge.end0.position.y, edge.end0.position.z);
				ir.color(edge.end1.color);
				ir.vertex(edge.end1.position.x, edge.end1.position.y, edge.end1.position.z);
				vertexCount += 2;
			}
			ir.end();
			// ANAGLYPH_BIT == 1
		} else if ((mode & ANAGLYPH_BIT) != 0) {
			Vector3 eyeAxis = camera3d.direction.cpy();
			eyeAxis.y = 0;
			float tempz = eyeAxis.z;
			eyeAxis.z = -eyeAxis.x;
			eyeAxis.x = tempz;
			eyeAxis.nor();

			camera3d.translate(eyeAxis.cpy().scl(eyeSeparation));
			camera3d.update();

			int vertexCount = 0;
			ir.begin(camera3d.combined, GL20.GL_LINES);
			for (Edge3 edge : projectedQueue) {

				if (vertexCount > vertCycle) {
					ir.end();
					ir.begin(camera3d.combined, GL20.GL_LINES);
					vertexCount = 0;
				}

				ir.color(leftColor);
				ir.vertex(edge.end0.position.x, edge.end0.position.y, edge.end0.position.z);
				ir.color(leftColor);
				ir.vertex(edge.end1.position.x, edge.end1.position.y, edge.end1.position.z);

				vertexCount += 2;
			}
			ir.end();

			camera3d.translate(eyeAxis.cpy().scl(-2 * eyeSeparation));
			camera3d.update();

			vertexCount = 0;
			ir.begin(camera3d.combined, GL20.GL_LINES);
			for (Edge3 edge : projectedQueue) {

				if (vertexCount > vertCycle) {
					ir.end();
					ir.begin(camera3d.combined, GL20.GL_LINES);
					vertexCount = 0;
				}

				ir.color(rightColor);
				ir.vertex(edge.end0.position.x, edge.end0.position.y, edge.end0.position.z);
				ir.color(rightColor);
				ir.vertex(edge.end1.position.x, edge.end1.position.y, edge.end1.position.z);

				vertexCount += 2;
			}
			ir.end();

			camera3d.translate(eyeAxis.cpy().scl(eyeSeparation));
			camera3d.update();
		}
	}

	public void end() {
		project();
		draw();
	}
}
