package com.ndrender.math;

import java.util.ArrayList;

import com.ndrender.polytope.Edge;
import com.ndrender.polytope.Polytope;
import com.ndrender.polytope.Vertex;
import com.ndrender.render.Gradient;
import com.ndrender.render.Palette;

public class Manifold {
	transient int worldDim;
	public ArrayList<Vertex> vertices;
	public ArrayList<Edge> edges;

	public Manifold(int worldDim) {
		this.worldDim = worldDim;
		vertices = new ArrayList<Vertex>();
		edges = new ArrayList<Edge>();
	}

	/**
	 * 
	 * # of dimensions of range of f - # of parameters should equal manifoldDim
	 * 
	 * @param worldDim
	 * @param manifoldDim
	 * @param f
	 * @param parameters
	 * @param step
	 * @param lowerBound
	 * @param upperBound
	 * @param gradient
	 *            Each parameter has its own gradient and each gradient is an
	 *            array of colors
	 * @return
	 */
	public static Manifold fromEquation(int worldDim, int manifoldDim,
			Function[] f, double[] parameters, double[] step,
			double[] lowerBound, double[] upperBound, Palette gradient) {
		Manifold manifold = new Manifold(worldDim);
		if (manifoldDim < 1) {
			VectorN vertexVector = new VectorN(worldDim);
			for (int i = 0; i < worldDim; i++) {
				vertexVector.q[i] = f[i].func(parameters);
			}

			if (gradient != null) {
				double[] gradientCoords = new double[parameters.length];
				for (int i = 0; i < parameters.length; i++) {
					gradientCoords[i] = (parameters[i] - lowerBound[i])
							/ (upperBound[i] - lowerBound[i]);
				}
				
				manifold.vertices.add(new Vertex(vertexVector, gradient
						.getColorAt(gradientCoords)));
			} else {
				manifold.vertices.add(new Vertex(vertexVector));
			}
		} else {
			for (double t = lowerBound[manifoldDim - 1]; t < upperBound[manifoldDim - 1]
					+ step[manifoldDim - 1] / 2; t += step[manifoldDim - 1]) {
				parameters[manifoldDim - 1] = t;
				Manifold subManifold = fromEquation(worldDim, manifoldDim - 1,
						f, parameters, step, lowerBound, upperBound, gradient);
				manifold = connect(manifold, subManifold);
				// System.out.println(manifold.edges.size());
			}
		}
		return manifold;
	}

	// Connects two manifolds by their corresponding vertices
	public static Manifold connect(Manifold A, Manifold B) {
		int sizeA = A.vertices.size();
		int sizeB = B.vertices.size();

		if (sizeA == 0)
			return B;
		if (sizeB == 0)
			return A;

		Manifold connected = new Manifold(A.worldDim);
		connected.vertices.addAll(A.vertices);
		connected.vertices.addAll(B.vertices);
		connected.edges.addAll(A.edges);
		connected.edges.addAll(B.edges);

		if (sizeA > sizeB)
			for (int i = 0; i < sizeB; i++)
				connected.edges.add(new Edge(A.vertices.get(i + sizeA - sizeB),
						B.vertices.get(i)));
		else
			for (int i = 0; i < sizeA; i++)
				connected.edges.add(new Edge(A.vertices.get(i), B.vertices
						.get(i + sizeB - sizeA)));

		return connected;
	}

	public Polytope toPolytope() {
		Polytope manifold = new Polytope(worldDim);
		manifold.addAllEdges(edges);

		manifold.addAllVertices(vertices);
		return manifold;
	}
}
