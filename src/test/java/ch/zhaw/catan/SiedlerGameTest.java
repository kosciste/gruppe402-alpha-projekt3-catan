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

    /*
    @Test
    void switchToPreviousPlayer() {
        SiedlerGame siedlerGame = new SiedlerGame(4);
        siedlerGame.switchToPreviousPlayer();
        assertEquals(siedlerGame.getPlayer().get(3), siedlerGame.getCurrentPlayer());
    }
    */

    @Test
    void placeInitialSettlementValidValue() {
        SiedlerGame siedlerGame = new SiedlerGame(4);
        assertEquals(siedlerGame.placeInitialSettlement(new Point(5,3), false),true);
    }
    @Test
    void placeInitialSettlementNotValidValue() {
        SiedlerGame siedlerGame = new SiedlerGame(4);

        //Not a valid corner
        assertEquals(siedlerGame.placeInitialSettlement(new Point(4,0), false),false);

        //Corner not null
        siedlerGame.placeInitialSettlement(new Point(5,3), false);
        assertEquals(siedlerGame.placeInitialSettlement(new Point(5,3), false),false);

        //Distance rule
        assertEquals(siedlerGame.placeInitialSettlement(new Point(5,1), false),false);


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
    void throwDice() {
    }

    @Test
    void buildSettlement() {
    }

    @Test
    void buildRoad() {
    }

    @Test
    void buildCity() {
    }

    @Test
    void getWinner() {
    }

    /*
    @Test
    void hasLongestRoad(){
        Settlement settlement = new Settlement(Config.Faction.RED);
        board.setCorner(new Point(5,3),settlement.toString());
        Road road = new Road(Config.Faction.RED,new Point(5,3), new Point(6,4));
        board.setEdge(new Point(5,3), new Point(6,4),road.toString());
        Road road2 = new Road(Config.Faction.RED,new Point(6,4), new Point(6,6));
        Road road3 = new Road(Config.Faction.RED,new Point(6,6), new Point(7,7));
        Settlement settlement1 = new Settlement(Config.Faction.BLUE);
        Settlement settlement2 = new Settlement(Config.Faction.BLUE);
        board.setCorner(new Point(6,6), settlement2.toString());
        getPlayer().get(1).initializeMeeple(settlement2);
        board.setCorner(new Point(10,4),settlement1.toString());
        Road road4 = new Road(Config.Faction.BLUE,new Point(10,4), new Point(10,6));
        board.setEdge(new Point(10,4), new Point(10,6),road4.toString());
        getPlayer().get(1).initializeMeeple(road4);
        getPlayer().get(0).initializeMeeple(road);
        getPlayer().get(0).initializeMeeple(road2);
        getPlayer().get(0).initializeMeeple(road3);
        board.setEdge(new Point(6,4), new Point(6,6),road2.toString());
        board.setEdge(new Point(6,6), new Point(7,7),road3.toString());
        System.out.println(getLongestRoad(getPlayer().get(0)));
        System.out.println(getLongestRoad(getPlayer().get(1)));
    }
    */
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
        Config.Resource offer = Config.Resource.WOOL;
        Config.Resource want = Config.Resource.WOOD;

        SiedlerGame siedlerGame = new SiedlerGame(2);
        for (int i = 0; i < 4; i++) {
            siedlerGame.getCurrentPlayer().addRescourceFromSettlement(Config.Resource.CLAY);
        }
        assertFalse(siedlerGame.tradeWithBankFourToOne(offer, want));
        assertEquals(4, siedlerGame.getCurrentPlayer().getNumberOfTotalResources());

    }

    @Test
    void tradeWithBankFourToOne_WithEnoughResources()
    {
        Config.Resource offer = Config.Resource.CLAY;
        Config.Resource want = Config.Resource.WOOD;

        SiedlerGame siedlerGame = new SiedlerGame(2);
        for (int i = 0; i < 4; i++) {
            siedlerGame.getCurrentPlayer().addRescourceFromSettlement(Config.Resource.CLAY);
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
        }
        assertEquals(5, siedlerGame.getCurrentPlayer().getNumberOfTotalResources());
        assertFalse(siedlerGame.tradeWithBankFourToOne(offer, want));
        assertFalse(siedlerGame.getCurrentPlayer().getResourceStock().contains(want));
    }
}