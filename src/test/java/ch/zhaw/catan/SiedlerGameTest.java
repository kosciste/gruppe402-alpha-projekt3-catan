package ch.zhaw.catan;

import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class SiedlerGameTest {

    @Test
    void switchToNextPlayer() {
        SiedlerGame siedlerGame = new SiedlerGame(4);
        siedlerGame.switchToNextPlayer();
        assertEquals(siedlerGame.getCurrentPlayer(),siedlerGame.getPlayer().get(1));
    }


    @Test
    void switchToPreviousPlayer() {
        SiedlerGame siedlerGame = new SiedlerGame(4);
        siedlerGame.switchToPreviousPlayer();
        assertEquals(siedlerGame.getPlayer().get(3), siedlerGame.getCurrentPlayer());
    }


    @Test
    void placeInitialSettlementValidValue() {
        SiedlerGame siedlerGame = new SiedlerGame(4);
        assertTrue(siedlerGame.placeInitialSettlement(new Point(5, 3), false));
    }
    @Test
    void placeInitialSettlementNotValidValue() {
        SiedlerGame siedlerGame = new SiedlerGame(4);

        //Not a valid corner
        assertFalse(siedlerGame.placeInitialSettlement(new Point(4, 0), false));

        //Corner not null
        siedlerGame.placeInitialSettlement(new Point(5,3), false);
        assertFalse(siedlerGame.placeInitialSettlement(new Point(5, 3), false));

        //Distance rule
        assertFalse(siedlerGame.placeInitialSettlement(new Point(5, 1), false));
    }

    @Test
    void placeInitialRoadStartAndEndNotSideBySide() {
    	SiedlerGame siedlerGame = new SiedlerGame(2);
    	
    	assertFalse(siedlerGame.placeInitialRoad(new Point(3, 7), new Point(11, 15)));
    }
    
    @Test
    void placeInitialRoadStartHasNoSettlementEndAdjoinsNothing() {
    	SiedlerGame siedlerGame = new SiedlerGame(2);
    	Point startOfNewRoad = new Point(7, 7);
    	Point endOfNewRoad = new Point(7, 9);
    	
    	assertFalse(siedlerGame.placeInitialRoad(startOfNewRoad, endOfNewRoad));
    }
    
    @Test
    void placeInitialRoadStartHasNoSettlementEndAdjoinsOwnRoad() {
    	SiedlerGame siedlerGame = new SiedlerGame(2);
    	Point startOfOwnRoad = new Point(7, 7);
    	Point endOfOwnRoad = new Point(7, 9);
    	siedlerGame.placeInitialSettlement(startOfOwnRoad, false);
    	siedlerGame.placeInitialRoad(startOfOwnRoad, endOfOwnRoad);
    	Point startOfNewRoad = new Point(8, 10);
    	
    	assertTrue(siedlerGame.placeInitialRoad(startOfNewRoad, endOfOwnRoad));
    }
    
    @Test
    void placeInitialRoadStartHasNoSettlementEndAdjoinsForeignRoad() {
    	SiedlerGame siedlerGame = new SiedlerGame(2);
    	Point startOfForeignRoad = new Point(7, 7);
    	Point endOfForeignRoad = new Point(7, 9);
    	siedlerGame.placeInitialSettlement(startOfForeignRoad, false);
    	siedlerGame.placeInitialRoad(startOfForeignRoad, endOfForeignRoad);
    	siedlerGame.switchToNextPlayer();
    	Point startOfNewRoad = new Point(8, 10);
    	
    	assertFalse(siedlerGame.placeInitialRoad(startOfNewRoad, endOfForeignRoad));
    }
    
    @Test
    void placeInitialRoadStartHasOwnSettlementEndAdjoinsNothing() {
    	SiedlerGame siedlerGame = new SiedlerGame(2);
    	Point startOfNewRoad = new Point(7, 7);
    	Point endOfNewRoad = new Point(7, 9);
    	siedlerGame.placeInitialSettlement(startOfNewRoad, false);
    	
    	assertTrue(siedlerGame.placeInitialRoad(startOfNewRoad, endOfNewRoad));
    }
    
    @Test
    void placeInitialRoadStartHasOwnSettlementEndAdjoinsOwnRoad() {
    	SiedlerGame siedlerGame = new SiedlerGame(2);
    	Point startOfNewRoad = new Point(7, 7);
    	Point endOfNewRoad = new Point(7, 9);
    	Point startOfOwnRoad = new Point(8, 10);
    	siedlerGame.placeInitialSettlement(startOfOwnRoad, false);
    	siedlerGame.placeInitialRoad(startOfOwnRoad, endOfNewRoad);
    	siedlerGame.placeInitialSettlement(startOfNewRoad, false);
    	
    	assertTrue(siedlerGame.placeInitialRoad(startOfNewRoad, endOfNewRoad));
    }
    
    @Test
    void placeInitialRoadStartHasOwnSettlementEndAdjoinsForeignRoad() {
    	SiedlerGame siedlerGame = new SiedlerGame(2);
    	Point startOfNewRoad = new Point(7, 7);
    	Point endOfNewRoad = new Point(7, 9);
    	Point startOfForeignRoad = new Point(8, 10);
    	siedlerGame.placeInitialSettlement(startOfForeignRoad, false);
    	siedlerGame.placeInitialRoad(startOfForeignRoad, endOfNewRoad);
    	siedlerGame.switchToNextPlayer();
    	siedlerGame.placeInitialSettlement(startOfNewRoad, false);
    	
    	assertTrue(siedlerGame.placeInitialRoad(startOfNewRoad, endOfNewRoad));
    }
    
    @Test
    void placeInitialRoadStartHasForeignSettlementEndAdjoinsNothing() {
    	SiedlerGame siedlerGame = new SiedlerGame(2);
    	Point startOfNewRoad = new Point(7, 7);
    	Point endOfNewRoad = new Point(7, 9);
    	siedlerGame.placeInitialSettlement(startOfNewRoad, false);
    	siedlerGame.switchToNextPlayer();
    	
    	assertFalse(siedlerGame.placeInitialRoad(startOfNewRoad, endOfNewRoad));
    }
    
    @Test
    void placeInitialRoadStartHasForeignSettlementEndAdjoinsForeignRoad() {
    	SiedlerGame siedlerGame = new SiedlerGame(2);
    	Point startOfNewRoad = new Point(7, 7);
    	Point endOfNewRoad = new Point(7, 9);
    	siedlerGame.placeInitialSettlement(startOfNewRoad, false);
    	Point startOfForeignRoad = new Point(8, 10);
    	siedlerGame.placeInitialSettlement(startOfForeignRoad, false);
    	siedlerGame.placeInitialRoad(startOfForeignRoad, endOfNewRoad);
    	siedlerGame.switchToNextPlayer();
    	
    	assertFalse(siedlerGame.placeInitialRoad(startOfNewRoad, endOfNewRoad));
    }

    @Test
    void buildSettlementWithValidConditions() {
        SiedlerGame  siedlerGame = new SiedlerGame(2);
        siedlerGame.placeInitialSettlement(new Point(5,3), false);
        siedlerGame.placeInitialRoad(new Point(5,3), new Point(6,4));
        siedlerGame.getCurrentPlayer().addRescourceFromCity(Config.Resource.CLAY);
        siedlerGame.getCurrentPlayer().addRescourceFromCity(Config.Resource.WOOD);
        siedlerGame.getCurrentPlayer().addRescourceFromCity(Config.Resource.WOOL);
        siedlerGame.getCurrentPlayer().addRescourceFromCity(Config.Resource.GRAIN);
        Road road = new Road(siedlerGame.getCurrentPlayer()
                .getPlayerFaction(),new Point(6,4), new Point(6,6));
        siedlerGame.buildRoad(new Point(6,4), new Point(6,6));
        siedlerGame.getCurrentPlayer().initializeMeeple(road);
        Settlement settlement = new Settlement(siedlerGame.getCurrentPlayer().getPlayerFaction());
        siedlerGame.getCurrentPlayer().initializeMeeple(settlement);
        assertTrue(siedlerGame.buildSettlement(new Point(6,6)));
    }

    @Test
    void buildSettlementWithNoAdjacentRoad() {
        SiedlerGame  siedlerGame = new SiedlerGame(2);
        siedlerGame.placeInitialSettlement(new Point(5,3), false);
        siedlerGame.placeInitialRoad(new Point(5,3), new Point(6,4));
        siedlerGame.getCurrentPlayer().addRescourceFromCity(Config.Resource.CLAY);
        siedlerGame.getCurrentPlayer().addRescourceFromCity(Config.Resource.WOOD);
        siedlerGame.getCurrentPlayer().addRescourceFromCity(Config.Resource.WOOL);
        siedlerGame.getCurrentPlayer().addRescourceFromCity(Config.Resource.GRAIN);
        Settlement settlement = new Settlement(siedlerGame.getCurrentPlayer().getPlayerFaction());
        siedlerGame.getCurrentPlayer().initializeMeeple(settlement);
        assertFalse(siedlerGame.buildSettlement(new Point(6,6)));
    }

    @Test
    void buildSettlementWithForeignAdjacentRoad() {
        SiedlerGame  siedlerGame = new SiedlerGame(2);
        siedlerGame.placeInitialSettlement(new Point(5,3), false);
        siedlerGame.placeInitialRoad(new Point(5,3), new Point(6,4));
        siedlerGame.getCurrentPlayer().addRescourceFromCity(Config.Resource.CLAY);
        siedlerGame.getCurrentPlayer().addRescourceFromCity(Config.Resource.WOOD);
        Road road = new Road(siedlerGame.getCurrentPlayer()
                .getPlayerFaction(),new Point(6,4), new Point(6,6));
        siedlerGame.getCurrentPlayer().initializeMeeple(road);
        siedlerGame.buildRoad(new Point(6,4), new Point(6,6));
        siedlerGame.switchToNextPlayer();
        siedlerGame.getCurrentPlayer().addRescourceFromCity(Config.Resource.CLAY);
        siedlerGame.getCurrentPlayer().addRescourceFromCity(Config.Resource.WOOD);
        siedlerGame.getCurrentPlayer().addRescourceFromCity(Config.Resource.WOOL);
        siedlerGame.getCurrentPlayer().addRescourceFromCity(Config.Resource.GRAIN);
        Settlement settlement = new Settlement(siedlerGame.getCurrentPlayer().getPlayerFaction());
        siedlerGame.getCurrentPlayer().initializeMeeple(settlement);
        assertFalse(siedlerGame.buildSettlement(new Point(6,6)));
    }

    @Test
    void buildSettlementWhenPositionOccupied() {
        SiedlerGame  siedlerGame = new SiedlerGame(2);
        siedlerGame.placeInitialSettlement(new Point(5,3), false);
        siedlerGame.placeInitialRoad(new Point(5,3), new Point(6,4));
        siedlerGame.getCurrentPlayer().addRescourceFromCity(Config.Resource.CLAY);
        siedlerGame.getCurrentPlayer().addRescourceFromCity(Config.Resource.WOOD);
        siedlerGame.getCurrentPlayer().addRescourceFromCity(Config.Resource.WOOL);
        siedlerGame.getCurrentPlayer().addRescourceFromCity(Config.Resource.GRAIN);
        Road road = new Road(siedlerGame.getCurrentPlayer()
                .getPlayerFaction(),new Point(6,4), new Point(6,6));
        siedlerGame.buildRoad(new Point(6,4), new Point(6,6));
        siedlerGame.getCurrentPlayer().initializeMeeple(road);
        Settlement settlement = new Settlement(siedlerGame.getCurrentPlayer().getPlayerFaction());
        Settlement settlement1 = new Settlement(siedlerGame.getCurrentPlayer().getPlayerFaction());
        siedlerGame.getCurrentPlayer().initializeMeeple(settlement);
        siedlerGame.getCurrentPlayer().initializeMeeple(settlement1);
        siedlerGame.buildSettlement(new Point(6,6));
        assertFalse(siedlerGame.buildSettlement(new Point(6,6)));
    }

    @Test
    void buildSettlementCheckDistanceRule() {
        SiedlerGame  siedlerGame = new SiedlerGame(2);
        siedlerGame.placeInitialSettlement(new Point(5,3), false);
        siedlerGame.placeInitialRoad(new Point(5,3), new Point(6,4));
        siedlerGame.getCurrentPlayer().addRescourceFromCity(Config.Resource.CLAY);
        siedlerGame.getCurrentPlayer().addRescourceFromCity(Config.Resource.WOOD);
        siedlerGame.getCurrentPlayer().addRescourceFromCity(Config.Resource.WOOL);
        siedlerGame.getCurrentPlayer().addRescourceFromCity(Config.Resource.GRAIN);
        Settlement settlement = new Settlement(siedlerGame.getCurrentPlayer().getPlayerFaction());
        siedlerGame.getCurrentPlayer().initializeMeeple(settlement);
        assertFalse(siedlerGame.buildSettlement(new Point(6,4)));
    }

    @Test
    void buildSettlementWhenNotEnoughResources() {
        SiedlerGame  siedlerGame = new SiedlerGame(2);
        siedlerGame.placeInitialSettlement(new Point(5,3), false);
        siedlerGame.placeInitialRoad(new Point(5,3), new Point(6,4));
        siedlerGame.getCurrentPlayer().addRescourceFromCity(Config.Resource.CLAY);
        siedlerGame.getCurrentPlayer().addRescourceFromCity(Config.Resource.WOOD);
        Road road = new Road(siedlerGame.getCurrentPlayer()
                .getPlayerFaction(),new Point(6,4), new Point(6,6));
        siedlerGame.buildRoad(new Point(6,4), new Point(6,6));
        siedlerGame.getCurrentPlayer().initializeMeeple(road);
        Settlement settlement = new Settlement(siedlerGame.getCurrentPlayer().getPlayerFaction());
        siedlerGame.getCurrentPlayer().initializeMeeple(settlement);
        assertFalse(siedlerGame.buildSettlement(new Point(6,6)));
    }


    @Test
    void buildRoadWithAdjacentRoad() {
        SiedlerGame  siedlerGame = new SiedlerGame(2);
        siedlerGame.placeInitialSettlement(new Point(5,3), false);
        siedlerGame.placeInitialRoad(new Point(5,3), new Point(6,4));
        siedlerGame.getCurrentPlayer().addRescourceFromCity(Config.Resource.CLAY);
        siedlerGame.getCurrentPlayer().addRescourceFromCity(Config.Resource.WOOD);
        siedlerGame.getCurrentPlayer().addRescourceFromCity(Config.Resource.WOOL);
        siedlerGame.getCurrentPlayer().addRescourceFromCity(Config.Resource.GRAIN);
        Road road = new Road(siedlerGame.getCurrentPlayer()
                .getPlayerFaction(),new Point(6,4), new Point(6,6));
        siedlerGame.getCurrentPlayer().initializeMeeple(road);
        assertTrue( siedlerGame.buildRoad(new Point(6,4), new Point(6,6)));
    }

    @Test
    void buildRoadWithAdjacentSettlement() {
        SiedlerGame  siedlerGame = new SiedlerGame(2);
        siedlerGame.placeInitialSettlement(new Point(5,3), false);
        siedlerGame.placeInitialRoad(new Point(5,3), new Point(6,4));
        siedlerGame.getCurrentPlayer().addRescourceFromCity(Config.Resource.CLAY);
        siedlerGame.getCurrentPlayer().addRescourceFromCity(Config.Resource.WOOD);
        siedlerGame.getCurrentPlayer().addRescourceFromCity(Config.Resource.CLAY);
        siedlerGame.getCurrentPlayer().addRescourceFromCity(Config.Resource.WOOD);
        siedlerGame.getCurrentPlayer().addRescourceFromCity(Config.Resource.WOOL);
        siedlerGame.getCurrentPlayer().addRescourceFromCity(Config.Resource.GRAIN);
        Road road = new Road(siedlerGame.getCurrentPlayer()
                .getPlayerFaction(),new Point(6,4), new Point(6,6));
        siedlerGame.buildRoad(new Point(6,4), new Point(6,6));
        siedlerGame.getCurrentPlayer().initializeMeeple(road);
        Settlement settlement = new Settlement(siedlerGame.getCurrentPlayer().getPlayerFaction());
        siedlerGame.getCurrentPlayer().initializeMeeple(settlement);
        siedlerGame.buildSettlement(new Point(6,6));
        assertTrue(siedlerGame.buildRoad(new Point(6,6), new Point(7,7)));
    }

    @Test
    void buildRoadWhenStartAndEndReversed() {
        SiedlerGame  siedlerGame = new SiedlerGame(2);
        siedlerGame.placeInitialSettlement(new Point(5,3), false);
        siedlerGame.placeInitialRoad(new Point(5,3), new Point(6,4));
        siedlerGame.getCurrentPlayer().addRescourceFromCity(Config.Resource.CLAY);
        siedlerGame.getCurrentPlayer().addRescourceFromCity(Config.Resource.WOOD);
        siedlerGame.getCurrentPlayer().addRescourceFromCity(Config.Resource.WOOL);
        siedlerGame.getCurrentPlayer().addRescourceFromCity(Config.Resource.GRAIN);
        Road road = new Road(siedlerGame.getCurrentPlayer()
                .getPlayerFaction(),new Point(6,4), new Point(6,6));
        siedlerGame.getCurrentPlayer().initializeMeeple(road);
        assertTrue( siedlerGame.buildRoad(new Point(6,6), new Point(6,4)));
    }

    @Test
    void buildRoadWhenNotEnoughResources() {
        SiedlerGame  siedlerGame = new SiedlerGame(2);
        siedlerGame.placeInitialSettlement(new Point(5,3), false);
        siedlerGame.placeInitialRoad(new Point(5,3), new Point(6,4));
        siedlerGame.getCurrentPlayer().addRescourceFromCity(Config.Resource.WOOD);
        siedlerGame.getCurrentPlayer().addRescourceFromCity(Config.Resource.WOOL);
        siedlerGame.getCurrentPlayer().addRescourceFromCity(Config.Resource.GRAIN);
        Road road = new Road(siedlerGame.getCurrentPlayer()
                .getPlayerFaction(),new Point(6,4), new Point(6,6));
        siedlerGame.getCurrentPlayer().initializeMeeple(road);
        assertFalse( siedlerGame.buildRoad(new Point(6,4), new Point(6,6)));
    }

    @Test
    void buildRoadWhenRoadIsOccupied() {
        SiedlerGame  siedlerGame = new SiedlerGame(2);
        siedlerGame.placeInitialSettlement(new Point(5,3), false);
        siedlerGame.placeInitialRoad(new Point(5,3), new Point(6,4));
        siedlerGame.getCurrentPlayer().addRescourceFromCity(Config.Resource.CLAY);
        siedlerGame.getCurrentPlayer().addRescourceFromCity(Config.Resource.WOOD);
        siedlerGame.getCurrentPlayer().addRescourceFromCity(Config.Resource.CLAY);
        siedlerGame.getCurrentPlayer().addRescourceFromCity(Config.Resource.WOOD);
        siedlerGame.getCurrentPlayer().addRescourceFromCity(Config.Resource.WOOL);
        siedlerGame.getCurrentPlayer().addRescourceFromCity(Config.Resource.GRAIN);
        Road road = new Road(siedlerGame.getCurrentPlayer()
                .getPlayerFaction(),new Point(6,4), new Point(6,6));
        siedlerGame.getCurrentPlayer().initializeMeeple(road);
        siedlerGame.buildRoad(new Point(6,4), new Point(6,6));
        assertFalse(siedlerGame.buildRoad(new Point(6,4), new Point(6,6)));
    }

    @Test
    void buildRoadAtNotValidPosition() {
        SiedlerGame  siedlerGame = new SiedlerGame(2);
        siedlerGame.placeInitialSettlement(new Point(5,3), false);
        siedlerGame.getCurrentPlayer().addRescourceFromCity(Config.Resource.CLAY);
        siedlerGame.getCurrentPlayer().addRescourceFromCity(Config.Resource.WOOD);
        siedlerGame.getCurrentPlayer().addRescourceFromCity(Config.Resource.WOOL);
        siedlerGame.getCurrentPlayer().addRescourceFromCity(Config.Resource.GRAIN);
        Road road = new Road(siedlerGame.getCurrentPlayer()
                .getPlayerFaction(),new Point(5,3), new Point(5,1));
        siedlerGame.getCurrentPlayer().initializeMeeple(road);
        assertFalse(siedlerGame.buildRoad(new Point(5,3), new Point(5,1)));
    }

    @Test
    void buildCityWhenNotEnoughResources() {
    	SiedlerGame  siedlerGame = new SiedlerGame(2);
    	siedlerGame.placeInitialSettlement(new Point(6,6), false);
    	siedlerGame.getCurrentPlayer().addRescourceFromSettlement(Config.Resource.GRAIN);
    	siedlerGame.getCurrentPlayer().addRescourceFromSettlement(Config.Resource.STONE);
    	siedlerGame.getCurrentPlayer().addRescourceFromSettlement(Config.Resource.STONE);
    	City city = new City(siedlerGame.getCurrentPlayer().getPlayerFaction());
    	siedlerGame.getCurrentPlayer().initializeMeeple(city);
    	assertFalse(siedlerGame.buildCity(new Point(6,6)));
    }
    
    @Test
    void buildCityWithNoSettlement() {
       	SiedlerGame  siedlerGame = new SiedlerGame(2);
    	siedlerGame.placeInitialSettlement(new Point(6,6), false);
    	siedlerGame.getCurrentPlayer().addRescourceFromSettlement(Config.Resource.GRAIN);
    	siedlerGame.getCurrentPlayer().addRescourceFromSettlement(Config.Resource.GRAIN);
    	siedlerGame.getCurrentPlayer().addRescourceFromSettlement(Config.Resource.STONE);
    	siedlerGame.getCurrentPlayer().addRescourceFromSettlement(Config.Resource.STONE);
    	siedlerGame.getCurrentPlayer().addRescourceFromSettlement(Config.Resource.STONE);
    	City city = new City(siedlerGame.getCurrentPlayer().getPlayerFaction());
    	siedlerGame.getCurrentPlayer().initializeMeeple(city);
    	assertFalse(siedlerGame.buildCity(new Point(9,9)));
    }
       
    @Test
    void buildCityMaxNumberOfCities() {
    	SiedlerGame  siedlerGame = new SiedlerGame(2);
    	siedlerGame.placeInitialSettlement(new Point(6,6), false);
    	siedlerGame.getCurrentPlayer().addRescourceFromSettlement(Config.Resource.GRAIN);
    	siedlerGame.getCurrentPlayer().addRescourceFromSettlement(Config.Resource.GRAIN);
    	siedlerGame.getCurrentPlayer().addRescourceFromSettlement(Config.Resource.STONE);
    	siedlerGame.getCurrentPlayer().addRescourceFromSettlement(Config.Resource.STONE);
    	siedlerGame.getCurrentPlayer().addRescourceFromSettlement(Config.Resource.STONE);
    	siedlerGame.getCurrentPlayer().initializeMeeple(new City(siedlerGame.getCurrentPlayer().getPlayerFaction()));
    	siedlerGame.getCurrentPlayer().initializeMeeple(new City(siedlerGame.getCurrentPlayer().getPlayerFaction()));
    	siedlerGame.getCurrentPlayer().initializeMeeple(new City(siedlerGame.getCurrentPlayer().getPlayerFaction()));
    	siedlerGame.getCurrentPlayer().initializeMeeple(new City(siedlerGame.getCurrentPlayer().getPlayerFaction()));
    	assertFalse(siedlerGame.buildCity(new Point(6,6)));
    }
    
    @Test
    void buildCityHasEnoughRessources() {
    	SiedlerGame  siedlerGame = new SiedlerGame(2);
    	siedlerGame.placeInitialSettlement(new Point(6,6), false);
    	siedlerGame.getCurrentPlayer().addRescourceFromSettlement(Config.Resource.GRAIN);
    	siedlerGame.getCurrentPlayer().addRescourceFromSettlement(Config.Resource.GRAIN);
    	siedlerGame.getCurrentPlayer().addRescourceFromSettlement(Config.Resource.STONE);
    	siedlerGame.getCurrentPlayer().addRescourceFromSettlement(Config.Resource.STONE);
    	siedlerGame.getCurrentPlayer().addRescourceFromSettlement(Config.Resource.STONE);
    	City city = new City(siedlerGame.getCurrentPlayer().getPlayerFaction());
    	siedlerGame.getCurrentPlayer().initializeMeeple(city);
    	assertTrue(siedlerGame.buildCity(new Point(6,6)));
    }

    @Test
    void getWinnerIfNoWinner() {
        SiedlerGame  siedlerGame = new SiedlerGame(4);
        siedlerGame.getCurrentPlayer().setWinPoints(2);
        siedlerGame.switchToNextPlayer();
        siedlerGame.getCurrentPlayer().setWinPoints(2);
        siedlerGame.switchToNextPlayer();
        siedlerGame.getCurrentPlayer().setWinPoints(2);
        siedlerGame.switchToNextPlayer();
        siedlerGame.getCurrentPlayer().setWinPoints(2);
        assertNull(siedlerGame.getWinner());
    }

    @Test
    void getWinnerIfLimit() {
        SiedlerGame  siedlerGame = new SiedlerGame(4);
        siedlerGame.getCurrentPlayer().setWinPoints(2);
        siedlerGame.switchToNextPlayer();
        siedlerGame.getCurrentPlayer().setWinPoints(2);
        siedlerGame.switchToNextPlayer();
        siedlerGame.getCurrentPlayer().setWinPoints(2);
        siedlerGame.switchToNextPlayer();
        siedlerGame.getCurrentPlayer().setWinPoints(6);
        assertNull(siedlerGame.getWinner());
    }

    @Test
    void getWinner() {
        SiedlerGame  siedlerGame = new SiedlerGame(4);
        siedlerGame.getCurrentPlayer().setWinPoints(2);
        siedlerGame.switchToNextPlayer();
        siedlerGame.getCurrentPlayer().setWinPoints(2);
        siedlerGame.switchToNextPlayer();
        siedlerGame.getCurrentPlayer().setWinPoints(2);
        siedlerGame.switchToNextPlayer();
        siedlerGame.getCurrentPlayer().setWinPoints(7);
        assertEquals(siedlerGame.getWinner(),siedlerGame.getCurrentPlayer());
    }

    @Test
    void hasLongestRoadIfConditionsApply(){
        SiedlerGame  siedlerGame = new SiedlerGame(2);
        siedlerGame.placeInitialSettlement(new Point(5,3), false);
        siedlerGame.placeInitialRoad(new Point(5,3), new Point(6,4));
        siedlerGame.getCurrentPlayer().addRescourceFromCity(Config.Resource.CLAY);
        siedlerGame.getCurrentPlayer().addRescourceFromCity(Config.Resource.WOOD);
        siedlerGame.getCurrentPlayer().addRescourceFromCity(Config.Resource.CLAY);
        siedlerGame.getCurrentPlayer().addRescourceFromCity(Config.Resource.WOOD);
        Road road = new Road(siedlerGame.getCurrentPlayer()
                .getPlayerFaction(),new Point(6,4), new Point(6,6));
        siedlerGame.buildRoad(new Point(6,4), new Point(6,6));
        siedlerGame.getCurrentPlayer().initializeMeeple(road);
        Road road1 = new Road(siedlerGame.getCurrentPlayer()
                .getPlayerFaction(),new Point(6,6), new Point(7,7));
        siedlerGame.buildRoad(new Point(6,6), new Point(7,7));
        siedlerGame.getCurrentPlayer().initializeMeeple(road1);
        Road road2 = new Road(siedlerGame.getCurrentPlayer()
                .getPlayerFaction(),new Point(7,7), new Point(7,9));
        siedlerGame.buildRoad(new Point(7,7), new Point(7,9));
        siedlerGame.getCurrentPlayer().initializeMeeple(road2);
        Road road3 = new Road(siedlerGame.getCurrentPlayer()
                .getPlayerFaction(),new Point(7,9), new Point(8,10));
        siedlerGame.buildRoad(new Point(7,9), new Point(8,10));
        siedlerGame.getCurrentPlayer().initializeMeeple(road3);
        siedlerGame.switchToNextPlayer();
        Settlement settlement = new Settlement(siedlerGame.getCurrentPlayer().getPlayerFaction());
        siedlerGame.getCurrentPlayer().initializeMeeple(settlement);
        siedlerGame.placeInitialSettlement(new Point(3,13), false);
        siedlerGame.placeInitialRoad(new Point(3,13), new Point(3,15));
        siedlerGame.getCurrentPlayer().addRescourceFromCity(Config.Resource.CLAY);
        siedlerGame.getCurrentPlayer().addRescourceFromCity(Config.Resource.WOOD);
        Road road4 = new Road(siedlerGame.getCurrentPlayer()
                .getPlayerFaction(),new Point(3,15), new Point(4,16));
        siedlerGame.buildRoad(new Point(3,15), new Point(4,16));
        siedlerGame.getCurrentPlayer().initializeMeeple(road4);
        assertEquals(siedlerGame.getPlayer().get(0), siedlerGame.getPlayerWithLongestRoad());
    }

    @Test
    void hasLongestRoadIfNull(){
        SiedlerGame  siedlerGame = new SiedlerGame(2);
        siedlerGame.placeInitialSettlement(new Point(5,3), false);
        siedlerGame.placeInitialRoad(new Point(5,3), new Point(6,4));
        siedlerGame.getCurrentPlayer().addRescourceFromCity(Config.Resource.CLAY);
        siedlerGame.getCurrentPlayer().addRescourceFromCity(Config.Resource.WOOD);
        siedlerGame.getCurrentPlayer().addRescourceFromCity(Config.Resource.CLAY);
        siedlerGame.getCurrentPlayer().addRescourceFromCity(Config.Resource.WOOD);
        Road road = new Road(siedlerGame.getCurrentPlayer()
                .getPlayerFaction(),new Point(6,4), new Point(6,6));
        siedlerGame.buildRoad(new Point(6,4), new Point(6,6));
        siedlerGame.getCurrentPlayer().initializeMeeple(road);
        Road road1 = new Road(siedlerGame.getCurrentPlayer()
                .getPlayerFaction(),new Point(6,6), new Point(7,7));
        siedlerGame.buildRoad(new Point(6,6), new Point(7,7));
        siedlerGame.getCurrentPlayer().initializeMeeple(road1);
        Road road2 = new Road(siedlerGame.getCurrentPlayer()
                .getPlayerFaction(),new Point(7,7), new Point(7,9));
        siedlerGame.buildRoad(new Point(7,7), new Point(7,9));
        siedlerGame.getCurrentPlayer().initializeMeeple(road2);
        Settlement settlement = new Settlement(siedlerGame.getCurrentPlayer().getPlayerFaction());
        siedlerGame.getCurrentPlayer().initializeMeeple(settlement);
        siedlerGame.placeInitialSettlement(new Point(3,13), false);
        siedlerGame.placeInitialRoad(new Point(3,13), new Point(3,15));
        siedlerGame.getCurrentPlayer().addRescourceFromCity(Config.Resource.CLAY);
        siedlerGame.getCurrentPlayer().addRescourceFromCity(Config.Resource.WOOD);
        Road road4 = new Road(siedlerGame.getCurrentPlayer()
                .getPlayerFaction(),new Point(3,15), new Point(4,16));
        siedlerGame.buildRoad(new Point(3,15), new Point(4,16));
        siedlerGame.getCurrentPlayer().initializeMeeple(road4);
        assertNull(siedlerGame.getPlayerWithLongestRoad());
    }

}

