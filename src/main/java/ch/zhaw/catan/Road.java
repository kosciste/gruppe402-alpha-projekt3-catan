package ch.zhaw.catan;

import java.awt.Point;

/**
 * This class models a road. A road defines an owner and a string
 * representation.
 * 
 * @author Nikola Jovanovic
 *
 */
public class Road extends Meeple {

	private Point roadStart;
	private Point roadEnd;

	/**
	 * Creates a new road with a specified owner. The owner is a player from
	 * {@link Config.Faction}.
	 * 
	 * @param owner the player to whom this road belongs to
	 */
	public Road(Config.Faction owner, Point roadStart, Point roadEnd) {
		super(owner);
		this.roadStart = roadStart;
		this.roadEnd = roadEnd;

	}

	@Override
	/**
	 * Example: 'r' for player RED, 'b' for player BLUE
	 * @return a string representation of the road, depending on the owner
	 */
	public String toString() {
		return super.toString().substring(0, 1);
	}

	public Point getRoadEnd() {
		return roadEnd;
	}

	public Point getRoadStart() {
		return roadStart;
	}

	public String getUniqueID(){
		return super.toString().substring(0, 1) + roadStart + roadEnd;
	}

	public String getUniqueIDreverse(){
		return super.toString().substring(0, 1) + roadEnd + roadStart;
	}
}
