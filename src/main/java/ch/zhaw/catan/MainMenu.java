package ch.zhaw.catan;

import ch.zhaw.catanGameActions.MainMenuActions;

public class MainMenu {

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
            switch (InputOutputConsole.getEnumValue( MainMenuActions.class)) {
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
