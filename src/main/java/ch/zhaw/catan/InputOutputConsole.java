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

    public static final int TERMINAL_WIDTH = 1280;
    public static final int TERMINAL_HEIGHT = 720;

    private static TextIO textIO =TextIoFactory.getTextIO();
    private  static TextTerminal<?> textTerminal = textIO.getTextTerminal();

    //defines the Window to a specified size.
    static
    {
        textTerminal.getProperties().setPaneDimension(TERMINAL_WIDTH,TERMINAL_HEIGHT);
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
     * This method returns a resouce from the userinput
     * @return resource
     */
    public static Config.Resource chooseResource() {
        return getEnumValue(Config.Resource.class);
    }

    /**
     * Closes the terminal
     */
    public static void closeInOutput()
    {
        textTerminal.dispose();
    }
}
