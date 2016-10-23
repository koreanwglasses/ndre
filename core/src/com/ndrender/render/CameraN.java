package com.ndrender.render;

import com.ndrender.math.VectorN;
import com.ndrender.polytope.ObjectN;


public abstract class CameraN extends ObjectN{

	public CameraN(int dim) {
		super(dim);
	}
	
	public abstract void update();
	public abstract VectorN project(VectorN worldCoords);
}
