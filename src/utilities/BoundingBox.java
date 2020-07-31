/**
 * BoundingBox complete class for SWEN20003: Object Oriented Software Development 2018
 * by Eleanor McMurtry, University of Melbourne
 * modified by James Barnes, University of Melbourne
 */
package utilities;

import org.newdawn.slick.Image;

public class BoundingBox {
	private float width;
	private float height;
	private float left;
	private float top;

	public BoundingBox(float x, float y, float width, float height) {
		setWidth(width);
		setHeight(height);
		setX(x);
		setY(y);
	}

	public BoundingBox(Image img, float x, float y) {
		this(x, y, img.getWidth(), img.getHeight());
	}

	public BoundingBox(BoundingBox bb) {
		width = bb.width;
		height = bb.height;
		left = bb.left;
		top = bb.top;
	}

	/* Sets the x and y position at the center of the bounding box */
	public void setX(float x) {
		left = x - width / 2;
	}

	public void setY(float y) {
		top = y - height / 2;
	}

	public void setWidth(float w) {
		width = w;
	}

	public void setHeight(float h) {
		height = h;
	}

	public float getLeft() {
		return left;
	}

	public float getTop() {
		return top;
	}

	public float getRight() {
		return left + width;
	}

	public float getBottom() {
		return top + height;
	}

	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
	}

	public boolean intersects(BoundingBox other) {
		return !(other.getLeft() >= getRight() || other.getRight() <= getLeft() || other.getTop() >= getBottom()
				|| other.getBottom() <= getTop());
	}
}
