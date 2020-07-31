import org.newdawn.slick.Image;

public class Item extends NonPlayer {

	/** Flag to indicate whether item has been consumed */
	private boolean consumed;
	
	/** Constructs an Item
	 * @param image Image used to draw Item
	 * @param x X coordinate
	 * @param y Y coordinate
	 * @param right Indicates the direction of movement. true if movement is in right direction
	 * @param speed Magnitude of movement
     */
	public Item(Image image, float x, float y, boolean right, float speed) {
		super(image, x, y, right, speed);
		
		consumed = false;
	}

	/** Consumes an Item */
	public void consume() {
		consumed = true;
	}
	
	public boolean isConsumed() {
		return consumed;
	}
}
