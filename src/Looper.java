import org.newdawn.slick.Input;

/**
 * Represents a Looper. An Entity who's movement offscreen causes it to wrap
 * around to the other side
 * 
 * @see Entity
 */
public abstract class Looper extends Entity {

	/**
	 * Constructs a Looper
	 * 
	 * @param imageSrc Path of image source
	 * @param x        X coordinate
	 * @param y        Y coordinate
	 * @param right    true if direction of travel is in the right direction, else
	 *                 false
	 * @param speed    Magnitude of speed of the Looper (px/ms)
	 */
	public Looper(String imageSrc, float x, float y, boolean right, float speed) {
		super(imageSrc, x, y, right, speed);
	}

	/**
	 * Moves and wraps the Looper
	 * 
	 * @param input The Slick Input object
	 * @param delta Time passed since last update (ms)
	 */
	@Override
	public void update(Input input, int delta) {
		super.update(input, delta);

		wrap();
		moveX(delta * getSpeed());
	}

	/**
	 * Wraps the Looper to other side of screen if offscreen.
	 * 
	 * @return dx Change in X coordinate
	 */
	public float wrap() {
		// width of the space Looper can be in
		float bound = App.SCREEN_WIDTH + getWidth();

		// sgn(right % bound) == sgn(right)
		// fix, such that 0 <= right % bound < bound
		float dx = (getRight() % bound + bound) % bound - getRight();

		moveX(dx);

		return dx;
	}
}
