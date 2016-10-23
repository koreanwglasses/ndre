package com.ndrender.polytope;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;

public class Vertex3 {

	public Vector3 position;
	public Color color;

	public Vertex3(Vertex3 vertex) {
		position = vertex.position;
		color = vertex.color;
	}

	public Vertex3(Vector3 position) {
		this.position = position;
		color = Color.WHITE;
	}

	public Vertex3(Vector3 position, Color color) {
		this.position = position;
		this.color = color;
	}
}
