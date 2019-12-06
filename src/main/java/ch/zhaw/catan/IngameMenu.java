package ch.zhaw.catan;

import ch.zhaw.catanGameActions.IngameMenuActions;
import ch.zhaw.catanGameActions.YesAndNo;
import ch.zhaw.hexboard.Label;

import java.awt.Point;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.HashMap;

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
  					new BuildingMenu(siedlerGame, view);
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
	public static Point chooseCorner() {
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

	public static String showPlayerResources()
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
	 * Prints all payouts of resources per player to the console. A print shows the
	 * players name, the received resource(s) and the amount of the resource(s). If
	 * a player didn't receive any resources, there is no print out. Example:
	 * 'Player RED received 2 WD, 1 ST'
	 * 
	 * @param payouts a Map with all players as key and their respective List of
	 *                resources which they got newly
	 */
	private static void showPayoutOfResources(Map<Player, List<Config.Resource>> payouts) {
		for (Map.Entry<Player, List<Config.Resource>> payout : payouts.entrySet()) {
			Player player = payout.getKey();
			List<Config.Resource> resources = payout.getValue();

			Map<Config.Resource, Integer> countedResources = getMapOfCountedResources(resources);
			if (!countedResources.isEmpty()) {
				String playerName = player.getPlayerFaction().name();
				String amountsAndResources = "";
				for (Map.Entry<Config.Resource, Integer> countedResource : countedResources.entrySet()) {
					amountsAndResources += ", " + countedResource.getValue() + " "
							+ countedResource.getKey().toString();
				}
				InputOutputConsole.printText(
						Output.getPayoutOfResourcesMessage(playerName, amountsAndResources.replaceFirst(",", "")));
			}
		}
	}
	
	/**
	 * Counts the amount of each resource out of a List of resources. Creates and
	 * returns a Map, in which a key is the respective resource and the value is its
	 * amount.
	 * 
	 * @param resources a List of resources, which possibly contains duplicates
	 * @return a Map with one entry per resource with the resource itself as key and
	 *         the amount as value, if the List of resources contains no elements or
	 *         null then an empty Map is returned
	 */
	private static Map<Config.Resource, Integer> getMapOfCountedResources(List<Config.Resource> resources) {
		Map<Config.Resource, Integer> countedResources = new HashMap<>();
		if (!resources.contains(null) && !resources.isEmpty()) {
			for (Config.Resource resource : resources) {
				if (countedResources.containsKey(resource)) {
					int amount = countedResources.get(resource);
					countedResources.put(resource, amount + 1);
				} else {
					countedResources.put(resource, 1);
				}
			}
		}
		return countedResources;
	}
}
