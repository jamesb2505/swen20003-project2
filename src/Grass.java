
/**
 * Represents a Grass Tile
 * 
 * @see Tile
 */
public final class Grass extends Tile {

	private static final String IMAGE_SRC = "assets/grass.png";

	/**
	 * Constructs a new Grass Tile
	 * 
	 * @param x X coordinate
	 * @param y Y coordinate
	 */
	public Grass(float x, float y) {
		super(IMAGE_SRC, x, y);
	}
}
