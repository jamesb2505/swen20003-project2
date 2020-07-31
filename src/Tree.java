
/**
 * Represents a Tree Tile. A Solid Tile
 * 
 * @see Tile
 * @see Sprite.Solid
 */
public final class Tree extends Tile implements Sprite.Solid {

	public static final String IMAGE_SRC = "assets/tree.png";

	/**
	 * Constructs a Tree
	 * 
	 * @param x X coordinate
	 * @param y Y coordinate
	 */
	public Tree(float x, float y) {
		super(IMAGE_SRC, x, y);
	}
}
