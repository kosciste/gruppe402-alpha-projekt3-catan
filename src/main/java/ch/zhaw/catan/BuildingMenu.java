package ch.zhaw.catan;

import java.awt.*;

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
        switch (InputOutputConsole.getEnumValue(Config.Structure.class)) {
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
}
