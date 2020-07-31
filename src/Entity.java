import org.newdawn.slick.Input;

/**
 * Represents an Entity. A Sprite with it's own automatic movement behavior
 * 
 * @see Sprite
 */
public abstract class Entity extends Sprite {

	private int age;
	private float speed;
	private boolean flipped;

	/**
	 * Constructs an Entity
	 * 
	 * @param imageSrc Path of source image
	 * @param x        X coordinate
	 * @param y        Y coordinate
	 * @param right    true if direction of travel is in the right direction, else
	 *                 false
	 * @param speed    Magnitude of speed of the Entity (px/ms)
	 */
	public Entity(String imageSrc, float x, float y, boolean right, float speed) {
		super(imageSrc, x, y);

		age = 0;
		flipped = false;
		setSpeed(right, speed);
	}

	/**
	 * Increments the age by delta of the Entity
	 * 
	 * @param input The Slick Input object
	 * @param delta Time passed since last update (ms)
	 */
	@Override
	public void update(Input input, int delta) {
		incrementAge(delta);
	}

	/** Reverses direction and flips Image of Entity */
	public void flip() {
		flipped = !flipped;
		speed *= -1;
		flipImage();
	}

	/**
	 * Sets the speed of Entity
	 * 
	 * @param right Indicates the direction of movement. true if movement is in
	 *              right direction
	 * @param speed Magnitude of movement
	 */
	public void setSpeed(boolean right, float speed) {
		// make sure we are facing right
		if (flipped) {
			flip();
		}

		this.speed = speed;

		// flip if we have to
		if (!right) {
			flip();
		}
	}

	/**
	 * Gets the speed of the Entity
	 * 
	 * @return Speed of Entity (px/ms)
	 */
	public float getSpeed() {
		return speed;
	}

	/**
	 * Gets the age of the Entity
	 * 
	 * @return Age of Entity (ms)
	 */
	public int getAge() {
		return age;
	}

	/**
	 * Increments the age of the Entity
	 * 
	 * @param delta Time to increment age by (ms)
	 * 
	 * @return new age of Entity
	 */
	public int incrementAge(int delta) {
		return age += delta;
	}

	/**
	 * Pushes Sprite in direction of speed, delta units
	 * 
	 * @param sprite The sprite to push
	 * @param delta  Number of units (multiples of speed) to move Sprite by
	 */
	public void pushSprite(Sprite sprite, int delta) {
		sprite.moveX(speed * delta);
	}

	/**
	 * Pushes Sprite in direction of speed, 1 unit
	 * 
	 * @param sprite The sprite to push
	 */
	public void pushSprite(Sprite sprite) {
		pushSprite(sprite, 1);
	}

	/**
	 * Returns the state of the flag representing whether the Entity has been
	 * flipped
	 * 
	 * @return true if it has been flipped, else false
	 */
	public boolean isFlipped() {
		return flipped;
	}
}
