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
    void tradeWithBankFourToOne() {
    }

    @Test
    void getWinner() {
    }
}