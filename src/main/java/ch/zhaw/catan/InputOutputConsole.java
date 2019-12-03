package ch.zhaw.catan;

import java.awt.Point;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;

/**
 * This class represents the input and output Console. It reads the input from the user and prints out
 * Strings for example Messages or the Siedlerboard
 *
 * @author Sileno Ennio
 */
public class InputOutputConsole {

    private static TextIO textIO =TextIoFactory.getTextIO();
    private  static TextTerminal<?> textTerminal = textIO.getTextTerminal();

    //defines the Window to a specified size.
    static
    {
        textTerminal.getProperties().setPaneDimension(1280,720);
    }

    // TODO to comment!
    public static <T extends Enum<T>> T getEnumValue( Class<T> commands)
    {
        return textIO.newEnumInputReader(commands).read("----");
    }

    /**
     * Prints Strings on the console.
     * @param string a string which should be printed out.
     */
    public static void printText(String string)
    {
        textTerminal.println(string);
    }

    /**
     * Prints the Siedlerboard
     * @param view an Object of the Typ SiedlerBoardTextView
     */
    public static void printSiedlerBoard(SiedlerBoardTextView view)
    {
        textTerminal.println(view.toString());
    }

    public static int setNumberOfWinpointsToWin()
    {
        return textIO.newIntInputReader()
                .withMinVal(3)
                .withMaxVal(10)
                .read("Declare number of winpoints: ");
    }

    /**
     * Reads an input between 2 and 4.
     * @return int, number of Players
     */
    public static int setNumberOfPlayers()
    {
        return textIO.newIntInputReader()
                .withMinVal(Config.MIN_NUMBER_OF_PLAYERS)
                .withMaxVal(IngameMenu.MAX_NUMBER_OF_PLAYERS)
                .read("Enter the number of Settlers: ");
    }
    
    /**
	 * Reads a specified x- and y-coordinate from the console and returns a point
	 * with these coordinates.
	 * 
	 * @return a point with the specified coordinates
	 */
	public static Point choosePoint() {
		int xCoordinate = textIO.newIntInputReader().read("x-coordinate");
		int yCoordinate = textIO.newIntInputReader().read("y-coordinate");
		return new Point(xCoordinate, yCoordinate);
	}

    /**
     * Closes the terminal
     */
    public static void closeInOutput()
    {
        textTerminal.dispose();
    }
}
