import org.newdawn.slick.Input;

/** Represents a NonPlayer */
public abstract class NonPlayer extends Sprite {

	/** Total time since NonPlayer's creation (net sum of delta) */
	private int age;
	/** Directional speed of NonPlayer */
	private float speed;
	/** Flag indicating if direction has been flipped from right to left */
	private boolean flipped;

	/**
	 * Constructs a NonPlayer
	 * 
	 * @param imageSrc Path of source image
	 * @param x        X coordinate
	 * @param y        Y coordinate
	 * @param right    true if direction of travel is in the right direction, else
	 *                 false
	 * @param speed    Magnitude of speed of the NonPlayer (px/ms)
	 */
	public NonPlayer(String imageSrc, float x, float y, boolean right, float speed) {
		super(imageSrc, x, y);

		age = 0;
		flipped = false;
		setSpeed(right, speed);
	}

	/**
	 * Increments the age by delta of the NonPlayer
	 * 
	 * @param input The Slick Input object
	 * @param delta Time passed since last update (ms)
	 */
	@Override
	public void update(Input input, int delta) {
		incrementAge(delta);
	}

	/** Reverses direction and flips Image of NonPlayer */
	public void flip() {
		flipped = !flipped;
		speed *= -1;
		flipImage();
	}

	/**
	 * Sets the speed of NonPlayer
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
	 * Gets the speed of the NonPlayer
	 * 
	 * @return Speed of NonPlayer (px/ms)
	 */
	public float getSpeed() {
		return speed;
	}

	/**
	 * Gets the age of the NonPlayer
	 * 
	 * @return Age of NonPlayer (ms)
	 */
	public int getAge() {
		return age;
	}

	/**
	 * Increments the age of the NonPlayer
	 * 
	 * @param delta Time to increment age by (ms)
	 * @return new age of NonPlayer
	 */
	public int incrementAge(int delta) {
		return age += delta;
	}
}
