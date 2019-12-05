package ch.zhaw.catan;

import java.awt.Point;
import java.util.*;

import ch.zhaw.catan.Config.Faction;
import ch.zhaw.catan.Config.Resource;


/**
 * This class models a player, the player is identified by his color. 
 * The player has two lists, one for his meeples and one for his resources.
 * 
 * @author Peter Blattmann
 *
 */
public class Player {
	private int winPoints;
	private List<Meeple> meeples;
	private List<Resource> resourceStock;
	private Faction playerFaction;
	
	/**
	 * The constructor creats a player with a list for his meeples.
	 * The player is identified by his color.
	 * 
	 * @param playerFaction The color from faction.
	 */
	public Player(Config.Faction playerFaction) {
		this.playerFaction = playerFaction;
		meeples = new ArrayList<>();
		resourceStock = new ArrayList<>();
		winPoints = 0;
	}
	
	/**
	 * The player receives a resource for a settlement.
	 * 
	 * @param resource count this resource + 1
	 */
	public void addRescourceFromSettlement(Resource resource) {
		resourceStock.add(resource);
	}
	
	/**
	 * The player receives two resources for a city.
	 * 
	 * @param resource Count this resource + 2
	 */
	public void addRescourceFromCity(Resource resource) {
		resourceStock.add(resource);
		resourceStock.add(resource);
	}
	
	/**
	 * This method removes a certain number of one resource type.
	 * 
	 * @param number The number of a certain resource type who get spend
	 * @param resource The type of resource
	 */
	public void removeResource(int number, Resource resource) {
		boolean running = true;
		int index = 0;
		Iterator<Resource> it = resourceStock.iterator();
		while(running && it.hasNext()) {
			Resource r1 = it.next();
			if(r1 == resource) {
				it.remove();
				index++;
			}
			if(index == number) {
				running = false;
			}
		}
	}
	
	/**
	 * This method gives a String with all counted resources. The player
	 * can show his stock of resources.
	 * 
	 * @return formatResources A String with all counted resources.
	 */
	public String getFormatResources() {
		//TODO Funktioniert die Ausgabe?
		String formatResources = "";
		if(getNumberOfTotalResources() == 0) {
			formatResources = "It seems like you don't have any resources :(";
		}
		for(Resource resource : resourceStock ) {
			formatResources += resource + " | " + getNumberOfSingleResource(resource) + " Pieces \n";
		}
		return formatResources;
	}
	
	/**
	 * This method returns the total number of  a certain resource
	 * type on the hand of the player. 
	 * 
	 * @param resource The type of resource
	 * @return The number of a certain resource type 
	 */
	public int getNumberOfSingleResource(Resource resource) {
		int numberOfSingleResource = 0;
		Iterator<Resource> it = resourceStock.iterator();
		while(it.hasNext()) {
			Resource r1 = it.next();
			if(r1 == resource) {
				numberOfSingleResource++;
			}
		}
		return numberOfSingleResource;
	}
	
	/**
	 * This method returns the number of total resources on the hand 
	 * of the player.
	 * 
	 * @return number of total resources
	 */
	public int getNumberOfTotalResources() {
		return resourceStock.size();
	}
	
	/**
	 * This method returns the list of resource.
	 * 
	 * @return resourceStock ArrayList
	 */
	public List<Resource> getResourceStock() {
		return resourceStock;
	}
	
	/**
	 * This method removes a settlement.
	 * 
	 */
	public void removeSettlement() {
		boolean running = true;
		Iterator<Meeple> it = meeples.iterator();
		while(it.hasNext() && running) {
			Meeple m1 = it.next();
			if(m1 instanceof Settlement) {
				it.remove();
				running = false;
			}
		}	
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
	 
	 /**
	  * This method counts the numbers of cities in the ArrayList of the 
	  * player and checks the numbers with the maximal numbers of cities 
	  * who are available pro player.
	  * 
	  * @return true or false: for available cities.
	  */
	 public boolean hasAvailableCities() {
		int usedCities = 0;
		Iterator<Meeple> it = meeples.iterator();
		while(it.hasNext()) {
			Meeple m1 = it.next();
			if(m1 instanceof City) {
				usedCities++;
			}	
		}
		return Config.Structure.CITY.getStockPerPlayer() > usedCities;
	 }

	 /**
	  * This method returns the list of meeples.
	  * 
	  * @return meeples ArrayList
	  */
	 public List<Meeple> getMeepleList() {
		 return meeples;
	 }
	 
	 /**
	  * This method returns the color of the player.
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
}
