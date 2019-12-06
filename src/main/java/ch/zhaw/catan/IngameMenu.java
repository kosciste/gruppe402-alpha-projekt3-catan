package ch.zhaw.catan;

import ch.zhaw.catanGameActions.IngameMenuActions;
import ch.zhaw.catanGameActions.YesAndNo;
import ch.zhaw.hexboard.Label;

import java.awt.Point;
import java.util.List;
import java.util.Map;
import java.util.Random;

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
    private static final int MIN_DIE_VALUE = 1;
    private static final int MAX_DIE_VALUE = 6;
    private static SiedlerGame siedlerGame;
    private static SiedlerBoardTextView view;
    private static Random firstDie = new Random();
    private static Random secondDie = new Random(); 

    /**
     * This method starts the ingame menu and initialises a new round of the Settlers of Catan
     */
    public static void startNewGame()
    {
        siedlerGame = initializeSiedlerGame();
        view = initialSiedlerBoardTextView(siedlerGame.getBoard());
        InputOutputConsole.printSiedlerBoard(view);
        startInitialBuilding();
        
        boolean gameIsRunning = true;
        boolean sameTurnIsRunning = true;
        int valueOfDice;
        while (gameIsRunning) {
        	showTurnOfCurrentPlayer();
        	valueOfDice = getValueOfDice();
        	InputOutputConsole.printText(Output.getValueOfDiceMessage(valueOfDice));
        	showPayoutOfResources(siedlerGame.throwDice(valueOfDice));
        	sameTurnIsRunning = true;
        	while (gameIsRunning && sameTurnIsRunning) {
        		switch (InputOutputConsole.getEnumValue(IngameMenuActions.class)) {
    			case SHOW_BOARD:
    				InputOutputConsole.printSiedlerBoard(view);
    				break;
    			case SHOW_RESOURCES:
    				InputOutputConsole.printText(showPlayerResources());
    				break;
        		case BUILD:
        			switch (InputOutputConsole.getEnumValue(Config.Structure.class)) {
        			case ROAD:
        				InputOutputConsole.printText(Output.getRoadBuildingMessage("beginning", "new"));
        				Point beginning = chooseCorner();
        				InputOutputConsole.printText(Output.getRoadBuildingMessage("ending", "new"));
        				Point ending = chooseCorner();
        				if (siedlerGame.buildRoad(beginning, ending)) {
        					InputOutputConsole.printSiedlerBoard(view);
        				} else {
        					InputOutputConsole.printText(Output.getFailureMessage());
        				}
        				break;
        			case SETTLEMENT:
        				InputOutputConsole.printText(Output.getSettlementBuildingMessage("new"));
        				Point locationForSettlement = chooseCorner();
        				if (siedlerGame.buildSettlement(locationForSettlement)) {
        					InputOutputConsole.printSiedlerBoard(view);
        				} else {
        					InputOutputConsole.printText(Output.getFailureMessage());
        				}
        				break;
        			case CITY:
        				InputOutputConsole.printText(Output.getCityBuildingMessage());
        				Point locationForCity = chooseCorner();
        				if (siedlerGame.buildCity(locationForCity)) {
        					InputOutputConsole.printSiedlerBoard(view);
        				} else {
        					InputOutputConsole.printText(Output.getFailureMessage());
        				}
        				break;
        			}
        			break;
        		case TRADE:
    				actionTrade();
        			break;
        		case END_TURN:
        			sameTurnIsRunning = false;
        			siedlerGame.switchToNextPlayer();
        			break;
        		case END_THE_GAME:
        			InputOutputConsole.printText(Output.getCantSafeWarning());
        			gameIsRunning = shouldStillRun();
        			break;
        		default:
        			InputOutputConsole.printText(Output.getErrorMessage());
        			break;
        		}
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
	
	/**
	 * Starts the initial building phase, which consists of two rounds. In the first
	 * round every player builds a settlement and a road. In the second round they
	 * do the same but in reverse order.
	 */
	private static void startInitialBuilding() {
		boolean payoutResources = true;
		int numberOfPlayers = siedlerGame.getPlayer().size();
		for (int i = 0; i < numberOfPlayers; i++) {
			showTurnOfCurrentPlayer();
			buildInitialSettlement("first", !payoutResources);
			buildInitialRoad("first");
			siedlerGame.switchToNextPlayer();
		}
		for (int i = 0; i < numberOfPlayers; i++) {
			siedlerGame.switchToPreviousPlayer();
			showTurnOfCurrentPlayer();
			buildInitialSettlement("second", payoutResources);
			buildInitialRoad("second");
		}
	}
	
	/**
	 * Shows currently whose players turn it is and prints that information to the
	 * console. The name of the player is the name of the enum constant from
	 * {@link Config.Faction}. Examples: The name of the first player is 'RED'. The
	 * name of the second player 'BLUE'.
	 */
	private static void showTurnOfCurrentPlayer() {
		String currentPlayerName = siedlerGame.getCurrentPlayer().getPlayerFaction().name();
		InputOutputConsole.printText(Output.getTurnOfCurrentPlayerMessage(currentPlayerName));
	}

	/**
	 * Builds an initial settlement. The parameter specifies whether it is the
	 * 'first' or 'second' initial settlement and affects only a message.
	 * 
	 * @param firstOrSecond the declaration, if it is the first or second settlement
	 * @param payoutResources true, if the resources should be paid out
	 */
	private static void buildInitialSettlement(String firstOrSecond, boolean payoutResources) {
		boolean settlementIsBuilt = false;
		while (!settlementIsBuilt) {
			InputOutputConsole.printText(Output.getSettlementBuildingMessage(firstOrSecond));
			Point location = chooseCorner();
			if (siedlerGame.placeInitialSettlement(location, payoutResources)) {
				InputOutputConsole.printSiedlerBoard(view);
				settlementIsBuilt = true;
			} else {
				InputOutputConsole.printText(Output.getFailureMessage());
			}
		}
	}
	
	/**
	 * Builds an initial road. The parameter specifies whether it is the
	 * 'first' or 'second' initial road and affects only a message.
	 * 
	 * @param firstOrSecond the declaration, if it is the first or second road
	 */
	private static void buildInitialRoad(String firstOrSecond) {
		boolean roadIsBuilt = false;
		while (!roadIsBuilt) {
			InputOutputConsole.printText(Output.getRoadBuildingMessage("beginning", firstOrSecond));
            Point beginning = chooseCorner();
            InputOutputConsole.printText(Output.getRoadBuildingMessage("ending", firstOrSecond));
            Point ending = chooseCorner();
            if (siedlerGame.placeInitialRoad(beginning, ending)) {
                InputOutputConsole.printSiedlerBoard(view);
                roadIsBuilt = true;
            } else {
            	InputOutputConsole.printText(Output.getFailureMessage());
            }
		}
	}

	private static String showPlayerResources()
	{
		return siedlerGame.getCurrentPlayer().getFormatResources();
	}

	private static void actionTrade()
	{
		InputOutputConsole.printText(showPlayerResources());
		if (siedlerGame.getCurrentPlayer().getNumberOfTotalResources() > 0) {
			InputOutputConsole.printText("Which material do you offer?");
			Config.Resource offer = InputOutputConsole.chooseResource();
			InputOutputConsole.printText("Which material do you want?");
			Config.Resource want = InputOutputConsole.chooseResource();
			siedlerGame.tradeWithBankFourToOne(offer, want);
		}
	}

	/**
	 * Simulates a cast of two dice. Each die delivers a value between 1 and 6
	 * (inclusive). The sum of these values, which is between 2 and 12 is returned.
	 * 
	 * @return a sum of two dice values
	 */
	private static int getValueOfDice() {
		int firstDieValue = firstDie.nextInt(MAX_DIE_VALUE) + MIN_DIE_VALUE;
		int secondDieValue = secondDie.nextInt(MAX_DIE_VALUE) + MIN_DIE_VALUE;
		return firstDieValue + secondDieValue;
	}
	
	/**
	 * TODO
	 * @param payout
	 */
	private static void showPayoutOfResources(Map<Player, List<Config.Resource>> payout) {
		for (Map.Entry<Player, List<Config.Resource>> entry : payout.entrySet()) {
			Player player = entry.getKey();
			List<Config.Resource> resources = entry.getValue();
			if (!resources.contains(null) && !resources.isEmpty()) {
				InputOutputConsole.printText(player.getPlayerFaction().name());
				for (Config.Resource resource: resources) {
					InputOutputConsole.printText(resource.toString());
				}
			}
		}
	}
}
