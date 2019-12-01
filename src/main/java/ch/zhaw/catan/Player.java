package ch.zhaw.catan;

import java.util.*;

import ch.zhaw.catan.Config.Faction;


/**
 * @author blatt
 *
 */
public class Player {
	private int winPoints;
//	private static List<Faction> playerColors = new ArrayList<>();
//	private static int colorIndex = 0;
	private List<Meeple> meeples;
	Faction playerFaction;
	
	/**
	 * The constructor creats a player with a list for his meeples.
	 * The player is identified by his colour.
	 * 
	 * @param playerFaction The colour from faction.
	 */
	public Player(Config.Faction playerFaction) {
		this.playerFaction = playerFaction;
		meeples = new ArrayList<>();
		winPoints = 0;
//		colorIndex++;
	}
	
	/**
	 * This method counts the numbers of roads in the ArrayList of the 
	 * player and checks the numbers with the maximal numbers of roads
	 * who are available pro player.
	 * 
	 * @return true or false: for available roads.
	 */
	public boolean hasAvailableRoads() {
		int usedRoads = 0;
		Iterator<Meeple> it = meeples.iterator();
		while(it.hasNext()) {
			Meeple m1 = it.next();
			if(m1 instanceof Road) {
				usedRoads++;
			}
		}
		return Config.Structure.ROAD.getStockPerPlayer() > usedRoads;
	}
	
	
	/**
	 * This method counts the numbers of settlements in the ArrayList of 
	 * the player and checks the numbers with the maximal numbers of 
	 * settlements who are available pro player.
	 * 
	 * @return true or false: for available settlements.
	 */
	 public boolean hasAvailableSettlements() {
		int usedSettlements = 0;
		Iterator<Meeple> it = meeples.iterator();
		while(it.hasNext()) {
			Meeple m1 = it.next();
			if(m1 instanceof Settlement) {
				usedSettlements++;
			}
		}
		return Config.Structure.SETTLEMENT.getStockPerPlayer() > usedSettlements;
	 }
	 
//	 public boolean hasAvailableCities() {
//			int usedCities = 0;
//			Iterator<Meeple> it = meeples.iterator();
//			while(it.hasNext()) {
//				Meeple m1 = it.next();
//				if(m1 instanceof City) {
//					usedCities++;
//				}
//			}
//			return Config.Structure.CITY.getStockPerPlayer() > usedCities;
//	 }

	 /**
	  * This method returns the colour of the player.
	  * 
	  * @return playerFaction
	  */
	public Faction getPlayerFaction() {
		return playerFaction;
	}
	
	/**
	 * This method returns the victory points of the player. 
	 * 
	 * @return winPoints
	 */
	public int getWinPoints() {
		return winPoints;
	}
	
	/**
	 * This Method set the victory points.
	 * 
	 * @param newWinPoints
	 */
	public void setWinPoints(int newWinPoints) {
		winPoints = newWinPoints;
	}
	
	/**
	 * This method saves one meeple in the ArrayList of the player.
	 * 
	 * @param meeple
	 */
	public void initializeMeeple(Meeple meeple) {
		meeples.add(meeple);
	}
	
//	public void initializeRoad(Faction owner) {
//		meeples.add(new Road(owner));
//	}
	
//	public void initializeSettlement(Faction owner) {
//		meeples.add(new Settlement(owner));
//	}
	
//	public void initializeCity(Faction owner) {
//		meeples.add(new City(owner));
//	}
	
	
//TODO Liste in SidlerGame speichern??? Farbe wird dann an den Konstruktor
	// von Player übergeben.
	
//	public static final void setColors() {
//		playerColors.add(Config.Faction.RED);
//		playerColors.add(Config.Faction.BLUE);
//		playerColors.add(Config.Faction.GREEN);
//		playerColors.add(Config.Faction.YELLOW);
//	}
}

