package ch.zhaw.catan;

import ch.zhaw.catan.Config.Faction;
import ch.zhaw.catan.Config.Resource;
import ch.zhaw.hexboard.Label;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * This class models a single Game with his information and functionalities.
 * author: Stefan Koscica
 */

public class SiedlerGame {

    private static final int OFFSET = 1;
    private static int playerAtTurn = 0;
    SiedlerBoard board = new SiedlerBoard();
    private int winPoints;
    private int numberOfPlayers;
    private List<Player> players = new ArrayList<>();

    public SiedlerGame(int winPoints, int numberOfPlayers) {
        this.winPoints = winPoints;
        this.numberOfPlayers = numberOfPlayers;
        setPlayers(numberOfPlayers);
        placeInitialSettlement(new Point(4, 0));

    }

    private void setPlayers(int numberOfPlayers) {

        for (int i = 0; i < numberOfPlayers; i++) {

            Player player = new Player(Faction.values()[i]);
            players.add(player);
        }

        placeInitialSettlement(new Point(6, 6));


    }

    /**
     * This method switches to next player who needs to make a turn.
     */
    public void switchToNextPlayer() {
        if (playerAtTurn < numberOfPlayers - OFFSET) {

            playerAtTurn++;
        } else {

            playerAtTurn = 0;
        }

    }

    /**
     * This method switches to the previous player who needs to make a turn.
     */
    public void switchToPreviousPlayer() {
        if (playerAtTurn == 0) {
            playerAtTurn = numberOfPlayers - OFFSET;

        } else {
            playerAtTurn--;

        }


    }


    /**
     * This method returns a list with all players.
     *
     * @return List with all players
     */
    public List<Player> getPlayer() {

        return players;
    }


    /**
     * This method returns the GameBoard
     *
     * @return the GameBoard
     */
    public SiedlerBoard getBoard() {

        return board;
    }


    /**
     * Returns the current player who needs to make a turn.
     *
     * @return the current player
     */
    public Player getCurrentPlayer() {
        return players.get(playerAtTurn);
    }


    public int getCurrentPlayerResourceStock(Resource resource) {
        // TODO: Implement
        return 0;
    }


    /**
     * Builds an  initial settlement
     *
     * @param position the specified position for the settlement
     * @return true, if the settlement has been built
     */
    public boolean placeInitialSettlement(Point position) {

        //TODO: Ressourcen gewinnen bei zweiter Siedlung

        Settlement settlement = new Settlement(getCurrentPlayer().getPlayerFaction());

        if (board.hasCorner(position)
                && board.getCorner(position) == null
                && getCurrentPlayer().hasAvailableSettlements()
                && isValidCorner(position)
                && board.getNeighboursOfCorner(position).isEmpty()) {

            getCurrentPlayer().initializeMeeple(settlement);

            board.setCorner(position, settlement.toString());
            return true;
        } else {
            return false;
        }


    }

    /**
     * Builds an initial road
     *
     * @param roadStart the Point where the road starts
     * @param roadEnd   the Point where the road ends
     * @return true, if the road has been built
     */
    public boolean placeInitialRoad(Point roadStart, Point roadEnd) {


        return buildRoad(roadStart, roadEnd);

    }

    /**
     * public Map<Faction, List<Resource>> throwDice(int dicethrow) {
     * // TODO: Implement
     * return null;
     * }
     */

    private Integer getFirstDigit(int number) {
        Integer digit;
        if (number >= 10) {
            digit = number / 10;
        } else {
            digit = 0;
        }
        return digit;
    }

    private Integer getLastDigit(int number) {
        Integer digit;
        digit = number % 10;
        return digit;
    }


    public void throwDice(int dicethrow) {

        String dice = getFirstDigit(dicethrow).toString() + getLastDigit(dicethrow).toString();
        List<Point> affectedFields = new ArrayList<>();

        for (Map.Entry<Point, Label> label : board.getLowerFieldLabel().entrySet()) {

            if (label.getValue().toString().equals(dice)) {

                affectedFields.add(label.getKey());
            }


            for (Player player : players) {

                for (int i = 0; i < player.getMeepleList().size(); i++) {

                    if (player.getMeepleList().get(i) instanceof Settlement) {

                        for (int j = 0; j < affectedFields.size(); j++)

                            for (int k = 0; k < board.getCornersOfField(affectedFields.get(j)).size(); k++)

                                if (player.getMeepleList().get(i).toString()
                                        .equals(board.getCornersOfField(affectedFields.get(j)).get(k))) {

                                    player.addRescourceFromSettlement(board.getField(affectedFields.get(j)).getResource());
                                    System.out.println(player.getResourceStock().get(0).toString());
                                }
                    }
                }
            }
        }

    }

