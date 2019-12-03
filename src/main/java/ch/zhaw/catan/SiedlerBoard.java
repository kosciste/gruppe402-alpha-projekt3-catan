package ch.zhaw.catan;

import ch.zhaw.catan.Config.Land;
import ch.zhaw.hexboard.HexBoard;
import ch.zhaw.hexboard.Label;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * This class holds every information about the Board
 *
 * @author Sileno Ennio
 */
public class SiedlerBoard extends HexBoard<Land, String, String, String> {

    private static final int FIRST_CHAR = 0;
    private static final int SECOND_CHAR = 1;

    private Map<Point, Land> standardLandPlacement = Config.getStandardLandPlacement();
    private Map<Point, Label> lowerFieldLabel = new HashMap<>();

    /**
     * Creates a new Siedlerboard with the standard properties
     */
    public SiedlerBoard()
    {
        createStandardBoard();
        putDicevaluesForStandardBoard();
    }

    // This method adds fields to the HexBoard.
    private void createStandardBoard()
    {
        for (Map.Entry<Point, Land> landvalue : standardLandPlacement.entrySet()) {
            addField(landvalue.getKey(), landvalue.getValue());
        }
    }

    // This method converts the dicenumbers into chars and puts them into the Map lowerFieldlabel.
    private void putDicevaluesForStandardBoard()
    {
        for(Map.Entry<Point, Integer> dicevalue : Config.getStandardDiceNumberPlacement().entrySet()) {
            String dicevalueAsString = dicevalue.getValue().toString();
            char firstChar = '0';
            char secondChar = '0';
            if(dicevalueAsString.length() == 2) {
                firstChar = dicevalueAsString.charAt(0);
                secondChar = dicevalueAsString.charAt(1);
            }
            else {
                secondChar = dicevalueAsString.charAt(0);
            }
            lowerFieldLabel.put(dicevalue.getKey(), new Label(firstChar, secondChar) );
        }
    }

    /*private char[] convertDicenumberInttoChar(int dicevalue)
    {
        char[] dicenumberAsChars = {'0', '0'};
        String dicevalueAsString = dicevalue.getValue().toString();
        char firstChar = '0';
        char secondChar = '0';
        if(dicevalueAsString.length() == 2) {
            firstChar = dicevalueAsString.charAt(0);
            secondChar = dicevalueAsString.charAt(1);
        }
        else {
            secondChar = dicevalueAsString.charAt(0);
        }
    }*/

    /**
     * @return lowerFieldLabel in this map are dicevalues in form of chars stored.
     */
    public Map<Point, Label> getLowerFieldLabel()
    {
        return lowerFieldLabel;
    }
}
