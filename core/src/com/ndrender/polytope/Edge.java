package com.ndrender.polytope;

public class Edge {

	Vertex end0;
	Vertex end1;

	public Edge(Vertex end0, Vertex end1) {
		this.end0 = end0;
		this.end1 = end1;
	}

	public Edge(Edge edge) {
		end0 = new Vertex(edge.end0);
		end1 = new Vertex(edge.end1);
	}

	public Edge3 toEdge3() {
		return new Edge3(end0.toVertex3(), end1.toVertex3());
	}
}
