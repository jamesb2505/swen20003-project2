import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;

import utilities.BoundingBox;

/** Represents a Sprite */
public abstract class Sprite implements Comparable<Sprite> {

	// ordering for correct updating/rendering of Sprites
	// all others have order of ORDERS.length, such as non-Solid Tiles
	private static final Class<?>[] ORDERS = { Player.class, Solid.class, Entity.class };

	/**
	 * Marker Interface for Solid Sprites. Sprites that should not be able to be
	 * moved into by a Player
	 */
	public abstract interface Solid {
		// intentionally blank
	}

	/**
	 * Marker Interface for Hazardous Sprites. Sprites that, when contacting a
	 * Player should remove life
	 */
	public abstract interface Hazard {
		// intentionally blank
	}

	private Image image;
	private float x;
	private float y;
	private BoundingBox boundingBox;
	private int order = 0;

	/**
	 * Constructs a Sprite
	 * 
	 * @param imageSrc Path of image source
	 * @param x        x coordinate of Sprite
	 * @param y        y coordinate of Sprite
	 */
	public Sprite(String imageSrc, float x, float y) {
		this.image = loadImage(imageSrc);
		this.x = x;
		this.y = y;
		this.boundingBox = new BoundingBox(image, x, y);

		setOrder();
	}

	@Override
	public int compareTo(Sprite sprite) {
		return order - sprite.order;
	}

	/**
	 * Placeholder for further Sprite updates
	 * 
	 * @param input The Slick input object
	 * @param delta Time passed since last update (ms)
	 */
	public void update(Input input, int delta) {
		// intentionally blank
		// not all Sprites require some update behaviour
	}

	/** Draws image at the Sprite's x,y coordinate */
	public void render() {
		image.drawCentered(x, y);
	}

	/**
	 * Draws image at the Sprite's x,y coordinate, colored
	 * 
	 * @param color Color Object
	 */
	public void render(Color color) {
		image.drawFlash(getLeft(), getTop(), getWidth(), getHeight(), color);
	}

	/**
	 * Check if two Sprites contact
	 * 
	 * @param other Another Sprite
	 * 
	 * @return boolean true if the Sprites' BoundingBoxes intersect, else false
	 */
	public boolean isContacting(Sprite sprite) {
		return boundingBox.intersects(sprite.boundingBox);
	}

	/**
	 * Default Sprite-Sprite collision behaviour. Handles Hazard-Player collision.
	 * 
	 * @param sprite A Sprite
	 * @param delta  Time since last update call (ms)
	 */
	public void onCollision(Sprite sprite, int delta) {
		// Hazard-Sprite collison
		if (this instanceof Hazard && sprite instanceof Player) {
			Player player = (Player) sprite;
			player.removeLife();
		}
	}

	/**
	 * Checks for Sprite-Sprite contact and performs collision behavior as defined
	 * by onCollision
	 * 
	 * @param sprite Sprite to check for contact with
	 * @param delta  Time since last update call (ms)
	 */
	public void checkCollision(Sprite sprite, int delta) {
		if (this != sprite && isContacting(sprite)) {
			onCollision(sprite, delta);
		}
	}

	/**
	 * Moves a Sprite's position
	 * 
	 * @param dx Change in x position
	 * @param dy Change in y position
	 */
	public void move(float dx, float dy) {
		moveX(dx);
		moveY(dy);
	}

	/**
	 * Moves a Sprite's x position
	 * 
	 * @param dx Change in x position
	 */
	public void moveX(float dx) {
		setX(x + dx);
	}

	/**
	 * Moves a Sprite's y position
	 * 
	 * @param dx Change in y position
	 */
	public void moveY(float dy) {
		setY(y + dy);
	}

	/**
	 * Gets the Sprite's x position
	 * 
	 * @return x position
	 */
	public float getX() {
		return x;
	}

	/**
	 * Gets the Sprite's y position
	 * 
	 * @return y position
	 */
	public float getY() {
		return y;
	}

	/**
	 * Sets the Sprite's x position
	 * 
	 * @param x New x position
	 */
	public void setX(float x) {
		this.boundingBox.setX(x);
		this.x = x;
	}

	/**
	 * Sets the Sprite's y position
	 * 
	 * @param y New y position
	 */
	public void setY(float y) {
		this.boundingBox.setY(y);
		this.y = y;
	}

	/**
	 * Gets the Sprite's Image width
	 * 
	 * @return width of Sprite
	 */
	public float getWidth() {
		return image.getWidth();
	}

	/**
	 * Gets the Sprite's Image height
	 * 
	 * @return height of Sprite
	 */
	public float getHeight() {
		return image.getHeight();
	}

	/**
	 * Gets the Sprite's Image left side coordinate
	 * 
	 * @return left coordinate of sprite
	 */
	public float getLeft() {
		return x - getWidth() / 2;
	}

	/**
	 * Gets the Sprite's Image right side coordinate
	 * 
	 * @return right coordinate of sprite
	 */
	public float getRight() {
		return x + getWidth() / 2;
	}

	/**
	 * Gets the Sprite's Image top side coordinate
	 * 
	 * @return top coordinate of sprite
	 */
	public float getTop() {
		return y - getHeight() / 2;
	}

	/**
	 * Gets the Sprite's Image bottom side coordinate
	 * 
	 * @return bottom coordinate of sprite
	 */
	public float getBottom() {
		return y + getHeight() / 2;
	}

	/**
	 * Sets the Sprite's BoundingBox's height
	 * 
	 * @param height new BoundingBox height
	 */
	public void setBoundingBoxWidth(float width) {
		boundingBox.setWidth(width);
	}

	/**
	 * Sets the Sprite's BoundingBox's height
	 * 
	 * @param height new BoundingBox height
	 */
	public void setBoundingBoxHeight(float height) {
		boundingBox.setHeight(height);
	}

	/** Flips the Sprite's image horizontally */
	public void flipImage() {
		image = image.getFlippedCopy(true, false);
	}

	/**
	 * Handles Image instantiation
	 * 
	 * @param imageSrc Source of Image
	 * @return new Image
	 */
	public static Image loadImage(String imageSrc) {
		try {
			return new Image(imageSrc);
		} catch (Exception e) {
			App.handleException(e);
		}

		return null;
	}

	private void setOrder() {
		Class<?> cls = getClass();

		// default order
		order = ORDERS.length;

		// try to find correct order, if not default
		for (int i = 0; i < ORDERS.length; i++) {
			// equivalent to (this instanceof ORDERS[i]) for Class<?>
			if (ORDERS[i].isAssignableFrom(cls)) {
				order = i;
				break;
			}
		}
	}
}
