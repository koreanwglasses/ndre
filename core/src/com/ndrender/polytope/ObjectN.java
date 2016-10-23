package com.ndrender.polytope;

import java.util.ArrayList;
import java.util.List;

import com.ndrender.math.RotationMatrix;
import com.ndrender.math.VectorN;
import com.ndrender.render.CameraN;

public class ObjectN {
	protected transient int dim;

	public VectorN origin;
	public VectorN position;
	public RotationMatrix rotation;
	public ArrayList<ObjectN> children;

	public ObjectN(int dim) {
		this.dim = dim;
		origin = new VectorN(dim);
		position = new VectorN(dim);
		rotation = new RotationMatrix(dim);
		children = new ArrayList<ObjectN>();
	}

	public ObjectN(VectorN position) {
		dim = position.getDimension();
		this.origin = position;
		this.position = position;
		rotation = new RotationMatrix(dim);
		children = new ArrayList<ObjectN>();
	}

	public ObjectN(VectorN position, VectorN origin) {
		dim = position.getDimension();
		this.origin = origin;
		this.position = position;
		rotation = new RotationMatrix(dim);
		children = new ArrayList<ObjectN>();
	}

	public ObjectN(VectorN position, VectorN origin, ArrayList<ObjectN> children) {
		dim = position.getDimension();
		this.origin = origin;
		this.position = position;
		this.children = children;
		rotation = new RotationMatrix(dim);
	}

	public ObjectN(ObjectN object) {
		dim = object.dim;
		origin = new VectorN(object.origin);
		position = new VectorN(object.position);
		rotation = new RotationMatrix(object.rotation);
		children = new ArrayList<ObjectN>(object.children.size());
		for (ObjectN child : object.children)
			children.add(new ObjectN(child));
	}

	public int getDimension() {
		return dim;
	}

	public ObjectN translate(VectorN disp) {
		position.add(disp);

		for (ObjectN child : children)
			child.translate(disp);

		return this;
	}

	public ObjectN rotate(VectorN origin, int axis0, int axis1, double radians) {
		position.sub(origin).rotate(axis0, axis1, radians).add(origin);
		rotation.rotate(axis0, axis1, radians);

		for (ObjectN child : children)
			child.rotate(origin, axis0, axis1, radians);
		return this;
	}

	public ObjectN rotate(int axis0, int axis1, double radians) {
		rotate(origin, axis0, axis1, radians);
		return this;
	}

	public ObjectN scale(VectorN origin, int axis, double factor) {
		position.sub(origin).scl(axis, factor).add(origin);

		for (ObjectN child : children)
			child.scale(origin, axis, factor);
		return this;
	}

	public ObjectN scale(VectorN origin, double factor) {
		position.sub(origin).scl(factor).add(origin);

		for (ObjectN child : children)
			child.scale(origin, factor);
		return this;
	}

	public ObjectN scale(int axis, double factor) {
		scale(origin, axis, factor);
		return this;
	}
	
	public ObjectN scale(double factor) {
		scale(origin, factor);
		return this;
	}

	public ObjectN project(CameraN camera) {
		origin = camera.project(origin);
		position = camera.project(position);
		rotation = new RotationMatrix(dim - 1);

		dim--;

		for (ObjectN child : children) {
			child.project(camera);
		}
		return this;
	}

	public void lookAt(VectorN target) {
		VectorN relative = target.cpy().sub(position);
		for (int i = 0; i < dim - 1; i++) {
			for (int j = i + 1; j < dim; j++) {
				rotate(i, j, -Math.atan2(relative.q[j], relative.q[i]));
			}
		}
	}

	public void flatten() {
		origin.flatten();
		position.flatten();

		for (ObjectN child : children)
			child.flatten();

		dim--;
	}

	public void addChild(ObjectN child) {
		children.add(child);
	}

	public void addAllChildren(List<? extends ObjectN> children) {
		this.children.addAll(children);
	}
}
