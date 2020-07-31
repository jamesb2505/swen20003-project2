import java.util.ArrayList;

import org.newdawn.slick.Input;

/**
 * Represents a Log. A Floater that can contain ExtraLives
 * 
 * @see Floater
 */
public abstract class Log extends Floater {

	private ArrayList<ExtraLife> extraLives;

	/**
	 * Constructs a Log
	 * 
	 * @param x     X coordinate of Log
	 * @param y     Y coordinate of Log
	 * @param right true if direction of travel is in the right direction, else
	 *              false
	 */
	public Log(String imageSrc, float x, float y, boolean right, float speed) {
		super(imageSrc, x, y, right, speed);

		extraLives = new ArrayList<ExtraLife>();
	}

	/* Draws Log and any ExtraLives on Log */
	@Override
	public void render() {
		super.render();

		for (ExtraLife extraLife : extraLives) {
			extraLife.render();
		}
	}

	/**
	 * Moves the Log and any ExtraLives on Log
	 * 
	 * @param input The Slick input object
	 * @param delta Time passed since last update (ms)
	 */
	@Override
	public void update(Input input, int delta) {
		super.update(input, delta);

		for (ExtraLife extraLife : extraLives) {
			pushSprite(extraLife, delta);
			extraLife.update(input, delta);
		}

		removeConsumedExtraLives();
	}

	/**
	 * Wraps Log and any ExtraLives on Log to other side of screen if offscreen.
	 * 
	 * @return dx Change in X coordinate
	 */
	@Override
	public float wrap() {
		// wrap Log, store the dx
		float dx = super.wrap();

		// move all ExtraLives by same dx
		for (ExtraLife extraLife : extraLives) {
			extraLife.moveX(dx);
		}
		
		return dx;
	}

	/**
	 * Checks for Log-Sprite contact and performs collision behavior as defined by
	 * onCollision. Performs the same prodecure on all ExtraLives on Log
	 * 
	 * @param sprite Sprite to check for contact with
	 * @param delta  Time since last update call (ms)
	 */
	public void checkCollision(Sprite sprite, int delta) {
		super.checkCollision(sprite, delta);

		for (ExtraLife extraLife : extraLives) {
			extraLife.checkCollision(sprite, delta);
		}
	}

	/** Adds a new ExtraLife to Log at current position */
	public void addExtraLife() {
		extraLives.add(new ExtraLife(this));
	}

	/** Removes ExtraLives from Log if they have been consumed */
	public void removeConsumedExtraLives() {
		extraLives.removeIf(ExtraLife::isConsumed);
	}

	/**
	 * Checks if Log has an ExtraLife attached
	 * 
	 * @return true if Log has an ExtraLife attached, else false
	 */
	public boolean hasExtraLife() {
		return !(extraLives.isEmpty());
	}
}
