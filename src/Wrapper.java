import org.newdawn.slick.Input;

/**
 * Represents a Wrapper.
 * 
 * A Sprite who's movement offscreen causes it to wrap around to the other side
 */
public abstract class Wrapper extends NonPlayer {

	/**
	 * Constructs a Wrapper
	 * 
	 * @param imageSrc Path of image source
	 * @param x        X coordinate
	 * @param y        Y coordinate
	 * @param right    true if direction of travel is in the right direction, else
	 *                 false
	 * @param speed    Magnitude of speed of the Wrapper (px/ms)
	 */
	public Wrapper(String imageSrc, float x, float y, boolean right, float speed) {
		super(imageSrc, x, y, right, speed);
	}

	/**
	 * Moves and wraps the Wrapper
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
	 * Wraps the wrapper to other side of screen if offscreen.
	 * 
	 * @return dx Change in X coordinate
	 */
	public float wrap() {
		// width of the space NonPlayer can be in
		float bound = App.SCREEN_WIDTH + getWidth();

		// find dx such that x + dx is within bound
		float dx = ((getX() + getWidth() / 2) % bound + bound) % bound - getWidth() / 2 - getX();

		moveX(dx);

		return dx;
	}
}
