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
        new SiedlerMain().runMainMenu();
    }

    private void runMainMenu()
    {
        boolean running = true;

        textTerminal.print(getTextWelcome());
        while(running) {
            switch (getEnumValue(textIO, MainMenuActions.class)) {
                case PLAY:
                    startNewRound();
                    break;
                case ABOUT:
                    textTerminal.println(getTextAbout());
                    break;
                case QUIT:
                    running = false;
                    break;
                default:
                    textTerminal.println("Something went wrong :(");
                    break;
            }
        }
        textTerminal.dispose();
    }

    private void startNewRound()
    {
        //SiedlerGame siedlerGame = initializeSiedlerGame();
        SiedlerBoard board = new SiedlerBoard();

        SiedlerBoardTextView view = new SiedlerBoardTextView(board);
        for (Map.Entry<Point, Label> e : board.getLowerFieldLabel().entrySet()) {
            view.setLowerFieldLabel(e.getKey(), e.getValue());
        }

        textTerminal.println(view.toString());

        // TODO: Implement initial round and role Dice
        // TODO: Implement Check wincondition
        boolean running = true;
        while (running) {
            switch (getEnumValue(textIO, IngameMenuActions.class)) {
                case BUILD:
                    // TODO: Implement Build Structure
                    break;
                case TRADE:
                    // TODO: Implement Trade with Bank
                    break;
                case END_TURN:
                    // TODO: Implement switch to next player
                    // TODO: Role Dice
                    break;
                case END_THE_GAME:
                    running = isStillRunning();
                    break;
                default:
                    textTerminal.println("Something went wrong :(");
                    break;
            }
        }
    }

    private boolean isStillRunning()
    {
        textTerminal.println("You can't safe the progress you've made so far...\n" +
                             "Do You really want to end the game?");

        switch (getEnumValue(textIO, YesAndNo.class)) {
            case YES:
                // TODO: Implement reset everything!
                textTerminal.print(getTextWelcome());
                return false;
            case NO:
                return true;
            default:
                textTerminal.println("Something went wrong :(");
                return true;
        }
    }

    private SiedlerGame initializeSiedlerGame()
    {
        textTerminal.println();
        int winpoints = textIO.newIntInputReader()
              .withMinVal(3)
              .withMaxVal(10)
              .read("Declare number of winpoints: ");
        int numberOfPlayers = textIO.newIntInputReader()
              .withMinVal(2)
              .withMaxVal(4)
              .read("Enter the number of Settlers: ");
        return new SiedlerGame(winpoints, numberOfPlayers);
    }

    private String getTextWelcome()
    {
        return "\n"
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
            + "                                   ****\n";
    }

    private String getTextAbout()
    {
        return "\n****\n\n"

                + "You're playing our third Project called THE SETTLERS OF CATAN\n\n"

                + "Autors: Blattmann Peter   -- blattpet\n"
                + "        Jovanovic Nikola  -- jovanni1\n"
                + "        Koscica Stefan    -- kosciste\n"
                + "        Sileno Ennio      -- silenenn\n\n"

                + "For more information please visit our repository on Github :)\n\n"

                + "****\n";
    }

    private static <T extends Enum<T>> T getEnumValue(TextIO textIO, Class<T> commands)
    {
        return textIO.newEnumInputReader(commands).read("----");
    }
}