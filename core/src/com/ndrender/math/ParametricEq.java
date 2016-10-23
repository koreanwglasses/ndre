package com.ndrender.math;

import com.ndrender.polytope.Polytope;
import com.ndrender.render.Gradient;
import com.ndrender.render.Palette;

public class ParametricEq {
	transient final int worldDim;
	public Function[] parameters;

	public ParametricEq(int worldDim) {
		this.worldDim = worldDim;
		parameters = new Function[worldDim];
	}

	public ParametricEq(Function... parameters) {
		worldDim = parameters.length;
		this.parameters = parameters;
	}

	public Polytope bake(int manifoldDim, double step, double[] lowerBound,
			double[] upperBound) {
		double[] steps = new double[manifoldDim];
		for (int i = 0; i < manifoldDim; i++)
			steps[i] = step;
		return bake(manifoldDim, steps, lowerBound, upperBound);
	}

	public Polytope bake(int manifoldDim, double[] step, double[] lowerBound,
			double[] upperBound) {
		return bake(manifoldDim, step, lowerBound, upperBound, null);
	}

	public Polytope bake(int manifoldDim, double[] step, double[] lowerBound,
			double[] upperBound, Palette gradient) {
		return Manifold
				.fromEquation(worldDim, manifoldDim, parameters,
						new double[manifoldDim], step, lowerBound, upperBound,
						gradient).toPolytope();
	}
}
