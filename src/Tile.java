
/**
 * Represents a Tile
 * 
 * @see Sprite
 */
public abstract class Tile extends Sprite {

	/**
	 * Constructs a Tile
	 * 
	 * @param imageSrc Path of image source
	 * @param x        X coordinate
	 * @param y        Y coordinate
	 */
	public Tile(String imageSrc, float x, float y) {
		super(imageSrc, x, y);
	}
}
