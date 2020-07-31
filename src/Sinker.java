import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public abstract class Sinker extends Floater {
	
	private static final Color SUNK_COLOR = new Color(39, 83, 119);
	
	private boolean surfaced;
	private int sinkTime;
	private int surfaceTime;
	
	/** Constructs a Sinker
	 * @param image Image of Sinker
	 * @param x X coordinate
	 * @param y Y coordinate
	 * @param right Indicates the direction of movement. true if movement is in right direction
	 * @param sinkTime Time interval before sinking
	 * @param surfaceTime Time interval until surfacing
	 * @throws SlickException 
     */
	public Sinker(Image image, float x, float y, boolean right, float speed, int sinkTime, int surfaceTime) {
		super(image, x, y, right, speed);

		surfaced = true;
		this.sinkTime = sinkTime;
		this.surfaceTime = surfaceTime;
	}
	
	/** Renders the Sinker. If !surfaced, Sinker is rendered with color SUNK_COLOR */
	@Override
	public void render() {
		if (surfaced) {
			super.render();
		} else {
			super.render(SUNK_COLOR);
		}
	}
	
	/** If surfaced, behaves as Floater, else ignored */
	@Override
	public void contactPlayer(Player player, int delta) {
		if (surfaced) {
			super.contactPlayer(player, delta);
		}
	}
	
	/** Surfaces/sinks the Floater if time interval has passed */
	@Override
	public void update(Input input, int delta) {
		super.update(input, delta);
		
		int age = getAge();
		
		if (intervalPassed(age, surfaceTime, delta)) {
			surfaced = true;
		} else {
			age %= surfaceTime;
			
			if (intervalPassed(age, sinkTime, delta)) {
				surfaced = false;
			}
		}
	}
	
	public boolean isSurfaced() {
		return surfaced;
	}
}
