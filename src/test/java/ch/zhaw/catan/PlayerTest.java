package ch.zhaw.catan;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import ch.zhaw.catan.Config.Resource;

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
	
	@Test
	public void testRemoveSettlement() {
		Player player = new Player(Config.Faction.RED);
		player.initializeMeeple(new Settlement(Config.Faction.RED));
		player.initializeMeeple(new Settlement(Config.Faction.RED));
		player.initializeMeeple(new Settlement(Config.Faction.RED));
		player.initializeMeeple(new Settlement(Config.Faction.RED));
		player.initializeMeeple(new Settlement(Config.Faction.RED));
		player.removeSettlement();
		assertTrue(player.hasAvailableSettlements());
	}
	
	@Test
	public void zeroGetFormatResources() {
		Player player = new Player(Config.Faction.RED);
		assertEquals("It seems like you don't have any resources :(", player.getFormatResources());
	}	
	
	@Test
	public void twoGetFormatResources() {
		Player player = new Player(Config.Faction.RED);
		player.addRescourceFromSettlement(Resource.GRAIN);
		player.addRescourceFromSettlement(Resource.GRAIN);
		assertEquals("GR | 2 Pieces \n", player.getFormatResources());
	}
	
	@Test
	public void sixGetFormatResources() {
		Player player = new Player(Config.Faction.RED);
		player.addRescourceFromCity(Resource.WOOL);
		player.addRescourceFromSettlement(Resource.WOOD);
		player.addRescourceFromSettlement(Resource.WOOD);
		player.addRescourceFromSettlement(Resource.CLAY);
		player.addRescourceFromSettlement(Resource.STONE);
		assertEquals("WL | 2 Pieces \n"
				+ "WD | 2 Pieces \n"
				+ "ST | 1 Pieces \n"
				+ "CL | 1 Pieces \n" , player.getFormatResources());
	}
	
	@Test
	public void testremoveResources() {
		Player player = new Player(Config.Faction.RED);
		player.addRescourceFromSettlement(Resource.GRAIN);
		player.addRescourceFromSettlement(Resource.GRAIN);
		player.addRescourceFromSettlement(Resource.WOOD);
		player.addRescourceFromSettlement(Resource.WOOD);
		player.addRescourceFromSettlement(Resource.CLAY);
		player.addRescourceFromSettlement(Resource.CLAY);
		player.addRescourceFromSettlement(Resource.GRAIN);
		player.addRescourceFromSettlement(Resource.STONE);
		player.removeResource(2, Resource.WOOD);
		assertEquals("GR | 3 Pieces \n"
				+ "ST | 1 Pieces \n"
				+ "CL | 2 Pieces \n", player.getFormatResources());
	}
	
	
}
