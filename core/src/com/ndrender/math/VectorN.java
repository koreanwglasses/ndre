package com.ndrender.math;

import java.util.Random;

import com.badlogic.gdx.math.Vector3;

public class VectorN {
	transient int dimensions;
	public double[] q;

	public VectorN(int dimensions) {
		this.dimensions = dimensions;
		q = new double[dimensions];
	}

	public VectorN(double... q) {
		dimensions = q.length;
		this.q = q;
	}

	public VectorN(VectorN vector) {
		this.dimensions = vector.dimensions;
		q = new double[dimensions];
		for (int i = 0; i < dimensions; i++)
			q[i] = vector.q[i];
	}

	public int getDimension() {
		return dimensions;
	}

	public VectorN cpy() {
		return new VectorN(this);
	}

	public VectorN add(VectorN v) {
		if (dimensions != v.dimensions)
			throw new MismatchedDimensionsException();
		for (int i = 0; i < dimensions; i++)
			q[i] += v.q[i];
		return this;
	}

	public VectorN sub(VectorN v) {
		if (dimensions != v.dimensions)
			throw new MismatchedDimensionsException();
		for (int i = 0; i < dimensions; i++)
			q[i] -= v.q[i];
		return this;
	}

	public VectorN scl(double scale) {
		for (int i = 0; i < dimensions; i++)
			q[i] *= scale;
		return this;
	}
	
	public VectorN scl(int axis, double scale) {
		q[axis] *= scale;
		return this;
	}

	public double dot(VectorN v) {
		if (dimensions != v.dimensions)
			throw new MismatchedDimensionsException();
		double dot = 0;
		for (int i = 0; i < dimensions; i++)
			dot += q[i] * v.q[i];
		return dot;
	}

	public double len2() {
		double len2 = 0;
		for (int i = 0; i < dimensions; i++)
			len2 += q[i] * q[i];
		return len2;
	}

	public double len() {
		return Math.sqrt(len2());
	}

	public double dst2(VectorN v) {
		double dst2 = 0;
		for (int i = 0; i < dimensions; i++)
			dst2 += (q[i] - v.q[i]) * (q[i] - v.q[i]);
		return dst2;
	}

	public double dst(VectorN v) {
		return Math.sqrt(dst2(v));
	}

	public VectorN nor() {
		scl(MathExtensions.invSqrt(len2()));
		return this;
	}

	public VectorN rotate(int axis0, int axis1, double radians) {
		double coord0 = q[axis0];
		double coord1 = q[axis1];

		if(coord0 == 0 && coord1 == 0) return this;
		
		double cos = Math.cos(radians);
		double sin = Math.sin(radians);

		double rcoord0 = coord0 * cos - coord1 * sin;
		double rcoord1 = coord0 * sin + coord1 * cos;

		q[axis0] = rcoord0;
		q[axis1] = rcoord1;
		return this;
	}

	/**
	 * @param dimensions
	 * @return new VectorN
	 */
	public VectorN truncated(int dimensions) {
		VectorN truncated = new VectorN(dimensions);

		for (int i = 0; i < dimensions; i++)
			truncated.q[i] = q[i];

		return truncated;
	}

	public VectorN flatten() {

		double[] newQ = new double[dimensions - 1];

		for (int i = 0; i < dimensions - 1; i++)
			newQ[i] = q[i];

		q = newQ;
		dimensions--;

		return this;
	}

	public Vector3 toVector3() {
		return new Vector3((float) q[0], (float) q[1], (float) q[2]);
	}

	public String toString() {
		String toString = "";
		for (double i : q)
			toString += i + " ";
		return toString;
	}

	public Matrix toMatrix() {
		Matrix newMatrix = new Matrix(1, dimensions);
		for (int i = 0; i < dimensions; i++)
			newMatrix.data[i] = new double[] { q[i] };
		return newMatrix;
	}
	
	public static VectorN rand(Random rand, int dim) {
		double[] q = rand.doubles(dim).toArray();
		return new VectorN(q);
	}
	
	public void print() {
		for(int i = 0; i < dimensions; i++)
			System.out.print(q[i] + " ");

		System.out.print("\n");
	}
}
