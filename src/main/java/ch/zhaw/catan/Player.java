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
//	private List<Meeple> meeples;
	Faction playerFaction;
	
	
	public Player(Config.Faction playerFaction) {
		this.playerFaction = playerFaction;
//		meeples = new ArrayList<>();
		winPoints = 0;
//		colorIndex++;
	}
	
	public int getWinPoints() {
		return winPoints;
	}
	
	public void setWinPoints(int newWinPoints) {
		winPoints = newWinPoints;
	}
	
	public void initializeMeeple() {
//		meeples.add(new Meeple())
	}
	
//TODO Liste in SidlerGame speichern??? Farbe wird dann an den Konstruktor
	// von Player übergeben.
	
//	public static final void setColors() {
//		playerColors.add(Config.Faction.RED);
//		playerColors.add(Config.Faction.BLUE);
//		playerColors.add(Config.Faction.GREEN);
//		playerColors.add(Config.Faction.YELLOW);
//	}
}

