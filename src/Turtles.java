import org.newdawn.slick.Color;
import org.newdawn.slick.Input;

/**
 * Represents a row of Turtles. A Floater that submerges itself periodically
 * 
 * @see Floater
 */
public final class Turtles extends Floater {

	private static final String IMAGE_SRC = "assets/turtles.png";
	private final static float SPEED = 0.085f;

	private static final Color SUNK_COLOR = new Color(39, 83, 119);

	private final static int PERIOD = 9000;
	private final static int SURFACE_TIME = 7000;

	private boolean surfaced = true;

	/**
	 * Constructs a row of Turtles
	 * 
	 * @param x     X coordinate
	 * @param y     Y coordinate
	 * @param right true if direction of travel is in the right direction, else
	 *              false
	 */
	public Turtles(float x, float y, boolean right) {
		super(IMAGE_SRC, x, y, right, SPEED);
	}

	/**
	 * Renders the Turtles. If the Turtles are not surfaced, Turtles are rendered
	 * with SUNK_COLOR
	 */
	@Override
	public void render() {
		if (surfaced) {
			super.render();
		} else {
			super.render(SUNK_COLOR);
		}
	}

	/**
	 * Turtles-Sprite collision behaviour.
	 * 
	 * @param Sprite A Sprite
	 * @param delta  Time since last update call (ms)
	 */
	@Override
	public void onCollision(Sprite sprite, int delta) {
		if (surfaced) {
			super.onCollision(sprite, delta);
		}
	}

	/**
	 * Surfaces/sinks the Turtles if time interval has passed
	 */
	@Override
	public void update(Input input, int delta) {
		super.update(input, delta);

		surfaced = (getAge() % PERIOD <= SURFACE_TIME);
	}
}
