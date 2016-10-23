package com.ndrender.render;

import com.badlogic.gdx.graphics.Color;

public class Gradient implements Palette{

	int dim;

	public Color[][] gradientSpace;

	public Gradient(int dim) {
		this.dim = dim;
		gradientSpace = new Color[dim][];

		for (int i = 0; i < dim; i++) {
			gradientSpace[i] = new Color[] { Color.CLEAR };
		}
	}

	public void setAxisGradient(int axis, Color... gradient) {
		gradientSpace[axis] = gradient;
	}

	/**
	 * @param coordinates
	 *            All coordinates must be between 0.0 and 1.0 inclusive
	 * @return
	 */
	public Color getColorAt(double[] coordinates) {
		Color[] colors = new Color[dim];

		for (int i = 0; i < dim; i++) {
			colors[i] = interpolate(gradientSpace[i], coordinates[i]);
		}

		return blend(colors);
	}

	// Uses alpha-weighted average
	public static Color blend(Color[] colors) {
		Color newColor = new Color();

		float a = 0;
		float r = 0;
		float g = 0;
		float b = 0;

		float sum = 0;

		for (Color color : colors) {
			a += color.a * color.a;
			r += color.r * color.a;
			b += color.b * color.a;
			g += color.g * color.a;

			sum += color.a;
		}

		newColor.a = a / sum;
		newColor.r = r / sum;
		newColor.g = g / sum;
		newColor.b = b / sum;

		return newColor;

	}

	public static Color interpolate(Color[] colors, double alpha) {
		if (colors.length == 1)
			return colors[0];
		else if (alpha <= 0)
			return colors[0];
		else if (alpha >= 1)
			return colors[colors.length - 1];
		else {
			return interpolate(
					colors[(int) Math.floor((colors.length - 1) * alpha)],
					colors[(int) Math.ceil((colors.length - 1) * alpha)], alpha
							% (1.0 / (colors.length - 1)));
		}
	}

	public static Color interpolate(Color a, Color b, double alpha) {
		Color newColor = new Color();
		newColor.a = (float) (a.a * (1 - alpha) + b.a * alpha);
		newColor.r = (float) (a.r * (1 - alpha) + b.r * alpha);
		newColor.g = (float) (a.g * (1 - alpha) + b.g * alpha);
		newColor.b = (float) (a.b * (1 - alpha) + b.b * alpha);
		return newColor;
	}
}
