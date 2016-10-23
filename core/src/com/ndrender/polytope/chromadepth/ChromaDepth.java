package com.ndrender.polytope.chromadepth;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.ndrender.polytope.Edge3;
import com.ndrender.polytope.PolytopeRenderer;

public class ChromaDepth extends PolytopeRenderer {

	public double near = 1.2;
	public double far = 5;

	public double nearWavelength = 710;
	public double farWavelength = 410;

	public double gamma = 0.80;
	public float intensityMax = 1;

	public ChromaDepth(int dim) {
		super(dim);
	}

	@Override
	public void end() {
		project();
		
		for(Edge3 edge: projectedQueue) {
			edge.end0.color = colorFromDepth(calculateDepth(camera3d, edge.end0.position));
			edge.end1.color = colorFromDepth(calculateDepth(camera3d, edge.end1.position));
		}

		draw();
	}
	
	private double calculateDepth(Camera camera, Vector3 point) {
		// Source: http://www.math.oregonstate.edu/home/programs/undergrad/CalculusQuestStudyGuides/vcalc/dotprod/dotprod.html
		Vector3 relpos = point.cpy().sub(camera.position);
		return camera.direction.cpy().scl(relpos.dot(camera.direction) / camera.direction.dot(camera.direction)).len();		
	}

	public Color colorFromDepth(double depth) {
		return wavelengthToColor(nearWavelength - (depth - near) / (far - near) * (nearWavelength - farWavelength));
	}

	private float adjust(double color, double factor) {
		if (color == 0)
			return 0;
		else
			return (float) (intensityMax * Math.pow(color * factor, gamma));
	}

	public Color wavelengthToColor(double wavelength) {
		// Source: http://www.efg2.com/Lab/ScienceAndEngineering/Spectra.htm
		
		double r, g, b, f;

		if (wavelength < 380) {
			r = 0;
			g = 0;
			b = 0;
		} else if (wavelength < 440) {
			r = -(wavelength - 440) / (440 - 380);
			g = 0;
			b = 1;
		} else if (wavelength < 490) {
			r = 0;
			g = (wavelength - 440) / (490 - 440);
			b = 1;
		} else if (wavelength < 510) {
			r = 0;
			g = 1;
			b = -(wavelength - 510) / (510 - 490);
		} else if (wavelength < 580) {
			r = (wavelength - 510) / (580 - 510);
			g = 1;
			b = 0;
		} else if (wavelength < 645) {
			r = 1;
			g = -(wavelength - 645) / (645 - 580);
			b = 0;
		} else if (wavelength < 780) {
			r = 1;
			g = 0;
			b = 0;
		} else {
			r = 0;
			g = 0;
			b = 0;
		}

		if (wavelength < 380)
			f = 0;
		else if (wavelength < 420)
			f = 0.3 + 0.7 * (wavelength - 380) / (420 - 380);
		else if (wavelength < 700)
			f = 1;
		else if (wavelength < 780)
			f = 0.3 + 0.7 * (780 - wavelength) / (780 - 700);
		else
			f = 0;

		float R, G, B;
		R = adjust(r, f);
		G = adjust(g, f);
		B = adjust(b, f);
		return new Color(R,G,B,1);
	}
}
