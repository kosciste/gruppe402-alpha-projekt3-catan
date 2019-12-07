package ch.zhaw.catan;

import ch.zhaw.catanGameActions.BuildingMenuActions;
import ch.zhaw.catan.Config.Structure;
import ch.zhaw.catan.Config.Resource;

import java.awt.Point;
import java.util.Map;

/**
 * This class displays the building-menu from The Settlers of Catan.
 * Here you will be able to choose from 6 Options. Each meeple built, the menu will
 * calculate if the current player has won the game.
 * <p>
 * 1. Go Back: return to the ingame-menu
 * 2. Show my resources: shows the resources of the current player.
 * 3. Show needed resources: shows the needed resources for each meeple to build.
 * 4. Road: Builds a road on your chosen coordinates.
 * 5. Settlement: Builds a settlement on your chosen coordinates.
 * 6. City: Builds a city on your chosen coordinates.
 * </p>
 * @author Nikola Jovanovic
 */
public class BuildingMenu {

    private SiedlerGame siedlerGame;
    private SiedlerBoardTextView view;
    protected static Player winner;

    /**
     * Initializes the building menu and all of its instance variables.
     */
    public BuildingMenu(SiedlerGame siedlerGame, SiedlerBoardTextView view) {
        this.siedlerGame = siedlerGame;
        this.view = view;
    }

	/**
	 * Starts the menu for building actions. The menu is running until the player
	 * chooses the option to go back or until the player builds a game-winning
	 * settlement or city.
	 * 
	 * @return false to stop the game if there is a winner
	 */
    public boolean startBuildingMenu() {
    	boolean gameIsRunning = true;
        boolean menuIsRunning = true;
        while(menuIsRunning) {
            switch (InputOutputConsole.getEnumValue(BuildingMenuActions.class)) {
                case GO_BACK:
                    menuIsRunning = false;
                    break;
                case SHOW_RESOURCES:
                    InputOutputConsole.printText(IngameMenu.showPlayerResources());
                    break;
                case SHOW_NEEDED_RESOURCES:
                    showNeededResources();
                    break;
                case ROAD:
                    startRoadBuilding();
                    break;
                case SETTLEMENT:
                    startSettlementBuilding();
                    if (isWinnerAvailable()) {
                    	winner = siedlerGame.getWinner();
                    	menuIsRunning = false;
                    	gameIsRunning = false;
                    }
                    break;
                case CITY:
                    buildCity();
                    if (isWinnerAvailable()) {
                    	winner = siedlerGame.getWinner();
                    	menuIsRunning = false;
                    	gameIsRunning = false;
                    }
                    break;
            }
        }
        return gameIsRunning;
    }

	/**
	 * Starts the process of building a new road. The player chooses a beginning and
	 * an ending for the new road. If the road has been built successfully, the
	 * updated board is printed to the console. Otherwise a failure message is
	 * displayed.
	 */
	private void startRoadBuilding() {
		InputOutputConsole.printText(Output.getRoadBuildingMessage("beginning", "new"));
		Point beginning = IngameMenu.chooseCorner();
		InputOutputConsole.printText(Output.getRoadBuildingMessage("ending", "new"));
		Point ending = IngameMenu.chooseCorner();
		if (siedlerGame.buildRoad(beginning, ending)) {
			InputOutputConsole.printSiedlerBoard(view);
		} else {
			InputOutputConsole.printText(Output.getFailureMessage());
		}
	}

	/**
	 * Starts the process of building a new settlement. The player chooses a
	 * location for the new settlement. If the settlement has been built
	 * successfully, the updated board is printed to the console. Otherwise a
	 * failure message is displayed.
	 */
	private void startSettlementBuilding() {
		InputOutputConsole.printText(Output.getSettlementBuildingMessage("new"));
		Point location = IngameMenu.chooseCorner();
		if (siedlerGame.buildSettlement(location)) {
			InputOutputConsole.printSiedlerBoard(view);
		} else {
			InputOutputConsole.printText(Output.getFailureMessage());
		}
	}

	/**
	 * Starts the process of building a new city. The player chooses a location for
	 * the new city. If the city has been built successfully, the updated board is
	 * printed to the console. Otherwise a failure message is displayed.
	 */
	private void buildCity() {
		InputOutputConsole.printText(Output.getCityBuildingMessage());
		Point location = IngameMenu.chooseCorner();
		if (siedlerGame.buildCity(location)) {
			InputOutputConsole.printSiedlerBoard(view);
		} else {
			InputOutputConsole.printText(Output.getFailureMessage());
		}
	}

    private void showNeededResources()
    {
        InputOutputConsole.printText("");
        for(Structure structure : Structure.values()) {
            InputOutputConsole.printText(getStringOfCostsforMeeple(structure));
        }
    }

    private String getStringOfCostsforMeeple(Structure structure)
    {
        if(structure == null){ return "";}

        Map<Resource, Long> structureCostsAsMap = structure.getCostsAsMap();
        String structureResourcesString = structure +" needs: ";
        for(Map.Entry<Resource, Long> resourceEntry : structureCostsAsMap.entrySet())
            structureResourcesString += resourceEntry.getKey() + ": " + resourceEntry.getValue() + ", ";
        return structureResourcesString;
    }
    
	/**
	 * Checks if there is a winner.
	 * 
	 * @return true, if there is a winner
	 */
	private boolean isWinnerAvailable() {
		return (siedlerGame.getWinner() != null) ? true : false;
	}
}
