package com.ndrender.math;

public class RotationMatrix {
	transient int dim;

	public VectorN[] axes;

	public RotationMatrix(int dim) {
		this.dim = dim;
		axes = new VectorN[dim];
		for (int i = 0; i < dim; i++) {
			axes[i] = new VectorN(dim);
			axes[i].q[i] = 1;
		}
	}
	
	public RotationMatrix(RotationMatrix rotation) {
		dim = rotation.dim;
		axes = new VectorN[dim];
		for (int i = 0; i < dim; i++) {
			axes[i] = new VectorN(rotation.axes[i]);
		}
	}
	
	public void rotate(int axis0, int axis1, double radians) {
		for(int i = 0; i < dim; i++) {
			axes[i].rotate(axis0, axis1, radians);
		}
	}
	
	public Matrix toMatrix() {
		Matrix matrix = new Matrix(dim, dim);
		for (int i = 0; i < dim; i++)
			for (int j = 0; j < dim; j++)
				matrix.data[i][j] = axes[i].q[j];
		return matrix;
	}
}
