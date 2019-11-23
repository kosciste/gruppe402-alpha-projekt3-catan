package ch.zhaw.catan;

import ch.zhaw.hexboard.Label;

import java.awt.Point;
import java.util.Map;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;

public class Dummy2 {

    public enum Actions {
        SHOW, BUILD, QUIT
    }

    private void run() {
        TextIO textIO = TextIoFactory.getTextIO();
        TextTerminal<?> textTerminal = textIO.getTextTerminal();


        SiedlerBoard board = new SiedlerBoard();

        SiedlerBoardTextView view = new SiedlerBoardTextView(board);
        for (Map.Entry<Point, Label> e : board.getLowerFieldLabel().entrySet()) {
            view.setLowerFieldLabel(e.getKey(), e.getValue());
        }

        int winPoints = 0;
        boolean running = true;
        while (running && winPoints < 3) {
            switch (getEnumValue(textIO, Actions.class)) {
                case SHOW:
                    textTerminal.println(view.toString());
                    break;
                case QUIT:
                    running = false;
                    break;
                case BUILD:
                    switch (getEnumValue(textIO, Config.Structure.class)) {
                        case ROAD:
                            textTerminal.println("Declare the beginning of your new Road");
                            int xCoordinateBeginning = textIO.newIntInputReader()
                                    .withMinVal(0)
                                    .withMaxVal(14)
                                    .read("X-Coordiante");
                            int yCoordinateBeginning = textIO.newIntInputReader()
                                    .withMinVal(0)
                                    .withMaxVal(22)
                                    .read("Y-Coordiante");
                            Point beginningRoad = new Point(xCoordinateBeginning, yCoordinateBeginning);
                            textTerminal.println("Declare the ending of your new Road");
                            int xCoordinateEnd = textIO.newIntInputReader()
                                    .withMinVal(0)
                                    .withMaxVal(14)
                                    .read("X-Coordiante");
                            int yCoordinateEnd = textIO.newIntInputReader()
                                    .withMinVal(0)
                                    .withMaxVal(22)
                                    .read("Y-Coordiante");
                            Point endRoad = new Point(xCoordinateEnd, yCoordinateEnd);
                            board.setEdge(beginningRoad, endRoad, "r");
                            textTerminal.println(view.toString());
                            textIO.getTextTerminal();
                            break;
                        case SETTLEMENT:
                            int xCoordinate = textIO.newIntInputReader()
                                .withMinVal(0)
                                .withMaxVal(14)
                                .read("X-Coordiante");
                            int yCoordinate = textIO.newIntInputReader()
                                .withMinVal(0)
                                .withMaxVal(22)
                                .read("Y-Coordiante");
                            board.setCorner(new Point(xCoordinate, yCoordinate), "RR");
                            textTerminal.println(view.toString());
                            textIO.getTextTerminal();
                            winPoints++;
                            break;
                        case CITY:
                            break;
                        default:
                            throw new IllegalStateException("Internal error found - Structure not implemented.");
                    }
                    break;
                default:
                    throw new IllegalStateException("Internal error found - Command not implemented.");
            }
        }
        textTerminal.println("the winner is RR!");
        textIO.dispose();
    }

    private static <T extends Enum<T>> T getEnumValue(TextIO textIO, Class<T> commands) {
        return textIO.newEnumInputReader(commands).read("What would you like to do?");
    }

    public static void main(String[] args) {
        new Dummy2().run();
    }
}
