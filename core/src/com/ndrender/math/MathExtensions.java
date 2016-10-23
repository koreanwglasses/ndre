package com.ndrender.math;

import java.util.Random;

public class MathExtensions {
	static Random rand;

	public static double invSqrt(double x) {
		double xhalf = 0.5d * x;
		long i = Double.doubleToLongBits(x);
		i = 0x5fe6ec85e7de30daL - (i >> 1);
		x = Double.longBitsToDouble(i);
		x = x * (1.5d - xhalf * x * x);
		return x;
	}

	public static VectorN proj(VectorN u, VectorN v) {
		return u.cpy().scl(u.dot(v) / u.dot(u));
	}

	public static VectorN[] GramSchmidt(VectorN forward) {
		VectorN[] vectors = new VectorN[forward.dimensions];
		vectors[0] = forward.cpy();

		for (int i = 1; i < forward.dimensions; i++) {
			VectorN vk = new VectorN(forward.dimensions);
			vk.q[i - 1] = 1;
		}

		for (int i = 1; i <= forward.dimensions; i++) {
			vectors[i - 1].nor();
			for (int j = i + 1; j <= forward.dimensions; j++)
				vectors[j - 1].sub(proj(vectors[i - 1], vectors[j - 1]));
		}

		return vectors;
	}

	static final double sqrtEps = Math.sqrt(2.2e-16);
	static final double[] ndiffCoeff = new double[] { 1 / 280., -4 / 105.,
			1 / 5., -4 / 5., 0, 4 / 5., -1 / 5., 4 / 105., -1 / 280. };

	public static double nDerivative(Function f, double x) {
		double h = sqrtEps * Math.max(Math.abs(x), 1);

		double slope = 0;
		for(int i = -4; i <= 4; i++) 
			slope += ndiffCoeff[i + 4] * f.func(x + i * h);
		
		return slope;
	}
}