/**
 * This class tests the method tradeWithBankFourToOne from the class SiedlerGame
 */
class SiedlerGame_TradeWithBankFourToOneTest {
    @Test
    void tradeWithBankFourToOne_OfferIsNull() {
        Config.Resource offer = null;
        Config.Resource want = Config.Resource.WOOD;

        SiedlerGame siedlerGame = new SiedlerGame(2);
        for (int i = 0; i < 4; i++) {
            siedlerGame.getCurrentPlayer().addRescourceFromSettlement(Config.Resource.CLAY);
            siedlerGame.bank.removeBankResource(1, Config.Resource.CLAY);
        }
        assertFalse(siedlerGame.tradeWithBankFourToOne(offer, want));
        assertEquals(4, siedlerGame.getCurrentPlayer().getNumberOfTotalResources());
    }

    @Test
    void tradeWithBankFourToOne_WantIsNull() {
        Config.Resource offer = Config.Resource.CLAY;
        Config.Resource want = null;

        SiedlerGame siedlerGame = new SiedlerGame(2);
        for (int i = 0; i < 4; i++) {
            siedlerGame.getCurrentPlayer().addRescourceFromSettlement(Config.Resource.CLAY);
        }
        assertFalse(siedlerGame.tradeWithBankFourToOne(offer, want));
        assertEquals(4, siedlerGame.getCurrentPlayer().getNumberOfTotalResources());
    }

