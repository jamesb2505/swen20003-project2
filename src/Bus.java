
/**
 * Represents a Bus. A Vehicle
 * 
 * @see Vehicle
 */
public final class Bus extends Vehicle {

	private static final String IMAGE_SRC = "assets/bus.png";
	
	private static final float SPEED = 0.15f;

	/**
	 * Constructs a Bus
	 * 
	 * @param x     X coordinate
	 * @param y     Y coordinate
	 * @param right true if direction of travel is in the right direction, else
	 *              false
	 */
	public Bus(float x, float y, boolean left) {
		super(IMAGE_SRC, x, y, left, SPEED);
	}
}
