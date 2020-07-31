import org.newdawn.slick.Input;

/**
 * Represents an Extra Life. An Entity that, on contact with the Player add's
 * Life
 * 
 * @see Entity
 */
public final class ExtraLife extends Entity {

	private static final String IMAGE_SRC = "assets/extralife.png";

	private static final boolean START_RIGHT = true;

	private static final int LIFE_SPAN = 14000;
	private static final int MOVE_TIME = 2000;

	private Log log;
	private boolean consumed;

	/**
	 * Constructs an ExtraLife
	 * 
	 * @param log Log ExtraLife is attached to
	 */
	public ExtraLife(Log log) {
		super(IMAGE_SRC, log.getX(), log.getY(), START_RIGHT, World.TILE_WIDTH);

		this.log = log;
		consumed = false;
	}

	/**
	 * Moves the ExtraLife if MOVE_TIME has passed. Consumes if LIFE_SPAN has passed
	 */
	@Override
	public void update(Input input, int delta) {
		super.update(input, delta);

		if (!consumed) {
			int age = getAge();

			// check if we should kill (consume) the life
			if (age >= LIFE_SPAN) {
				consume();
			}
			// check if we would move
			else if (age % MOVE_TIME < delta) {
				move();
			}
		}
	}

	/** Moves ExtraLife within the bounds of it's Log */
	public void move() {
		// check if any movement is valid
		if (log.getWidth() > World.TILE_WIDTH) {
			// try to move in current direction
			moveX(getSpeed());

			// fix if we moved off our Log
			if (!isContacting(log)) {
				flip();
				// account for previous movement
				moveX(2 * getSpeed());
			}

		}
	}

	/**
	 * ExtraLife-Sprite contact behvavior. If ExtraLife is not already consumed,
	 * adds life to player on contact and consumes itself
	 * 
	 * @param sprite A Sprite
	 * @param delta  Time since last update call (ms)
	 */
	@Override
	public void onCollision(Sprite sprite, int delta) {
		if (sprite instanceof Player && !consumed) {
			Player player = (Player) sprite;
			player.addLife();

			consume();
		}
	}

	/** Consumes an ExtraLife */
	public void consume() {
		consumed = true;
	}

	/**
	 * Gets the state of the consumed flag
	 * 
	 * @return The state of the consumed flag
	 */
	public boolean isConsumed() {
		return consumed;
	}
}
