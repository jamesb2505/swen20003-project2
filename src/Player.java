import org.newdawn.slick.Image;
import org.newdawn.slick.Input;

/**
 * Represents a Player. An Input controlled Sprite
 * 
 * @see Sprite
 */
public final class Player extends Sprite {

	private static final String IMAGE_SRC = "assets/frog.png";
	private static final String LIFE_IMAGE_SRC = "assets/lives.png";

	private static final int INITIAL_X = 512;
	private static final float INITIAL_Y = 720;

	private static final int INITIAL_LIVES = 3;
	private static final int LIFE_X = 24;
	private static final int LIFE_Y = 744;
	private static final int LIFE_SEPARATION = 32;

	private int lives;
	private Image lifeCounterImage;
	private boolean safe;
	private float prevX;
	private float prevY;
	private boolean moveSuccess;

	/**
	 * Constructs a Player
	 */
	public Player() {
		super(IMAGE_SRC, INITIAL_X, INITIAL_Y);

		setSafe(false);
		lives = INITIAL_LIVES;
		lifeCounterImage = loadImage(LIFE_IMAGE_SRC);
	}

	/**
	 * Moves the Player based on user input. Sets Player's safe flag to false
	 * 
	 * @param input The Slick Input object
	 * @param delta Time passed since last update (ms)
	 */
	@Override
	public void update(Input input, int delta) {

		setSafe(false);

		// accumulate the total movement in x and y directions
		float dx = 0, dy = 0;
		if (input.isKeyPressed(Input.KEY_LEFT)) {
			dx -= World.TILE_WIDTH;
		}
		if (input.isKeyPressed(Input.KEY_RIGHT)) {
			dx += World.TILE_WIDTH;
		}
		if (input.isKeyPressed(Input.KEY_UP)) {
			dy -= World.TILE_HEIGHT;
		}
		if (input.isKeyPressed(Input.KEY_DOWN)) {
			dy += World.TILE_HEIGHT;
		}

		move(dx, dy);
	}

	/**
	 * Player-Sprite collision behaviour.
	 * 
	 * @param sprite A Sprite
	 * @param delta  Time since last update call (ms)
	 */
	@Override
	public void onCollision(Sprite sprite, int delta) {
		if (sprite instanceof Solid) {
			moveBack();
		}
	}

	/** Draws the Player and Life Counter */
	@Override
	public void render() {
		super.render();

		// draw life counter
		for (int i = 0; i < lives; i++) {
			lifeCounterImage.drawCentered(LIFE_X + LIFE_SEPARATION * i, LIFE_Y);
		}
	}

	/**
	 * Moves a Player's x coordinate. Only performs movement if it is within the
	 * screen
	 * 
	 * @param dx Change in x coordinate
	 */
	@Override
	public void moveX(float dx) {
		prevX = getX();

		// check if x movement is within screen
		if (validMove(prevX + dx, App.SCREEN_WIDTH, getWidth())) {
			super.moveX(dx);
		}
	}

	/**
	 * Moves a Player's y coordinate. Only performs movement if it is within the
	 * screen
	 * 
	 * @param dy Change in y coordinate
	 */
	@Override
	public void moveY(float dy) {
		prevY = getY();

		// check if y movement is within screen
		if (validMove(prevY + dy, App.SCREEN_HEIGHT, getHeight())) {
			super.moveY(dy);
		}
	}

	private boolean validMove(float pos, float bound, float size) {
		// set the moveSuccess flag
		return (moveSuccess = size / 2 <= pos && pos <= bound - size / 2);
	}

	/** If not safe, takes a life from the Player and resets position */
	public void removeLife() {
		if (!safe) {
			lives--;
			resetPosition();
		}
	}

	/** Adds a life to the Player */
	public void addLife() {
		lives++;
	}

	/** Resets Player's position to initial position */
	public void resetPosition() {
		setX(INITIAL_X);
		setY(INITIAL_Y);
	}

	/** Moves Player to previous position */
	public void moveBack() {
		setX(prevX);
		setY(prevY);
	}

	/**
	 * Sets the Player's safety flag
	 * 
	 * @param safe The new value of the flag
	 */
	public void setSafe(boolean safe) {
		this.safe = safe;
	}

	/**
	 * Checks if the Player has died. Player is considered dead if it's life has
	 * fallen below zero
	 * 
	 * @return true if the player is dead, else false
	 */
	public boolean isDead() {
		return lives < 0;
	}
	
	/**
	 * Checks if the Player the previous move made by the player was successful
	 * 
	 * @return true if the previous move was successful, else false
	 */
	public boolean successfulMove() {
		return moveSuccess;
	}
}
