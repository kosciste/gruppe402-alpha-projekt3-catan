package ch.zhaw.catan;

/**
 * @author Nikola Jovanovic
 *
 */
public class Road extends Meeple {
	
	public Road(Config.Faction owner) {
		super(owner);
	}
	
	@Override
	public String toString() {
		return super.toString().substring(0, 1);
	}
}
