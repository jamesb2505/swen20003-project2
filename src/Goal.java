
/**
 * Represents a Goal Tile. A Tile that must be reached to pass onto the next
 * level, and acts as a hazard once taken 
 * 
 * @see Tile
 * @see Sprite.Hazard
 */
public final class Goal extends Tile implements Sprite.Hazard {

	private static final String IMAGE_SRC = "assets/frog.png";

	private boolean taken;

	/**
	 * Constructs a Bus
	 * 
	 * @param x     X coordinate
	 * @param y     Y coordinate
	 * @param right Indicates the direction of movement. true if movement is in
	 *              right direction
	 */
	public Goal(float x, float y) {
		super(IMAGE_SRC, x, y);

		// width of boundingBox does not match the image width with default asset.
		// fix that
		setBoundingBoxWidth(2 * getWidth());
	}

	/** Draws if Goal is taken */
	@Override
	public void render() {
		if (taken) {
			super.render();
		}
	}

	/**
	 * Goal-Sprite contact behvavior.
	 * 
	 * @param sprite A Player Object
	 * @param delta  Time since last update call (ms)
	 */
	@Override
	public void onCollision(Sprite sprite, int delta) {
		if (!taken && sprite instanceof Player) {
			taken = true;
			((Player) sprite).resetPosition();
		} else {
			// regular Hazard collision
			super.onCollision(sprite, delta);
		}
	}

	/**
	 * Gets the value of the taken flag
	 * 
	 * @return true if Goal is taken
	 */
	public boolean isTaken() {
		return taken;
	}
}