    @Test
    void tradeWithBankFourToOne_NoResources()
    {
        Config.Resource offer = Config.Resource.STONE;
        Config.Resource want = Config.Resource.WOOD;

        SiedlerGame siedlerGame = new SiedlerGame(2);
        for (int i = 0; i < 2; i++) {
            siedlerGame.getCurrentPlayer().addRescourceFromSettlement(Config.Resource.WOOL);
            siedlerGame.bank.removeBankResource(1, Config.Resource.WOOL);
        }
        assertFalse(siedlerGame.tradeWithBankFourToOne(offer, want));
        assertEquals(2, siedlerGame.getCurrentPlayer().getNumberOfTotalResources());

    }

    @Test
    void tradeWithBankFourToOne_WithEnoughResources()
    {
        Config.Resource offer = Config.Resource.CLAY;
        Config.Resource want = Config.Resource.WOOD;

        SiedlerGame siedlerGame = new SiedlerGame(2);
        for (int i = 0; i < 4; i++) {
            siedlerGame.getCurrentPlayer().addRescourceFromSettlement(Config.Resource.CLAY);
            siedlerGame.bank.removeBankResource(1, Config.Resource.CLAY);
        }
        assertTrue(siedlerGame.tradeWithBankFourToOne(offer, want));
        assertEquals(1, siedlerGame.getCurrentPlayer().getNumberOfTotalResources());
        assertEquals(Config.Resource.WOOD, siedlerGame.getCurrentPlayer().getResourceStock().get(0));
    }

