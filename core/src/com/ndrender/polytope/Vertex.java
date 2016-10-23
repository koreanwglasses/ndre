package com.ndrender.polytope;

import com.badlogic.gdx.graphics.Color;
import com.ndrender.math.VectorN;

public class Vertex extends ObjectN{
	
	public Color color;
	
	public Vertex(Vertex vertex) {
		super(vertex);
		color = vertex.color;
	}
	public Vertex(double... q) {
		super(new VectorN(q));
		color = Color.WHITE;
	}
	
	public Vertex(VectorN position) {
		super(position);
		color = Color.WHITE;
	}
	
	public Vertex(VectorN position, Color color) {
		super(position);
		this.color = color;
	}
	
	public Vertex3 toVertex3() {
		return new Vertex3(position.toVector3(), color);
	}
}
