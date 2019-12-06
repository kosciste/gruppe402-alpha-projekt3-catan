package ch.zhaw.catan;

import ch.zhaw.catanGameActions.BuildingMenuActions;
import ch.zhaw.catan.Config.Structure;
import ch.zhaw.catan.Config.Resource;

import java.awt.*;
import java.util.Map;

/**
 * This class displays the building-menu from The Settlers of Catan.
 * Here you will be able to choose From 3 Options.
 * <p>
 * 1. Road: Builds a road on your chosen coordinates.
 * 2. Settlement: Builds a settlement on your chosen coordinates.
 * 3. City: Builds a city on your chosen coordinates.
 * </p>
 * @author Nikola Jovanovic
 */
public class BuildingMenu {

    private SiedlerGame siedlerGame;
    private SiedlerBoardTextView view;

    /**
     * Starts the building-menu. From Here a player can build meeples.
     */
    public BuildingMenu(SiedlerGame siedlerGame, SiedlerBoardTextView view) {
        this.siedlerGame = siedlerGame;
        this.view = view;
        startBuildingMenu();
    }

    private void startBuildingMenu() {
        boolean running = true;
        while(running) {
            switch (InputOutputConsole.getEnumValue(BuildingMenuActions.class)) {
                case GO_BACK:
                    running = false;
                    break;
                case SHOW_RESOURCES:
                    InputOutputConsole.printText(IngameMenu.showPlayerResources());
                    break;
                case SHOW_NEEDED_RESOURCES:
                    showNeededResources();
                    break;
                case ROAD:
                    buildRoad();
                    break;
                case SETTLEMENT:
                    buildSettlement();
                    break;
                case CITY:
                    buildCity();
                    break;
            }
        }
    }

    private void buildRoad() {
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

    private void buildSettlement() {
        InputOutputConsole.printText(Output.getSettlementBuildingMessage("new"));
        Point locationForSettlement = IngameMenu.chooseCorner();
        if (siedlerGame.buildSettlement(locationForSettlement)) {
            InputOutputConsole.printSiedlerBoard(view);
        } else {
            InputOutputConsole.printText(Output.getFailureMessage());
        }
    }

    private void buildCity() {
        InputOutputConsole.printText(Output.getCityBuildingMessage());
        Point locationForCity = IngameMenu.chooseCorner();
        if (siedlerGame.buildCity(locationForCity)) {
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
}
