package com.ndrender.polytope;

import com.ndrender.math.Matrix;
import com.ndrender.math.VectorN;
import com.ndrender.render.CameraN;

public class PerspectiveCameraN extends CameraN {
	public double FOV;
	protected double tanFOV;

	Matrix rotationMatrix;

	public PerspectiveCameraN(int dim) {
		super(dim);

		FOV = Math.PI / 9;
		tanFOV = 1 / Math.tan(FOV / 2);
	}

	public void setFOV(double fov) {
		this.FOV = fov;
		tanFOV = 1 / Math.tan(FOV / 2);
	}

	@Override
	public void update() {
		
		rotationMatrix = rotation.toMatrix();
	}

	@Override
	public VectorN project(VectorN worldCoords) {
		VectorN relCoords = worldCoords.cpy().sub(position);

		// Source: http://www.continuummechanics.org/cm/coordxforms.html
		VectorN rotated = Matrix.multiply(rotationMatrix, relCoords.toMatrix()).toVector();
		
		return rotated.scl(tanFOV / rotated.q[dim - 1]).truncated(dim - 1);	
	}
}
