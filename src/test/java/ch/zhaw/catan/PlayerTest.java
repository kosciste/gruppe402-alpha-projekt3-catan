package ch.zhaw.catan;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * @author blatt
 *
 */
class PlayerTest {

	@Test
	public void startHasAvailableRoads() {
		Player player = new Player(Config.Faction.RED);
		assertTrue(player.hasAvailableRoads());
	}

	@Test
	public void normalHasAvailableRoads() {
		Player player = new Player(Config.Faction.RED);
		player.initializeMeeple(new Road(Config.Faction.RED));
		player.initializeMeeple(new Road(Config.Faction.RED));
		player.initializeMeeple(new Road(Config.Faction.RED));
		player.initializeMeeple(new Road(Config.Faction.RED));
		player.initializeMeeple(new Road(Config.Faction.RED));
		player.initializeMeeple(new Road(Config.Faction.RED));
		player.initializeMeeple(new Road(Config.Faction.RED));
		player.initializeMeeple(new Road(Config.Faction.RED));
		player.initializeMeeple(new Road(Config.Faction.RED));
		player.initializeMeeple(new Road(Config.Faction.RED));
		player.initializeMeeple(new Road(Config.Faction.RED));
		player.initializeMeeple(new Road(Config.Faction.RED));
		player.initializeMeeple(new Road(Config.Faction.RED));
		player.initializeMeeple(new Road(Config.Faction.RED));
		assertTrue(player.hasAvailableRoads());
	}
	
	@Test
	public void overMaxHasAvailableRoads() {
		Player player = new Player(Config.Faction.RED);
		player.initializeMeeple(new Road(Config.Faction.RED));
		player.initializeMeeple(new Road(Config.Faction.RED));
		player.initializeMeeple(new Road(Config.Faction.RED));
		player.initializeMeeple(new Road(Config.Faction.RED));
		player.initializeMeeple(new Road(Config.Faction.RED));
		player.initializeMeeple(new Road(Config.Faction.RED));
		player.initializeMeeple(new Road(Config.Faction.RED));
		player.initializeMeeple(new Road(Config.Faction.RED));
		player.initializeMeeple(new Road(Config.Faction.RED));
		player.initializeMeeple(new Road(Config.Faction.RED));
		player.initializeMeeple(new Road(Config.Faction.RED));
		player.initializeMeeple(new Road(Config.Faction.RED));
		player.initializeMeeple(new Road(Config.Faction.RED));
		player.initializeMeeple(new Road(Config.Faction.RED));
		player.initializeMeeple(new Road(Config.Faction.RED));
		assertFalse(player.hasAvailableRoads());
	}
	
	@Test
	public void startHasAvailableSettlements() {
		Player player = new Player(Config.Faction.RED);
		assertTrue(player.hasAvailableSettlements());
	}
	
	@Test
	public void normalHasAvailableSettlements() {
		Player player = new Player(Config.Faction.RED);
		player.initializeMeeple(new Settlement(Config.Faction.RED));
		player.initializeMeeple(new Settlement(Config.Faction.RED));
		player.initializeMeeple(new Settlement(Config.Faction.RED));
		player.initializeMeeple(new Settlement(Config.Faction.RED));
		assertTrue(player.hasAvailableSettlements());
	}
	
	@Test
	public void overMaxHasAvailableSettlements() {
		Player player = new Player(Config.Faction.RED);
		player.initializeMeeple(new Settlement(Config.Faction.RED));
		player.initializeMeeple(new Settlement(Config.Faction.RED));
		player.initializeMeeple(new Settlement(Config.Faction.RED));
		player.initializeMeeple(new Settlement(Config.Faction.RED));
		player.initializeMeeple(new Settlement(Config.Faction.RED));
		assertFalse(player.hasAvailableSettlements());
	}
	
	@Test
	public void startHasAvailableCities() {
		Player player = new Player(Config.Faction.RED);
		assertTrue(player.hasAvailableCities());
	}
	
	@Test
	public void normalHasAvailableCities() {
		Player player = new Player(Config.Faction.RED);
		player.initializeMeeple(new City(Config.Faction.RED));
		player.initializeMeeple(new City(Config.Faction.RED));
		player.initializeMeeple(new City(Config.Faction.RED));
		assertTrue(player.hasAvailableCities());
	}
	
	@Test
	public void overMaxHasAvailableCities() {
		Player player = new Player(Config.Faction.RED);
		player.initializeMeeple(new City(Config.Faction.RED));
		player.initializeMeeple(new City(Config.Faction.RED));
		player.initializeMeeple(new City(Config.Faction.RED));
		player.initializeMeeple(new City(Config.Faction.RED));
		assertFalse(player.hasAvailableCities());
	}
}
