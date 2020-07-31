
/**
 * Represents a Bulldozer. A Solid Vehicle
 * 
 * @see Vehicle
 * @see Sprite.Solid
 */
public final class Bulldozer extends Vehicle implements Sprite.Solid {

	private static final String IMAGE_SRC = "assets/bulldozer.png";

	private static final float SPEED = 0.05f;

	/**
	 * Constructs a Bulldozzer
	 * 
	 * @param x     X coordinate
	 * @param y     Y coordinate
	 * @param right true if direction of travel is in the right direction, else
	 *              false
	 */
	public Bulldozer(float x, float y, boolean right) {
		super(IMAGE_SRC, x, y, right, SPEED);
	}

	/**
	 * Bulldozer-Sprite collision behaviour.
	 * 
	 * @param sprite A Sprite
	 * @param delta  Time since last update call (ms)
	 */
	@Override
	public void onCollision(Sprite sprite, int delta) {
		// push Player
		if (sprite instanceof Player) {
			Player player = (Player) sprite;

			// push the player as much as necessary
			do {
				pushSprite(player);
			} while (isContacting(player) && player.successfulMove());
		}

		// try again with regular Vehicle (Hazard) collision
		if (isContacting(sprite)) {
			super.onCollision(sprite, delta);
		}
	}
}
