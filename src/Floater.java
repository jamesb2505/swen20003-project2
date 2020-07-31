
/**
 * Represents a Floater. A Looper Sprite that allows the Player to ride safely
 * across Water
 * 
 * @see Wrpper
 */
public abstract class Floater extends Looper {

	/**
	 * Constructs a Floater
	 * 
	 * @param x     X coordinate
	 * @param y     Y coordinate
	 * @param right true if direction of travel is in the right direction, else
	 *              false
	 * @param speed Magnitude of speed of the Floater (px/ms)
	 */
	public Floater(String imageSrc, float x, float y, boolean right, float speed) {
		super(imageSrc, x, y, right, speed);
	}

	/**
	 * Floater-Sprite collision behaviour.
	 * 
	 * @param Sprite A Sprite
	 * @param delta  Time since last update call (ms)
	 */
	@Override
	public void onCollision(Sprite sprite, int delta) {
		if (sprite instanceof Player) {
			Player player = (Player) sprite;
			player.setSafe(true);
			pushSprite(player, delta);
		}

		super.onCollision(sprite, delta);
	}
}
