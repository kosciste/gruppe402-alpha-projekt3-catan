package ch.zhaw.catan;

import ch.zhaw.catan.Config.Land;
import ch.zhaw.hexboard.HexBoard;
import ch.zhaw.hexboard.Label;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class SiedlerBoard extends HexBoard<Land, String, String, String> {

    private Map<Point, Land> standardLandPlacement = Config.getStandardLandPlacement();
    private Map<Point, Integer> standardDiceNumberPlacement = Config.getStandardDiceNumberPlacement();
    private Map<Point, Label> lowerFieldLabel = new HashMap<>();


    public SiedlerBoard()
    {
        //Fülle die Hexagons mit Materialien
        for(Map.Entry<Point, Land> landvalue : standardLandPlacement.entrySet()) {
            addField(landvalue.getKey(), landvalue.getValue());
        }

        //Fülle die Hexagons mit den Würfelwerten
        for(Map.Entry<Point, Integer> dicevalue : standardDiceNumberPlacement.entrySet()) {
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

    public Map<Point, Label> getLowerFieldLabel()
    {
        return lowerFieldLabel;
    }
}
