package ch.zhaw.catan;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class SiedlerGameTest {

    @BeforeEach
    void setUp() {

    }

    @Test
    void switchToNextPlayer() {
        SiedlerGame siedlerGame = new SiedlerGame(4,4);
        siedlerGame.switchToNextPlayer();
        assertEquals(siedlerGame.getCurrentPlayer(),siedlerGame.getPlayer().get(1));
    }

    @Test
    void switchToPreviousPlayer() {
        SiedlerGame siedlerGame = new SiedlerGame(4,4);
        siedlerGame.switchToNextPlayer();
        assertEquals(siedlerGame.getCurrentPlayer(),siedlerGame.getPlayer().get(4));
    }

    @Test
    void placeInitialSettlementValidValue() {
        SiedlerGame siedlerGame = new SiedlerGame(4,4);
        assertEquals(siedlerGame.placeInitialSettlement(new Point(5,3), false),true);
    }
    @Test
    void placeInitialSettlementNotValidValue() {
        SiedlerGame siedlerGame = new SiedlerGame(4,4);

        //Not a valid corner
        assertEquals(siedlerGame.placeInitialSettlement(new Point(4,0), false),false);

        //Corner not null
        siedlerGame.placeInitialSettlement(new Point(5,3), false);
        assertEquals(siedlerGame.placeInitialSettlement(new Point(5,3), false),false);

        //Distance rule
        assertEquals(siedlerGame.placeInitialSettlement(new Point(5,1), false),false);


    }

    @Test
    void placeInitialRoad() {
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
}

/**
 * This class tests the method tradeWithBankFourToOne from the class SiedlerGame
 */
class SiedlerGame_TradeWithBankFourToOneTest {
    @Test
    void tradeWithBankFourToOne_OfferIsNull() {
        Config.Resource offer = null;
        Config.Resource want = Config.Resource.WOOD;

        SiedlerGame siedlerGame = new SiedlerGame(1, 2);
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

        SiedlerGame siedlerGame = new SiedlerGame(1, 2);
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

        SiedlerGame siedlerGame = new SiedlerGame(1, 2);
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

        SiedlerGame siedlerGame = new SiedlerGame(1, 2);
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

        SiedlerGame siedlerGame = new SiedlerGame(1, 2);
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

        SiedlerGame siedlerGame = new SiedlerGame(1, 2);
        for (int i = 0; i < 5; i++) {
            siedlerGame.getCurrentPlayer().addRescourceFromSettlement(Config.Resource.CLAY);
        }
        assertEquals(5, siedlerGame.getCurrentPlayer().getNumberOfTotalResources());
        assertFalse(siedlerGame.tradeWithBankFourToOne(offer, want));
        assertFalse(siedlerGame.getCurrentPlayer().getResourceStock().contains(want));
    }
}