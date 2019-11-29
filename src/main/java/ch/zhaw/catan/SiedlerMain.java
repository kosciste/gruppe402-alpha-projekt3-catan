package ch.zhaw.catan;

import ch.zhaw.hexboard.Label;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;

import java.awt.*;
import java.util.Map;

import ch.zhaw.catanGameActions.*;


public class SiedlerMain {

    private TextIO textIO;
    private TextTerminal<?> textTerminal;

    private SiedlerMain()
    {
        initializeTextTerminal();
    }


    private void initializeTextTerminal()
    {
        textIO = TextIoFactory.getTextIO();
        textTerminal = textIO.getTextTerminal();
        textTerminal.getProperties().setPaneDimension(1280,720);
    }

    public static void main(String[] args)
    {
        new SiedlerMain().startMainMenu();
    }

    // This method starts and runs the main menu.
    private void startMainMenu()
    {
        printTextWelcome();
        MainMenu();
        textTerminal.dispose();
    }

    // This method implements the main menu.
    private void MainMenu()
    {
        boolean running = true;
        while(running) {
            switch (getEnumValue(textIO, MainMenuActions.class)) {
                case PLAY:
                    startNewRound();
                    break;
                case ABOUT:
                    printTextAbout();
                    break;
                case QUIT:
                    running = false;
                    break;

                default:
                    printErrorMessage();
                    break;
            }
        }
    }

    // This Method starts a new round of the Settlers of catan.
    private void startNewRound()
    {
        SiedlerGame siedlerGame = initializeSiedlerGame();
        SiedlerBoardTextView view = initialSiedlerBoardTextview(siedlerGame.getBoard());

        printSiedlerBoard(view);

        // TODO: Implement initial round and role Dice
        // TODO: Implement Check wincondition
        boolean running = true;
        while (running) {
            switch (getEnumValue(textIO, IngameMenuActions.class)) {
                case BUILD:
                    // TODO: Implement Build Structure
                    /*
                    case ROAD:
                        siedlergame.buildRoad();
                        printSiedlerBoard(view);
                     */
                    break;
                case TRADE:
                    // TODO: Implement Trade with Bank
                    break;
                case END_TURN:
                    // TODO: Implement switch to next player
                    // TODO: Role Dice
                    break;
                case END_THE_GAME:
                    printCantSafeWarning();
                    running = shouldStillRun();
                    break;

                default:
                    printErrorMessage();
                    break;
            }
        }
    }

    // This method returns true if the round still should run otherwise it will return false.
    private boolean shouldStillRun()
    {
        switch (getEnumValue(textIO, YesAndNo.class)) {
            case YES:
                printTextWelcome();
                return false;
            case NO:
                return true;

            default:
                printErrorMessage();
                return true;
        }
    }

    private SiedlerGame initializeSiedlerGame()
    {
        textTerminal.println();
        return new SiedlerGame(setNumberOfWinpointsToWin(), setNumberOfPlayers());
    }

    private int setNumberOfWinpointsToWin()
    {
        return textIO.newIntInputReader()
                .withMinVal(3)
                .withMaxVal(10)
                .read("Declare number of winpoints: ");
    }

    private int setNumberOfPlayers()
    {
        return textIO.newIntInputReader()
                .withMinVal(2)
                .withMaxVal(4)
                .read("Enter the number of Settlers: ");
    }

    private SiedlerBoardTextView initialSiedlerBoardTextview(SiedlerBoard board)
    {
        SiedlerBoardTextView view = new SiedlerBoardTextView(board);

        for (Map.Entry<Point, Label> e : board.getLowerFieldLabel().entrySet()) {
            view.setLowerFieldLabel(e.getKey(), e.getValue());
        }
        return view;
    }

    private static <T extends Enum<T>> T getEnumValue(TextIO textIO, Class<T> commands)
    {
        return textIO.newEnumInputReader(commands).read("----");
    }

    private void printTextWelcome()
    {
        textTerminal.println(
              "\n"
            + "   _____ _____ _____     _____ _____ _____ _____ __    _____ _____ _____  \n"
            + "  |_   _|  |  |   __|   |   __|   __|_   _|_   _|  |  |   __| __  |   __| \n"
            + "    | | |     |   __|   |__   |   __| | |   | | |  |__|   __|    -|__   | \n"
            + "    |_| |__|__|_____|   |_____|_____| |_|   |_| |_____|_____|__|__|_____| \n"
            + "\n"
            + "               _____ _____     _____ _____ _____ _____ _____  \n"
            + "              |     |   __|   |     |  _  |_   _|  _  |   | | \n"
            + "              |  |  |   __|   |   --|     | | | |     | | | | \n"
            + "              |_____|__|      |_____|__|__| |_| |__|__|_|___| \n"
            + "\n\n"
            + "                                   ****\n\n"
            + "                          Hello fellow Settlers!\n"
            + "      You have successfully startet the game THE SETTLERS OF CATAN.\n"
            + "                         May the best of you win.\n\n"
            + "                                   ****\n"
        );
    }

    private void printTextAbout()
    {
        textTerminal.println(
              "\n****\n\n"

            + "You're playing our third Project called THE SETTLERS OF CATAN\n\n"

            + "Autors: Blattmann Peter   -- blattpet\n"
            + "        Jovanovic Nikola  -- jovanni1\n"
            + "        Koscica Stefan    -- kosciste\n"
            + "        Sileno Ennio      -- silenenn\n\n"

            + "For more information please visit our repository on Github :)\n\n"

            + "****\n"
        );
    }

    private void printCantSafeWarning()
    {
        textTerminal.println("You can't safe the progress you've made so far...\n" +
                             "Do You really want to end the game?");

    }

    private void printErrorMessage()
    {
        textTerminal.println("Something unexpected went wrong :(");
    }

    private void printSiedlerBoard(SiedlerBoardTextView view)
    {
        textTerminal.println(view.toString());
    }
}