package com.ndrender.math;

public class RotationTensor {
	transient int dim;

	public VectorN[] axes;

	public RotationTensor(int dim) {
		this.dim = dim;
		axes = new VectorN[dim];
		for (int i = 0; i < dim; i++) {
			axes[i] = new VectorN(dim);
			axes[i].q[i] = 1;
		}
	}
	
	public RotationTensor(RotationTensor rotation) {
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
}
