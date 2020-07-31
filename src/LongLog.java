
/**
 * Represents a Long Log
 * 
 * @see Log
 */
public final class LongLog extends Log {

	private static final String IMAGE_SRC = "assets/longlog.png";

	private final static float SPEED = 0.07f;

	/**
	 * Constructs a LongLog
	 * 
	 * @param x     X coordinate
	 * @param y     Y coordinate
	 * @param right true if direction of travel is in the right direction, else
	 *              false
	 */
	public LongLog(float x, float y, boolean right) {
		super(IMAGE_SRC, x, y, right, SPEED);
	}
}
