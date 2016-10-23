package com.ndrender.math;

public class Matrix {
	transient final int width;
	transient final int height;
	public double[][] data;

	public Matrix(int width, int height) {
		this.width = width;
		this.height = height;
		data = new double[height][width];
	}

	public Matrix(double[][] data) {
		width = data[0].length;
		height = data.length;
		this.data = data;
	}

	public Matrix(Matrix matrix) {
		width = matrix.width;
		height = matrix.height;
		data = new double[height][width];
		for (int i = 0; i < width; i++)
			for (int j = 0; j < width; j++)
				data[i][j] = matrix.data[i][j];
	}

	public Matrix cpy() {
		return new Matrix(this);
	}

	public Matrix transpose() {
		double[][] tempData = new double[width][height];
		for (int i = 0; i < width; i++)
			for (int j = 0; j < width; j++)
				tempData[j][i] = data[i][j];
		data = tempData;
		return this;
	}

	public Matrix set(int row, int col, double value) {
		data[row][col] = value;
		return this;
	}

	public static Matrix multiply(Matrix A, Matrix B) {
		if (A.width != B.height)
			throw new MismatchedDimensionsException();
		Matrix product = new Matrix(B.width, A.height);
		for (int i = 0; i < A.height; i++)
			for (int j = 0; j < B.width; j++) {
				double sum = 0;
				for (int k = 0; k < A.width; k++)
					sum += A.data[i][k] * B.data[k][j];
				product.data[i][j] = sum;
			}
		return product;
	}

	public VectorN toVector() {
		if (width == 1) {
			VectorN newVector = new VectorN(height);
			for (int i = 0; i < height; i++)
				newVector.q[i] = data[i][0];
			return newVector;
		}

		if (height == 1) {
			VectorN newVector = new VectorN(width);
			for (int i = 0; i < height; i++)
				newVector.q[i] = data[0][i];
			return newVector;
		}

		else
			throw new MismatchedDimensionsException();
	}
	
	public void print() {
		for(int j = 0; j < height; j++) {
			for(int i = 0; i < width; i++)
				System.out.print(data[j][i] + " ");
			System.out.print("\n");
		}
	}
}
