package ch.zhaw.catan;

import ch.zhaw.catan.Config.Land;
import ch.zhaw.hexboard.HexBoard;
import ch.zhaw.hexboard.Label;

import java.awt.*;
import java.util.*;
import java.util.List;

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
     * Creates a new Siedlerboard with the standard properties.
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

    // This method puts dicenumbers as chars into the Map lowerFieldlabel.
    private void putDicevaluesForStandardBoard()
    {
        for(Map.Entry<Point, Integer> dicevalue : Config.getStandardDiceNumberPlacement().entrySet()) {
            char[] dicevalueAsChars = convertDicenumberIntoArrayOfChars(dicevalue);
            lowerFieldLabel.put(dicevalue.getKey(), new Label(dicevalueAsChars[FIRST_CHAR], dicevalueAsChars[SECOND_CHAR]));
        }
    }

    private char[] convertDicenumberIntoArrayOfChars(Map.Entry<Point, Integer> dicevalue)
    {
        char[] dicenumberAsChars = {'0', '0'};
        String dicevalueAsString = dicevalue.getValue().toString();
        if(dicevalueAsString.length() == 2) {
            dicenumberAsChars[FIRST_CHAR] = dicevalueAsString.charAt(FIRST_CHAR);
            dicenumberAsChars[SECOND_CHAR] = dicevalueAsString.charAt(SECOND_CHAR);
        }
        else {
            dicenumberAsChars[SECOND_CHAR] = dicevalueAsString.charAt(FIRST_CHAR);
        }
        return dicenumberAsChars;
    }

    /**
     * @return lowerFieldLabel in this map are dicevalues in form of chars stored.
     */
    public Map<Point, Label> getLowerFieldLabel()
    {
        return lowerFieldLabel;
    }


    /**
     * Returns the (non-null) corner data elements of the corners that are direct
     * @param position the location of the corner for which to return the direct
     * neighbors
     * @return list with non-null corner data elements
     */
    public List<Point> getNeighborsOfCornerAsPoints(Point position){

        List<Point> result = new ArrayList<>();
        for (Point corner : super.getAdjacentCorners(position)) { ;
            if (corner != null) {
                result.add(corner);
            }
        }
        return result;
    }

}