    /**
     * This method builds a settlement at a specified position
     *
     * @param position the specified position for the settlement
     * @return
     */
    public boolean buildSettlement(Point position) {
        //TODO: Überprüfen und abziehen der Rohstoffe
        Settlement settlement = new Settlement(getCurrentPlayer().getPlayerFaction());
        boolean hasAdjacentRoads = false;
        List<Meeple> meeples = getCurrentPlayer().getMeepleList();
        for (int i = 0; i < meeples.size(); i++) {

            if (board.getAdjacentEdges(position).contains(meeples.get(i).toString())) {
                hasAdjacentRoads = true;
            }
        }
        boolean hasEnoughRessources = true;
        for (Resource resource : Config.Structure.SETTLEMENT.getCosts()) {

            if (getCurrentPlayer().getNumberOfSingleResource(resource) < 1) {
                hasEnoughRessources = false;
            }
        }


        if (board.getCorner(position) == null
                && hasAdjacentRoads
                && hasEnoughRessources
                && getCurrentPlayer().hasAvailableSettlements()
                && isValidCorner(position)
                && board.getNeighboursOfCorner(position).isEmpty()) {

            getCurrentPlayer().initializeMeeple(settlement);

            board.setCorner(position, settlement.toString());

            for (Resource resource : Config.Structure.SETTLEMENT.getCosts()) {

                getCurrentPlayer().removeResource(1, resource);
            }
            return true;
        } else {
            return false;
        }
    }

    private boolean isValidCorner(Point position) {

        boolean isValid = false;
        for (int i = 0; i < Config.Resource.values().length; i++) {

            for (int j = 0; j < board.getFields(position).size(); j++) {

                if (board.getFields(position).get(j).getResource() == Config.Resource.values()[i]) {
                    isValid = true;
                }

            }
        }
        return isValid;
    }


    public boolean buildCity(Point position) {
        // TODO: OPTIONAL task - Implement
        return false;
    }

    /**
     * This method builds a road at a specicfied location
     *
     * @param roadStart the Point where the road starts
     * @param roadEnd   the Point where the road ends
     * @return true, if the road has been built
     */
    public boolean buildRoad(Point roadStart, Point roadEnd) {
        Road road = new Road(getCurrentPlayer().getPlayerFaction());
        boolean hasAdjacentElements = false;
        List<String> meeples = new ArrayList<>();
        for (Meeple meeple : getCurrentPlayer().getMeepleList()) {
            meeples.add(meeple.toString());
        }

        for (int i = 0; i < meeples.size(); i++) {

            if (board.getAdjacentEdges(roadStart).
                    contains(meeples.get(i)) ||
                    board.getCorner(roadStart) == (meeples.get(i))) {
                if (board.getCorner(roadEnd) == null
                        || meeples.contains(board.getCorner(roadEnd))) {
                    if (isValidCorner(roadEnd)) {
                        hasAdjacentElements = true;
                    }

                }
            }

            if (board.getAdjacentEdges(roadEnd).
                    contains(meeples.get(i)) ||
                    board.getCorner(roadEnd) == (meeples.get(i))) {
                if (board.getCorner(roadStart) == null ||
                        meeples.contains(board.getCorner(roadStart))) {
                    if (isValidCorner(roadStart)) {
                        hasAdjacentElements = true;
                    }

                }
            }
        }

        boolean hasEnoughRessources = true;
        for (Resource resource : Config.Structure.ROAD.getCosts()) {

            if (getCurrentPlayer().getNumberOfSingleResource(resource) < 1) {
                hasEnoughRessources = false;
            }
        }

        if (board.hasEdge(roadStart, roadEnd) &&
                board.getEdge(roadStart, roadEnd) == null
                && hasAdjacentElements
                && hasEnoughRessources) {

            getCurrentPlayer().initializeMeeple(road);
            board.setEdge(roadStart, roadEnd, road.toString());


            for (Resource resource : Config.Structure.ROAD.getCosts()) {

                getCurrentPlayer().removeResource(1, resource);
            }

            return true;
        } else {
            return false;
        }
    }

    public boolean tradeWithBankFourToOne(Resource offer, Resource want) {
        // TODO: Implement
        return false;
    }

    public Player getWinner() {
        // TODO: Implement
        return null;
    }
}
