package ch.zhaw.catan;

import ch.zhaw.catanGameActions.MainMenuActions;

/**
 * This class Displays the Main Menu of the Settlers of Catan.
 * Here you will be able to choose from 3 options.
 * <p>
 * 1. Play: Here you will start a new Game
 * 2. About: A text appears that displays the authors of this application
 * 3. Quit: Closes the game.
 * </p>
 *
 * @author Sileno Ennio
 */
public class MainMenu {

    /**
     * Starts the main menu and closes the application if option 3 Quit was chosen.
     */
    public static void startMainMenu()
    {
        InputOutputConsole.printText(Output.getTextWelcome());
        mainMenu();
        InputOutputConsole.closeInOutput();
    }

    private static void mainMenu()
    {
        boolean running = true;
        while(running) {
            switch (InputOutputConsole.getEnumValue(MainMenuActions.class)) {
                case PLAY:
                    IngameMenu.startNewGame();
                    break;
                case ABOUT:
                    InputOutputConsole.printText(Output.getTextAbout());
                    break;
                case QUIT:
                    running = false;
                    break;

                default:
                    InputOutputConsole.printText(Output.getErrorMessage());
                    break;
            }
        }
    }
}