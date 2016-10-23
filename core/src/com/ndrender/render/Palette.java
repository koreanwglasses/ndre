package com.ndrender.render;

import com.badlogic.gdx.graphics.Color;

public interface Palette {
	/**
	 * @param coordinates
	 *            All coordinates must be between 0.0 and 1.0 inclusive
	 * @return
	 */
	public Color getColorAt(double[] coordinates);
}
