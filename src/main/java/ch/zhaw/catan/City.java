package ch.zhaw.catan;

/**
 * This class models a city. A city is the next level from a settlement.
 * The city gives two point of resources back and also two winpoints.
 * 
 * @author Peter Blattmann
 *
 */
public class City extends Meeple {

	
	
	/**
	 * Creats a new city with a specified owner.
	 * 
	 * @param owner The owner of the city
	 */
	public City(Config.Faction owner) {
		super(owner);
	}
	
	@Override
	public String toString() {
		return super.toString().toUpperCase();
	}
}
