package ch.zhaw.catan;

/**
 * This class models a settlement. A settlement defines an owner and a string
 * representation. The worth of a settlement is 1 e.g. when getting points or
 * resources.
 * 
 * @author Nikola Jovanovic
 *
 */
public class Settlement extends Meeple {

	public static final int WORTH = 1;

	/**
	 * Creates a new settlement with a specified owner. The owner is a player from
	 * {@link Config.Faction}.
	 * 
	 * @param owner the player to whom this settlement belongs to
	 */
	public Settlement(Config.Faction owner) {
		super(owner);
	}

	@Override
	public String toString() {
		return super.toString();
	}
}
