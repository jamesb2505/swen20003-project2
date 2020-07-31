
/**
 * Represents a Water Tile. A Hazardous Tile
 * 
 * @see Tile
 * @see Sprite.Hazard
 */
public final class Water extends Tile implements Sprite.Hazard {

	/** Default source of Water Image */
	private static final String IMAGE_SRC = "assets/water.png";

	/**
	 * Constructs a Water Tile
	 * 
	 * @param x X coordinate
	 * @param y Y coordinate
	 */
	public Water(float x, float y) {
		super(IMAGE_SRC, x, y);
	}
}
