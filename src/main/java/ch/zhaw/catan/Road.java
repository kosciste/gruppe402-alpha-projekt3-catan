package ch.zhaw.catan;

/**
 * This class models a road. A road defines an owner and a string
 * representation.
 * 
 * @author Nikola Jovanovic
 *
 */
public class Road extends Meeple {

	/**
	 * Creates a new road with a specified owner. The owner is a player from
	 * {@link Config.Faction}.
	 * 
	 * @param owner the player to whom this road belongs to
	 */
	public Road(Config.Faction owner) {
		super(owner);
	}

	@Override
	/**
	 * Example: 'r' for player RED, 'b' for player BLUE
	 * @return a string representation of the road, depending on the owner
	 */
	public String toString() {
		return super.toString().substring(0, 1);
	}
}
