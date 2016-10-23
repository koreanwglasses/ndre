package com.ndrender.polytope;

import java.util.ArrayList;
import java.util.List;

import com.ndrender.math.VectorN;

public class Polytope extends ObjectN {

	public ObjectN vertices;
	public ArrayList<Edge> edges;

	public Polytope(int dim) {
		super(dim);

		vertices = new ObjectN(dim);
		addChild(vertices);

		edges = new ArrayList<Edge>();
	}

	public Polytope(Polytope polytope) {
		super(polytope);

		vertices = new ObjectN(dim);

		ArrayList<Vertex> rawVertices = new ArrayList<Vertex>(
				polytope.vertices.children.size());
		for (int i = 0; i < polytope.vertices.children.size(); i++) {
			rawVertices.add(new Vertex((Vertex) polytope.vertices.children
					.get(i)));
		}

		vertices.addAllChildren(rawVertices);

		edges = new ArrayList<Edge>(polytope.edges.size());
		for (Edge edge : polytope.edges) {
			edges.add(new Edge(rawVertices.get(polytope.vertices.children
					.indexOf(edge.end0)), rawVertices
					.get(polytope.vertices.children.indexOf(edge.end1))));
		}
		addChild(vertices);
	}

	public void addAllVertices(List<Vertex> vertices) {
		this.vertices.addAllChildren(vertices);
	}

	public void addAllEdges(List<Edge> edges) {
		this.edges.addAll(edges);
	}

	public void merge(Polytope polytope) {
		vertices.addAllChildren(polytope.vertices.children);
		edges.addAll(polytope.edges);
	}

	public static Polytope tesseract() {
		Polytope tess = new Polytope(4);

		ArrayList<Vertex> tessVerts = new ArrayList<Vertex>(16);

		tessVerts.add(new Vertex(0, 0, 0, 0));
		tessVerts.add(new Vertex(0, 0, 0, 1));
		tessVerts.add(new Vertex(0, 0, 1, 0));
		tessVerts.add(new Vertex(0, 0, 1, 1));
		tessVerts.add(new Vertex(0, 1, 0, 0));
		tessVerts.add(new Vertex(0, 1, 0, 1));
		tessVerts.add(new Vertex(0, 1, 1, 0));
		tessVerts.add(new Vertex(0, 1, 1, 1));
		tessVerts.add(new Vertex(1, 0, 0, 0));
		tessVerts.add(new Vertex(1, 0, 0, 1));
		tessVerts.add(new Vertex(1, 0, 1, 0));
		tessVerts.add(new Vertex(1, 0, 1, 1));
		tessVerts.add(new Vertex(1, 1, 0, 0));
		tessVerts.add(new Vertex(1, 1, 0, 1));
		tessVerts.add(new Vertex(1, 1, 1, 0));
		tessVerts.add(new Vertex(1, 1, 1, 1));

		tess.addAllVertices(tessVerts);

		tess.edges.add(new Edge(tessVerts.get(0), tessVerts.get(1)));
		tess.edges.add(new Edge(tessVerts.get(0), tessVerts.get(2)));
		tess.edges.add(new Edge(tessVerts.get(0), tessVerts.get(4)));
		tess.edges.add(new Edge(tessVerts.get(0), tessVerts.get(8)));
		tess.edges.add(new Edge(tessVerts.get(1), tessVerts.get(3)));
		tess.edges.add(new Edge(tessVerts.get(1), tessVerts.get(5)));
		tess.edges.add(new Edge(tessVerts.get(1), tessVerts.get(9)));
		tess.edges.add(new Edge(tessVerts.get(2), tessVerts.get(3)));
		tess.edges.add(new Edge(tessVerts.get(2), tessVerts.get(6)));
		tess.edges.add(new Edge(tessVerts.get(2), tessVerts.get(10)));
		tess.edges.add(new Edge(tessVerts.get(3), tessVerts.get(7)));
		tess.edges.add(new Edge(tessVerts.get(3), tessVerts.get(11)));
		tess.edges.add(new Edge(tessVerts.get(4), tessVerts.get(5)));
		tess.edges.add(new Edge(tessVerts.get(4), tessVerts.get(6)));
		tess.edges.add(new Edge(tessVerts.get(4), tessVerts.get(12)));
		tess.edges.add(new Edge(tessVerts.get(5), tessVerts.get(7)));
		tess.edges.add(new Edge(tessVerts.get(5), tessVerts.get(13)));
		tess.edges.add(new Edge(tessVerts.get(6), tessVerts.get(7)));
		tess.edges.add(new Edge(tessVerts.get(6), tessVerts.get(14)));
		tess.edges.add(new Edge(tessVerts.get(7), tessVerts.get(15)));
		tess.edges.add(new Edge(tessVerts.get(8), tessVerts.get(9)));
		tess.edges.add(new Edge(tessVerts.get(8), tessVerts.get(10)));
		tess.edges.add(new Edge(tessVerts.get(8), tessVerts.get(12)));
		tess.edges.add(new Edge(tessVerts.get(9), tessVerts.get(11)));
		tess.edges.add(new Edge(tessVerts.get(9), tessVerts.get(13)));
		tess.edges.add(new Edge(tessVerts.get(10), tessVerts.get(11)));
		tess.edges.add(new Edge(tessVerts.get(10), tessVerts.get(14)));
		tess.edges.add(new Edge(tessVerts.get(11), tessVerts.get(15)));
		tess.edges.add(new Edge(tessVerts.get(12), tessVerts.get(13)));
		tess.edges.add(new Edge(tessVerts.get(12), tessVerts.get(14)));
		tess.edges.add(new Edge(tessVerts.get(13), tessVerts.get(15)));
		tess.edges.add(new Edge(tessVerts.get(14), tessVerts.get(15)));

		return tess;
	}

