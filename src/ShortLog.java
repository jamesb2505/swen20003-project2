
/**
 * Represents a Short Log
 * 
 * @see Log
 */
public final class ShortLog extends Log {

	/** Source of ShortLog Image */
	private static final String IMAGE_SRC = "assets/log.png";
	/** Default Speed of ShortLog */
	private static final float SPEED = 0.1f;

	/**
	 * Constructs a ShortLog
	 * 
	 * @param x     X coordinate
	 * @param y     Y coordinate
	 * @param right true if direction of travel is in the right direction, else
	 *              false
	 */
	public ShortLog(float x, float y, boolean right) {
		super(IMAGE_SRC, x, y, right, SPEED);
	}
}
