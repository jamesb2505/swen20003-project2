
/**
 * Represents a Vehicle. A Hazardous Looper
 * 
 * @see Looper
 * @see Sprite.Hazard
 */
public abstract class Vehicle extends Looper implements Sprite.Hazard {

	/**
	 * Constructs a Vehicle
	 * 
	 * @param imageSrc Path of image source
	 * @param x        X coordinate
	 * @param y        Y coordinate
	 * @param right    true if direction of travel is in the right direction, else
	 *                 false
	 * @param speed    Magnitude of speed of the vehicle (px/ms)
	 */
	public Vehicle(String imageSrc, float x, float y, boolean right, float speed) {
		super(imageSrc, x, y, right, speed);
	}
}
