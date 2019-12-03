package ch.zhaw.catan;

import ch.zhaw.catan.Config.Faction;
import ch.zhaw.catanGameActions.IngameMenuActions;
import ch.zhaw.catanGameActions.YesAndNo;
import ch.zhaw.hexboard.Label;

import java.awt.*;
import java.util.Map;

/**
 * This class Displays the ingame menu of the Settlers of Catan. It also
 * initialises a new round of the Settlers of Catan.
 * Here you will be able to choose from 4 options.
 * <p>
 * 1. Build: Here you can build a structure.
 * 2. Trade: Trade with the bank.
 * 3. End my turn: ends the turn and switches to the next player.
 * 4. End the game: Ends the game and returns to the main menu
 * </p>
 *
 * @author Sileno Ennio
 */
public class IngameMenu {

    public static final int MAX_NUMBER_OF_PLAYERS = 4;
    private static SiedlerGame siedlerGame;
    private static SiedlerBoardTextView view;

    /**
     * This method starts the main menu and initialises a new round of the Settlers of Catan
     */
    public static void startNewGame()
    {
        siedlerGame = initializeSiedlerGame();
        view = initialSiedlerBoardTextView(siedlerGame.getBoard());

        InputOutputConsole.printSiedlerBoard(view);
        
        startFoundationPhase();

        // TODO: Implement initial round and role Dice
        // TODO: Implement Check wincondition
        boolean running = true;
        while (running) {
            switch (InputOutputConsole.getEnumValue(IngameMenuActions.class)) {
                case BUILD:
                    switch(InputOutputConsole.getEnumValue(Config.Structure.class)) {
                        case ROAD:
                            InputOutputConsole.printText("\nDeclare the beginning of your new road");
                            Point beginning = chooseCorner();
                            InputOutputConsole.printText("\nDeclare the ending of your new road");
                            Point ending = chooseCorner();
                            if (siedlerGame.buildRoad(beginning, ending)) {
                                InputOutputConsole.printSiedlerBoard(view);
                            } else {
                            	InputOutputConsole.printText(Output.getFailureMessage());
                            }
                            break;
                        case SETTLEMENT:
                        	InputOutputConsole.printText("\nDeclare the location of your new settlement");
                            Point location = chooseCorner();
                            if (siedlerGame.buildSettlement(location)) {
                            	InputOutputConsole.printSiedlerBoard(view);
                            } else {
                            	InputOutputConsole.printText(Output.getFailureMessage());
                            }
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
    
    /**
	 * Reads a specified x- and y-coordinate from the console until the point refers
	 * to a corner from the board. Only then the point is returned.
	 * 
	 * @return a point which refers to a corner
	 */
	private static Point chooseCorner() {
		Point point = InputOutputConsole.choosePoint();
		while (!siedlerGame.getBoard().hasCorner(point)) {
			InputOutputConsole.printText(Output.getNotValidCornerMessage());
			point = InputOutputConsole.choosePoint();
		}
		return point;
	}
	
	private static void startFoundationPhase() {
		for (int i = 0; i < siedlerGame.getPlayer().size(); i++) {
			InputOutputConsole.printText("\nIt's player " + siedlerGame.getCurrentPlayer().getPlayerFaction() + "s turn");
			
			boolean settlementIsBuilt = false;
			while (!settlementIsBuilt) {
				InputOutputConsole.printText("\nDeclare the location of your first settlement");
				Point location = chooseCorner();
				if (siedlerGame.placeInitialSettlement(location)) {
					InputOutputConsole.printSiedlerBoard(view);
					settlementIsBuilt = true;
				} else {
					InputOutputConsole.printText(Output.getFailureMessage());
				}
			}
			
			boolean roadIsBuilt = false;
			while (!roadIsBuilt) {
				InputOutputConsole.printText("\nDeclare the beginning of your first road");
                Point beginning = chooseCorner();
                InputOutputConsole.printText("\nDeclare the ending of your first road");
                Point ending = chooseCorner();
                if (siedlerGame.placeInitialRoad(beginning, ending)) {
                    InputOutputConsole.printSiedlerBoard(view);
                    roadIsBuilt = true;
                } else {
                	InputOutputConsole.printText(Output.getFailureMessage());
                }
			}
			
			siedlerGame.switchToNextPlayer();
			i++;
		}
	}
}
