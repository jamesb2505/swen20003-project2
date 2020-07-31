import org.newdawn.slick.Input;

/**
 * Represents a Bike. A Vehicle that flips its once it reaches certain X
 * coordinates
 * 
 * @see Vehicle
 */
public final class Bike extends Vehicle {

	private static final String IMAGE_SRC = "assets/bike.png";

	private static final float SPEED = 0.2f;

	// bounds of Bike movement
	private static final float MIN_X = 24;
	private static final float MAX_X = 1000;

	/**
	 * Constructs a Bike
	 * 
	 * @param x     X coordinate
	 * @param y     Y coordinate
	 * @param right true if direction of travel is in the right direction, else
	 *              false
	 */
	public Bike(float x, float y, boolean left) {
		super(IMAGE_SRC, x, y, left, SPEED);
	}

	/**
	 * Moves Bike in it's current direction. Flips once reaches max or min position
	 * 
	 * @param input The Slick Input object
	 * @param delta Time passed since last update (ms)
	 */
	@Override
	public void update(Input input, int delta) {
		super.update(input, delta);

		if ((getX() <= MIN_X && isFlipped()) || (getX() >= MAX_X && !isFlipped())) {
			flip();
		}
	}
}
