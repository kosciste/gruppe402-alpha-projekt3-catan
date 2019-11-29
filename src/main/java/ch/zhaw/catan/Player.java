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
	
	
	public Player(Config.Faction playerFaction) {
		this.playerFaction = playerFaction;
		meeples = new ArrayList<>();
		winPoints = 0;
//		colorIndex++;
	}
	
	
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
	 
	public int getWinPoints() {
		return winPoints;
	}
	
	public void setWinPoints(int newWinPoints) {
		winPoints = newWinPoints;
	}
	
	public void initializeRoad(Faction owner) {
		meeples.add(new Road(owner));
	}
	
	public void initializeSettlement(Faction owner) {
		meeples.add(new Settlement(owner));
	}
	
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

