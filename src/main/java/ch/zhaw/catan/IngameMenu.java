package ch.zhaw.catan;

import ch.zhaw.catanGameActions.IngameMenuActions;
import ch.zhaw.catanGameActions.YesAndNo;
import ch.zhaw.hexboard.Label;

import java.awt.*;
import java.util.Map;

public class IngameMenu {

    public static final int MAX_NUMBER_OF_PLAYERS = 4;

    // This Method starts a new round of the Settlers of catan.
    public static void startNewGame()
    {
        SiedlerGame siedlerGame = initializeSiedlerGame();
        SiedlerBoardTextView view = initialSiedlerBoardTextView(siedlerGame.getBoard());

        InputOutputConsole.printSiedlerBoard(view);

        // TODO: Implement initial round and role Dice
        // TODO: Implement Check wincondition
        boolean running = true;
        while (running) {
            switch (InputOutputConsole.getEnumValue(IngameMenuActions.class)) {
                case BUILD:
                    switch(InputOutputConsole.getEnumValue(Config.Structure.class)) {
                        case ROAD:
                            /*textTerminal.println("\nDeclare the beginning of your new road");
                            Point beginning = chooseCorner();
                            textTerminal.println("\nDeclare the ending of your new road");
                            Point ending = chooseCorner();
                            if (siedlerGame.buildRoad(beginning, ending)) {
                                printSiedlerBoard(view);
                            } else {
                                printFailureMessage();
                            }*/
                            break;
                        case SETTLEMENT:
                            /*textTerminal.println("\nDeclare the location of your new settlement");
                            Point location = chooseCorner();
                            if (siedlerGame.buildSettlement(location)) {
                                printSiedlerBoard(view);
                            } else {
                                printFailureMessage();
                            }*/
                            break;
                        case CITY:
                            // TODO
                    }
                    break;
                case TRADE:
                    // TODO: Implement Trade with Bank
                    break;
                case END_TURN:
                    // TODO: Implement switch to next player
                    // TODO: Role Dice
                    break;
                case END_THE_GAME:
                    InputOutputConsole.printText(Output.getCantSafeWarning());
                    running = shouldStillRun();
                    break;

                default:
                    InputOutputConsole.printText(Output.getErrorMessage());
                    break;
            }
        }
    }

    private static SiedlerGame initializeSiedlerGame()
    {
        return new SiedlerGame(InputOutputConsole.setNumberOfWinpointsToWin(), InputOutputConsole.setNumberOfPlayers());
    }

    private static boolean shouldStillRun()
    {
        switch (InputOutputConsole.getEnumValue(YesAndNo.class)) {
            case YES:
                InputOutputConsole.printText(Output.getTextWelcome());
                return false;
            case NO:
                return true;

            default:
                InputOutputConsole.printText(Output.getErrorMessage());
                return true;
        }
    }

    private static SiedlerBoardTextView initialSiedlerBoardTextView(SiedlerBoard board)
    {
        SiedlerBoardTextView view = new SiedlerBoardTextView(board);

        for (Map.Entry<Point, Label> e : board.getLowerFieldLabel().entrySet()) {
            view.setLowerFieldLabel(e.getKey(), e.getValue());
        }
        return view;
    }
}