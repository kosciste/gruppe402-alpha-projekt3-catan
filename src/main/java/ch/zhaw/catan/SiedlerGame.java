package ch.zhaw.catan;

import ch.zhaw.catan.Config.Faction;
import ch.zhaw.catan.Config.Resource;

import java.awt.Point;
import java.util.*;

/**
 * author: kosciste
 */

public class SiedlerGame {

    private static int playerAtTurn = 0;
    SiedlerBoard board = new SiedlerBoard();
    private int winPoints;
    private int numberOfPlayers;
    private List<Player> players = new ArrayList<>();

    public SiedlerGame(int winPoints, int numberOfPlayers) {
        this.winPoints = winPoints;
        this.numberOfPlayers = numberOfPlayers;
        for (int i = 0; i < numberOfPlayers; i++) {

            Player player = new Player(Faction.values()[i]);
            players.add(player);
        }
        placeInitialSettlement(new Point(4,0));

    }

    /**
     * This method switches to next player who needs to make a turn.
     */
    public void switchToNextPlayer() {
        if (playerAtTurn < 4) {

            playerAtTurn++;
        }

        playerAtTurn = 0;
    }

    /**
     * This method switches to the previous player who needs to make a turn.
     */
    public void switchToPreviousPlayer() {
        if (playerAtTurn > 0) {
            playerAtTurn--;

        }

        playerAtTurn = 4;
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

      Settlement settlement = new Settlement(getCurrentPlayer().getPlayerFaction());

      if (board.hasCorner(position)
              && board.getCorner(position) == null
              && getCurrentPlayer().hasAvailableSettlements()
              && board.getNeighboursOfCorner(position).isEmpty()) {

        getCurrentPlayer().initializeMeeple(settlement);

        board.setCorner(position, settlement.toString());
        return true;
      }
      else {
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


    public Map<Faction, List<Resource>> throwDice(int dicethrow) {
        // TODO: Implement
        return null;
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
        if (board.getCorner(position) == null && hasAdjacentRoads
                && getCurrentPlayer().hasAvailableSettlements()
                && board.getNeighboursOfCorner(position).isEmpty()) {

            getCurrentPlayer().initializeMeeple(settlement);

            board.setCorner(position, settlement.toString());
            return true;
        }
        else {
            return false;
        }
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
        for(Meeple meeple : getCurrentPlayer().getMeepleList()){
          meeples.add(meeple.toString());
        }

        for(int i = 0; i < meeples.size() ;i++){

            if (board.getAdjacentEdges(roadStart).
                    contains(meeples.get(i)) ||
                    board.getCorner(roadStart) == (meeples.get(i))) {
                if (board.getCorner(roadEnd) == null ||
                        meeples.contains(board.getCorner(roadEnd))) {
                    hasAdjacentElements = true;
                }
            }

            if (board.getAdjacentEdges(roadEnd).
                    contains(meeples.get(i)) ||
                    board.getCorner(roadEnd) == (meeples.get(i))) {
                if (board.getCorner(roadStart) == null ||
                        meeples.contains(board.getCorner(roadStart))) {
                    hasAdjacentElements = true;
                }
            }
        }

            if (board.hasEdge(roadStart, roadEnd) &&
                    board.getEdge(roadStart, roadEnd) == null
                    && hasAdjacentElements) {

                getCurrentPlayer().initializeMeeple(road);
                board.setEdge(roadStart, roadEnd, road.toString());

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
