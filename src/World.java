import java.io.BufferedReader;
import java.io.FileReader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

/** Represents a World. Handles its Sprite's rendering and updating */
public class World {

	/** Tile width (px) */
	public static final float TILE_WIDTH = 48;
	/** Tile height (px) */
	public static final float TILE_HEIGHT = 48;

	// format of path of .lvl files
	private static final String LEVEL_FILE_FORMAT = "assets/levels/%d.lvl";

	// maximum .lvl file
	private static final int MAX_LEVEL = 1;
	private static final int INITIAL_LEVEL = 0;
	
	// extraLifeCountDown range
	private static final int NEW_LIFE_LOW = 25000;
	private static final int NEW_LIFE_HIGH = 35000;

	// Goal Locations
	private static final float GOAL_X_START = 120;
	private static final float GOAL_X_SEPARATION = 192;
	private static final float GOAL_Y = 48;

	// .lvl parsing types
	private static enum ParseType {
		GRASS("grass"), WATER("water"), TREE("tree"), BUS("bus"), RACECAR("racecar"), BIKE("bike"),
		BULLDOZER("bulldozer"), SHORTLOG("log"), LONGLOG("longLog"), TURTLES("turtle");

		private String value;

		private ParseType(String value) {
			this.value = value;
		}

		/**
		 * Gets the ParseType by it's value
		 * 
		 * @param value Value to look for
		 * 
		 * @return ParseType with corresponding value
		 * 
		 * @throws Exception on unknown value
		 */
		public static ParseType getByValue(String value) throws Exception {
			for (ParseType pt : values()) {
				if (pt.value.equals(value)) {
					return pt;
				}
			}
			throw new Exception("Type \"" + value + "\" not recognised.");
		}

		/**
		 * Gets the value associated with a ParseType
		 * 
		 * @return String value of ParseType
		 */
		public String getValue() {
			return value;
		}
	}

	// sprites
	private Player player;
	// master ArrayList
	private ArrayList<Sprite> sprites;
	// sub ArrayLists
	// not for update/render
	private ArrayList<Log> logs;
	private ArrayList<Goal> goals;

	private int level;
	private int newLifeCountDown;
	private Random rng;

	/**
	 * Constructs a World
	 */
	public World() {
		player = new Player();

		// seed RNG
		rng = new Random(System.currentTimeMillis());

		level = INITIAL_LEVEL;
		buildLevel();
	}

	/**
	 * Updates all Sprites in the World. Handles for win/loss state
	 * 
	 * @param input The Slick input object
	 * @param delta Time passed since last update (ms)
	 */
	public void update(Input input, int delta) {
		// update all Sprites
		for (int i = 0; i < sprites.size(); i++) {
			Sprite sprite = sprites.get(i);

			player.checkCollision(sprite, delta);

			sprite.update(input, delta);
			sprite.checkCollision(player, delta);
		}

		tryAddExtraLife(delta);

		checkGameState();
	}

	/**
	 * Renders all Sprites in the World
	 * 
	 * @param g The Slick Graphics object
	 */
	public void render(Graphics g) {
		// render Sprites in correct order
		// reversed order to update
		for (int i = sprites.size() - 1; i >= 0; i--) {
			sprites.get(i).render();
		}
	}

	private void buildLevel() {

		// reset sprite ArrayLists
		sprites = new ArrayList<>();
		logs = new ArrayList<>();
		goals = new ArrayList<>();

		resetNewLifeCountDown();

		// read .lvl file (csv), and add sprites
		try (BufferedReader reader = new BufferedReader(new FileReader(getLevelFile()))) {
			String line = null;

			// read lines
			while ((line = reader.readLine()) != null) {
				addSprite(parseSprite(line));
			}
		} catch (Exception e) {
			App.handleException(e);
		}

		// add goals. hard-coded
		for (float x = GOAL_X_START; x < App.SCREEN_WIDTH; x += GOAL_X_SEPARATION) {
			addSprite(new Goal(x, GOAL_Y));
		}

		// reset player
		player.resetPosition();
		addSprite(player);

		Collections.sort(sprites);
	}

	private void checkGameState() {
		// check for player death
		if (player.isDead()) {
			App.endGame("You died! Better luck next time.");
		}

		// check if we should move to the next level
		if (allGoalsTaken()) {
			// next level
			level++;
			if (level > MAX_LEVEL) {
				App.endGame("You won! Congratulations.");
			} else {
				buildLevel();
			}
		}
	}

	private boolean allGoalsTaken() {
		for (Goal goal : goals) {
			if (!goal.isTaken()) {
				return false;
			}
		}

		return true;
	}

	private void resetNewLifeCountDown() {
		newLifeCountDown = NEW_LIFE_LOW + rng.nextInt(NEW_LIFE_HIGH - NEW_LIFE_LOW + 1);
	}

	private void tryAddExtraLife(int delta) {

		if (!extraLifeExists()) {
			newLifeCountDown -= delta;

			// if count down has finished
			if (newLifeCountDown <= 0) {
				addExtraLife();

				resetNewLifeCountDown();
			}
		}
	}

	private boolean extraLifeExists() {
		// look for an ExtraLife on a Log
		for (Log log : logs) {
			if (log.hasExtraLife()) {
				return true;
			}
		}

		return false;
	}

	private void addExtraLife() {
		if (logs.size() > 0) {
			int index = rng.nextInt(logs.size());
			logs.get(index).addExtraLife();
		}
	}

	private String getLevelFile() {
		return String.format(LEVEL_FILE_FORMAT, level);
	}

	private void addSprite(Sprite sprite) {
		// add sprite to master ArrayList
		sprites.add(sprite);

		// add sprite to sub ArrayLists, if required by type
		if (sprite instanceof Log) {
			logs.add((Log) sprite);
		}
		if (sprite instanceof Goal) {
			goals.add((Goal) sprite);
		}
	}

	// .lvl line parser
	private Sprite parseSprite(String line) throws Exception {
		// get values from line
		String[] values = line.split(",");

		// it is assumed that these three (type,x,y) are present
		ParseType type = ParseType.getByValue(values[0]);
		float x = Float.parseFloat(values[1]), y = Float.parseFloat(values[2]);

		// read direction, if present
		boolean right = true;
		if (values.length == 4) {
			right = Boolean.parseBoolean(values[3]);
		}

		// return a new Sprite of correct type
		switch (type) {
			case GRASS:
				return new Grass(x, y);
			case WATER:
				return new Water(x, y);
			case TREE:
				return new Tree(x, y);
			case BUS:
				return new Bus(x, y, right);
			case RACECAR:
				return new Racecar(x, y, right);
			case BIKE:
				return new Bike(x, y, right);
			case BULLDOZER:
				return new Bulldozer(x, y, right);
			case SHORTLOG:
				return new ShortLog(x, y, right);
			case LONGLOG:
				return new LongLog(x, y, right);
			case TURTLES:
				return new Turtles(x, y, right);
			default:
				throw new Exception("\"" + type.getValue() + "\" cannot be parsed.");
		}
	}
}
