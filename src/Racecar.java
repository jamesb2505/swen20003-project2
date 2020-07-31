
/**
 * Represents a Racecar. A Vehicle
 * 
 * @see Vehicle
 */
public final class Racecar extends Vehicle {

	private static final String IMAGE_SRC = "assets/racecar.png";

	private final static float SPEED = 0.5f;

	/**
	 * Constructs a Racecar
	 * 
	 * @param x     X coordinate
	 * @param y     Y coordinate
	 * @param right true if direction of travel is in the right direction, else
	 *              false
	 */
	public Racecar(float x, float y, boolean right) {
		super(IMAGE_SRC, x, y, right, SPEED);
	}
}
