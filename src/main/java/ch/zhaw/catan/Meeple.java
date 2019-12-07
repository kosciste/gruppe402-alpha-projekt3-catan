package ch.zhaw.catan;

/**
 * This abstract class is a superclass for specific meeples. A meeple defines an
 * owner and a string representation.
 * 
 * @author Nikola Jovanovic
 *
 */
public abstract class Meeple {

	public final Config.Faction OWNER;

	public Meeple(Config.Faction owner) {
		OWNER = owner;
	}

	@Override
	public String toString() {
		return OWNER.toString();
	}
}