	public static Polytope nCube(int dim) {
		Polytope ncube = new Polytope(dim);

		int vertexCount = 1 << dim;
		ArrayList<Vertex> cubeVerts = new ArrayList<Vertex>(vertexCount);

		for (int i = 0; i < vertexCount; i++) {
			VectorN newVertex = new VectorN(dim);
			for (int j = 0; j < dim; j++)
				newVertex.q[j] = Math.floorDiv(i, 1 << j) % 2;
			cubeVerts.add(new Vertex(newVertex));
		}

		ncube.addAllVertices(cubeVerts);

		int[] flips = new int[dim];
		for (int i = 0; i < dim; i++)
			flips[i] = 1 << i;

		for (int i = 0; i < vertexCount; i++) {
			for (int j = 0; j < dim; j++) {
				if ((i ^ flips[j]) > i)
					ncube.edges.add(new Edge(cubeVerts.get(i), cubeVerts.get(i
							^ flips[j])));
			}
		}

		return ncube;
	}

	public static Polytope uvwTorus4(double r1, double r2, int u, int v, int w) {
		Polytope returnVal = new Polytope(4);
		for (int i = 0; i < w; i++) {
			Polytope sphere = uvSphere4(r2, u, v);
			sphere.translate(new VectorN(r1 * Math.cos(Math.PI * i * 2D / w),
					0, 0, r1 * Math.sin(Math.PI * i * 2D / w)));
			sphere.rotate(0, 3, Math.PI * i * 2D / w);
			returnVal.merge(sphere);
		}
		for (int i = 0; i < w - 1; i++) {
			for (int j = 0; j < u * (v - 2) + 2; j++) {
				returnVal.edges.add(new Edge(
						(Vertex) returnVal.vertices.children.get(i
								* (u * (v - 2) + 2) + j),
						(Vertex) returnVal.vertices.children.get((i + 1)
								* (u * (v - 2) + 2) + j)));
			}
		}
		for (int j = 0; j < u * (v - 2) + 2; j++) {
			returnVal.edges.add(new Edge((Vertex) returnVal.vertices.children
					.get((w - 1) * (u * (v - 2) + 2) + j),
					(Vertex) returnVal.vertices.children.get(j)));
		}
		return returnVal;
	}

	public static Polytope uvSphere4(double r, int u, int v) {
		Polytope returnVal = new Polytope(4);
		ArrayList<Vertex> vertices = new ArrayList<Vertex>(u * (v - 2) + 2);
		vertices.add(new Vertex(new VectorN(0, -r, 0, 0)));
		for (int j = 1; j < v - 1; j++) {
			double y = r * Math.sin(Math.PI * (j / (v - 1D) - .5D));
			for (int i = 0; i < u; i++) {
				double subRadius = Math.sqrt(r * r - y * y);
				vertices.add(new Vertex(new VectorN(subRadius
						* Math.cos(Math.PI * 2 * i / (double) u), y, subRadius
						* Math.sin(Math.PI * 2 * i / (double) u), 0)));
			}
		}
		vertices.add(new Vertex(new VectorN(0, r, 0, 0)));

		ObjectN vertObj = new ObjectN(4);
		vertObj.addAllChildren(vertices);
		returnVal.vertices = vertObj;
		returnVal.addChild(vertObj);

		for (int i = 1; i <= u; i++)
			returnVal.edges.add(new Edge(vertices.get(0), vertices.get(i)));
		for (int l = 0; l < v - 3; l++)
			for (int i = 1; i <= u; i++) {
				returnVal.edges.add(new Edge(vertices.get(l * u + i), vertices
						.get((l + 1) * u + i)));
				returnVal.edges.add(new Edge(vertices.get(l * u + i), vertices
						.get(l * u + (i < u ? (i + 1) : 1))));
			}
		for (int i = 1; i <= u; i++)
			returnVal.edges.add(new Edge(vertices.get((v - 3) * u + i),
					vertices.get((v - 3) * u + (i < u ? (i + 1) : 1))));
		for (int i = (v - 3) * u + 1; i < u * (v - 2) + 1; i++) {
			returnVal.edges.add(new Edge(vertices.get(i), vertices.get(u
					* (v - 2) + 1)));
		}

		return returnVal;
	}
}