    @Test
    void tradeWithBankFourToOne_WithNotEnoughResources()
    {
        Config.Resource offer = Config.Resource.CLAY;
        Config.Resource want = Config.Resource.WOOD;

        SiedlerGame siedlerGame = new SiedlerGame(2);
        for (int i = 0; i < 2; i++) {
            siedlerGame.getCurrentPlayer().addRescourceFromSettlement(Config.Resource.CLAY);
            siedlerGame.bank.removeBankResource(1, Config.Resource.CLAY);
        }
        assertFalse(siedlerGame.tradeWithBankFourToOne(offer, want));
        assertEquals(2, siedlerGame.getCurrentPlayer().getNumberOfTotalResources());
        assertEquals(Config.Resource.CLAY, siedlerGame.getCurrentPlayer().getResourceStock().get(0));
    }

    @Test
    void tradeWithBankFourToOne_WithWrongResources()
    {
        Config.Resource offer = Config.Resource.WOOL;
        Config.Resource want = Config.Resource.WOOD;

        SiedlerGame siedlerGame = new SiedlerGame(2);
        for (int i = 0; i < 5; i++) {
            siedlerGame.getCurrentPlayer().addRescourceFromSettlement(Config.Resource.CLAY);
            siedlerGame.bank.removeBankResource(1, Config.Resource.CLAY);
        }
        assertEquals(5, siedlerGame.getCurrentPlayer().getNumberOfTotalResources());
        assertFalse(siedlerGame.tradeWithBankFourToOne(offer, want));
        assertFalse(siedlerGame.getCurrentPlayer().getResourceStock().contains(want));
    }

    @Test
    void tradeWithBankFourToOne_WithBankNoResources()
    {
        Config.Resource offer = Config.Resource.CLAY;
        Config.Resource want = Config.Resource.WOOD;

        SiedlerGame siedlerGame = new SiedlerGame(2);
        for (int i = 0; i < 4; i++) {
            siedlerGame.getCurrentPlayer().addRescourceFromSettlement(Config.Resource.CLAY);
            siedlerGame.bank.removeBankResource(1, Config.Resource.CLAY);
        }
        for (int i = 0; i < 19; i++) {
            siedlerGame.getCurrentPlayer().addRescourceFromSettlement(Config.Resource.WOOD);
            siedlerGame.bank.removeBankResource(1, Config.Resource.WOOD);
        }

        assertFalse(siedlerGame.tradeWithBankFourToOne(offer, want));
        assertEquals(23, siedlerGame.getCurrentPlayer().getNumberOfTotalResources());
    }
}