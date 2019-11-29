package ch.zhaw.catan;

/**
 * @author Nikola Jovanovic
 *
 */
public class Meeple {

	public final Config.Faction OWNER;
	
	public Meeple(Config.Faction owner) {
		OWNER = owner;
	}
	
	@Override
	public String toString() {
		return OWNER.toString();
	}
}