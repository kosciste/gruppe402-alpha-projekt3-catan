package ch.zhaw.catan;

import ch.zhaw.catan.Config.Faction;
import ch.zhaw.catan.Config.Resource;
import ch.zhaw.hexboard.Label;

import java.awt.Point;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;

/**
 * This class models a single siedler game with its information and functionalities.
 * author: Stefan Koscica
 */

public class SiedlerGame {

    protected static final int POINTS_LONGEST_ROAD = 2;
	private static final int WINPOINTS = 7;
    private static final int OFFSET = 1;
    SiedlerBoard board = new SiedlerBoard();
    Bank bank = new Bank();
    Map<String, Integer> visitedRoads = new HashMap<>();
    private int playerAtTurn = 0;
    private int numberOfPlayers;
    private List<Player> players = new ArrayList<>();
    private int longestRoad = 0;


    public SiedlerGame(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
        setPlayers(numberOfPlayers);
    }

    private void setPlayers(int numberOfPlayers) {

        for (int i = 0; i < numberOfPlayers; i++) {

            Player player = new Player(Faction.values()[i]);
            players.add(player);
        }
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

    /**
     * The method builds an  initial settlement without the need to pay
     * with resources. It also pays out resources at the second initial round.
     *
     * @param position the specified position for the settlement
     * @param payout   'true' if a payout of resources is needed
     * @return true, if the settlement has been built
     */
    public boolean placeInitialSettlement(Point position, boolean payout) {

        Player currentPlayer = getCurrentPlayer();
        Settlement settlement = new Settlement(getCurrentPlayer().getPlayerFaction());

        if (board.hasCorner(position)
                && board.getCorner(position) == null
                && getCurrentPlayer().hasAvailableSettlements()
                && isValidCorner(position)
                && board.getNeighboursOfCorner(position).isEmpty()) {

            getCurrentPlayer().initializeMeeple(settlement);
            board.setCorner(position, settlement.toString());
            currentPlayer.setWinPoints(currentPlayer.getWinPoints() + 1);

            if (payout) {

                for (int i = 0; i < board.getFields(position).size(); i++) {
                    getCurrentPlayer().addRescourceFromSettlement
                            (board.getFields(position).get(i).getResource());
                    bank.removeBankResource(1, board.getFields(position)
                            .get(i).getResource());
                }
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * The method builds an  initial settlement without the need to pay
     * with resources.
     *
     * @param roadStart the Point where the road starts
     * @param roadEnd   the Point where the road ends
     * @return true, if the road has been built
     */
    public boolean placeInitialRoad(Point roadStart, Point roadEnd) {


        Player currentPlayer = getCurrentPlayer();
        Road road = new Road(currentPlayer.getPlayerFaction(), roadStart, roadEnd);

        if (hasValidConditionsForRoad(currentPlayer, roadStart, roadEnd)) {
            getCurrentPlayer().initializeMeeple(road);
            board.setEdge(roadStart, roadEnd, road.toString());

            return true;
        } else {
            return false;
        }
    }

    /**
     * This method checks the number of the 'dicethrow' and searches the fields which are affected.
     * After that it checks all adjacent corners(settlements or cities) and assigns the resources
     * to thew corresponding player who owns this corners
     *
     * @param dicethrow The number of the dices
     * @return 'Map' with the players and the added resources
     */

    public Map<Player, List<Config.Resource>> throwDice(int dicethrow) {

        String dice = getFirstDigit(dicethrow).toString() + getLastDigit(dicethrow).toString();
        List<Point> affectedFields = new ArrayList<>();
        Map<Player, List<Config.Resource>> changes = new HashMap<>();

        if (!(dicethrow == 7)) {

            for (Map.Entry<Point, Label> label : board.getLowerFieldLabel().entrySet()) {

                if (label.getValue().toString().equals(dice)) {

                    affectedFields.add(label.getKey());
                }
            }

            for (Player player : players) {
                List<Config.Resource> resources = new ArrayList<>();

                for (Point field : affectedFields) {

                    List<String> corners = board.getCornersOfField(field);

                    for (String corner : corners) {

                        if (corner.equals(player.getPlayerFaction().toString())) {

                            if (bank.hasBankEnoughResources(1, board.getField(field).getResource())) {

                                player.addRescourceFromSettlement
                                        (board.getField(field).getResource());

                                resources.add(board.getField(field).getResource());
                                bank.removeBankResource(1, board.getField(field).getResource());
                            }
                        }

                        if (corner.equals(player.getPlayerFaction().toString().toUpperCase())) {
                            if (bank.hasBankEnoughResources(2, board.getField(field).getResource())) {
                                player.addRescourceFromCity
                                        (board.getField(field).getResource());

                                resources.add(board.getField(field).getResource());
                                resources.add(board.getField(field).getResource());
                                bank.removeBankResource(2, board.getField(field).getResource());
                            }
                        }
                    }
                }
                changes.put(player, resources);
            }
        } else {
            changes.clear();
            stealResourcesFromPlayer();
        }

        return changes;
    }

    /**
     * Returns the first digit of a two-digit number
     *
     * @param number
     * @return first digit as 'int'
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

    /**
     * Returns the last digit of a two-digit number
     *
     * @param number
     * @return last digit as 'int'
     */

    private Integer getLastDigit(int number) {
        Integer digit;
        digit = number % 10;
        return digit;
    }

    /**
     * The robber method is called when the number 7 is rolled. The method checks the number
     * of resources pro player. Players who have more than 7 resources will be robbed of half
     * the resources.
     * An example of the rounding system: A player with 9 resources must give 4 resources back.
     */
    private void stealResourcesFromPlayer() {
        for (Player player : players) {
            int numberOfResources = player.getNumberOfTotalResources();
            if (numberOfResources > Config.MAX_CARDS_IN_HAND_NO_DROP) {
                if (numberOfResources % 2 == 0) {  //even number
                    stealingResources(player, numberOfResources / 2);
                } else {    //uneven number
                    stealingResources(player, (numberOfResources - 1) / 2);
                }
            }
        }
    }

    /**
     * This method performs the Robber's function. It removes a certain number of random resources
     * from the player and add them back to the bank.
     *
     * @param player            The player who gets robbed.
     * @param numberOfResources Number of resources robbed
     */
    private void stealingResources(Player player, int numberOfResources) {
        int index = 0;
        for (int i = 0; i < numberOfResources; i++) {
            index = (int) (Math.random() * player.getNumberOfTotalResources());
            bank.addBankResources(1, player.getResourceStock().get(index));
            player.getResourceStock().remove(index);
        }
    }


    /**
     * This method builds a settlement at a specified position.
     * if all needed conditions match.
     *
     * @param position the specified position for the settlement
     * @return 'true' if the settlement has been successfully built.
     */
    public boolean buildSettlement(Point position) {
        Player currentPlayer = getCurrentPlayer();
        Settlement settlement = new Settlement(currentPlayer.getPlayerFaction());
        boolean hasAdjacentRoads = false;
        List<Meeple> meeples = getCurrentPlayer().getMeepleList();
        for (int i = 0; i < meeples.size(); i++) {

            if (board.getAdjacentEdges(position).contains(meeples.get(i).toString())) {
                hasAdjacentRoads = true;
            }
        }
        if (board.getCorner(position) == null
                && hasAdjacentRoads
                && hasEnoughResources(Config.Structure.SETTLEMENT.getCosts(), currentPlayer)
                && getCurrentPlayer().hasAvailableSettlements()
                && isValidCorner(position)
                && board.getNeighboursOfCorner(position).isEmpty()) {

            getCurrentPlayer().initializeMeeple(settlement);
            board.setCorner(position, settlement.toString());
            payWithResources(Config.Structure.SETTLEMENT.getCosts(), currentPlayer);
            currentPlayer.setWinPoints(currentPlayer.getWinPoints() + 1);
            return true;
        } else {
            return false;
        }
    }


    /**
     * The method checks if the corner which is approached is
     * positioned at a valid field of the game board.
     *
     * @param position the position the player wants to approach
     * @return 'true' if the corner who is approached is a valid postition
     */
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


    /**
     * The method checks if the player has enough resources to build
     * a specified kind of meeple.
     *
     * @param list          the costs of resources fot the specified meeple
     * @param currentPlayer the player who wants to build
     * @return 'true' if the player can pay the costs.
     */
    private boolean hasEnoughResources(List<Config.Resource> list, Player currentPlayer) {

        boolean hasEnoughRessources = true;
        for (Resource resource : list) {

            if (getCurrentPlayer().getNumberOfSingleResource(resource) < 1) {
                hasEnoughRessources = false;
            }
        }
        return hasEnoughRessources;
    }

    /**
     * This method removes resources from the player which he uses
     * to build something.
     *
     * @param list          the costs of resources fot the specified meeple
     * @param currentPlayer the player who wants to build
     */
    private void payWithResources(List<Config.Resource> list, Player currentPlayer) {

        for (Resource resource : list) {

            getCurrentPlayer().removeResource(1, resource);
            bank.addBankResources(1, resource);
        }
    }

    /**
     * This method builds a road at a specicfied location,
     * if all needed conditions match.
     *
     * @param roadStart the point where the road starts
     * @param roadEnd   the point where the road ends
     * @return 'true' if the road has been successfully built.
     */
    public boolean buildRoad(Point roadStart, Point roadEnd) {
        Player currentPlayer = getCurrentPlayer();
        Road road = new Road(currentPlayer.getPlayerFaction(), roadStart, roadEnd);

        if (hasValidConditionsForRoad(currentPlayer, roadStart, roadEnd)
                && hasEnoughResources(Config.Structure.ROAD.getCosts(), currentPlayer)) {

            getCurrentPlayer().initializeMeeple(road);
            board.setEdge(roadStart, roadEnd, road.toString());
            payWithResources(Config.Structure.ROAD.getCosts(), currentPlayer);
            return true;
        } else {
            return false;
        }
    }

    /**
     * This method checks if all conditions are given to build
     * a road at specified position
     *
     * @param currentPlayer
     * @param roadStart     the point where the road starts
     * @param roadEnd       he point where the road ends
     * @return 'true' if the road has been successfully built.
     */
    private boolean hasValidConditionsForRoad(Player currentPlayer, Point roadStart, Point roadEnd) {
        return (board.hasEdge(roadStart, roadEnd) &&
                board.getEdge(roadStart, roadEnd) == null
                && (hasAdjacentElementsForRoad(currentPlayer, roadStart, roadEnd)
                || hasAdjacentElementsForRoad(currentPlayer, roadEnd, roadStart)));
    }

    /**
     * This method checks if the position where a player wants to build
     * a road is valid.That means a adjacent meeple needs to be
     * in connection to the new road.
     *
     * @param currentPlayer
     * @param roadStart     the point where the road starts
     * @param roadEnd       he point where the road ends
     * @return 'true' if the defined position has adjacent elements
     */
    private boolean hasAdjacentElementsForRoad(Player currentPlayer, Point roadStart, Point roadEnd) {

        boolean hasAdjacentElements = false;

        List<String> meeples = new ArrayList<>();
        for (Meeple meeple : currentPlayer.getMeepleList()) {
            meeples.add(meeple.toString());
        }

        for (int i = 0; i < meeples.size(); i++) {
            if (board.getAdjacentEdges(roadStart).contains(meeples.get(i))
                    || meeples.get(i).equals(board.getCorner(roadStart))
                    || meeples.get(i).toUpperCase().equals((board.getCorner(roadStart)))) {
                if (isValidCorner(roadEnd)) {
                    hasAdjacentElements = true;
                }
            }
        }
        return hasAdjacentElements;
    }

    /**
     * This Method build a city at a specified position,
     * if all needed conditions match.
     *
     * @param position the specified position for the city
     * @return true, if the city has been built
     */
    public boolean buildCity(Point position) {
        Player currentPlayer = getCurrentPlayer();
        City city = new City(getCurrentPlayer().getPlayerFaction());
        boolean hasSettlement = false;

        if (currentPlayer.getPlayerFaction().toString().equals(board.getCorner(position))) {
            hasSettlement = true;
        }
        if (hasSettlement
                && hasEnoughResources(Config.Structure.CITY.getCosts(), currentPlayer)
                && getCurrentPlayer().hasAvailableCities()) {

            getCurrentPlayer().removeSettlement();
            getCurrentPlayer().initializeMeeple(city);
            board.setCorner(position, city.toString());
            payWithResources(Config.Structure.CITY.getCosts(), currentPlayer);
            currentPlayer.setWinPoints(currentPlayer.getWinPoints() + 1);
            return true;
        } else {
            return false;
        }
    }

    /**
     * This method trade 4 offered resources to one wished resource.
     *
     * @param offer The resource you offer 4 times.
     * @param want  The resource you get if your offer is valid.
     * @return true if the trade went well, returns false if there wasn't enough resoureces offered.
     */
    public boolean tradeWithBankFourToOne(Resource offer, Resource want) {
        if (want != null
                && bank.hasBankEnoughResources(1, want)
                && hasEnoughResourcesToTrade(offer)) {

            getCurrentPlayer().removeResource(4, offer);
            bank.addBankResources(4, offer);
            getCurrentPlayer().getResourceStock().add(want);
            bank.removeBankResource(1, want);
            return true;
        }
        return false;
    }

    private boolean hasEnoughResourcesToTrade(Resource offer) {
        if (offer == null) {
            return false;
        }

        if (getCurrentPlayer().getNumberOfSingleResource(offer) >= 4) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Searches for the Player who has won the game
     *
     * @return 'Player' who has reached the max amount of points,
     * if there is no winner return 'null'
     */
    public Player getWinner() {
        Player winner = null;

        for (Player player : players) {

            if (player.getWinPoints() >= WINPOINTS) {

                winner = player;
            }

        }

        return winner;
    }

    /**
     * This method checks if a player has a road that is longer than
     * five connected Edges which are not interrupted by a different
     * settlement or city. If the condition is true the player gets two extra
     * points except there is another player with a even longer road.
     *
     * @return 'Player' who gets five extra points or 'Null', if there
     * is no player who matches the conditions.
     */

    public Player getPlayerWithLongestRoad() {
        Player playerWithLongestRoad = null;
        for (Player player : players) {

            if (getLongestRoad(player) >= 5 && getLongestRoad(player) > longestRoad) {

                longestRoad = getLongestRoad(player);
                playerWithLongestRoad = player;
            }
        }

        return playerWithLongestRoad;
    }

    /**
     * This method returns the size of the longest road of a specified player
     * regarding the game rules.
     *
     * @param player the player whose roads need to be checked
     * @return 'int' size of the longest road
     */
    public int getLongestRoad(Player player) {
        int longestRoad = 0;

        Set<Point> roadCorners = new HashSet<>();

        for (Meeple meepple : player.getMeepleList()) {

            if (meepple instanceof Road) {
                roadCorners.add(((Road) meepple).getRoadStart());
                roadCorners.add(((Road) meepple).getRoadEnd());
            }
        }
        for (Point corner : roadCorners) {
            visitedRoads.clear();
            searchForLongestRoadAtCorner(corner, player, 1);
            for (int i : visitedRoads.values()) {
                if (i > longestRoad) {
                    longestRoad = i;
                }
            }
        }
        return longestRoad;
    }
    /**
     * This method is called by 'getLongestRoad' an searches the
     * longest road at specified corner that could be the ending or beginning
     * of a road. When the  length of the road is found it saves then the road
     * with the calculated length to a Map.
     *
     * @param corner the corner that needs to be checked
     * @param player the currentPlayer who is checked
     * @param length the current length of the road
     */
    private void searchForLongestRoadAtCorner(Point corner, Player player, int length) {

        for (Road road : getAdjacentRoads(corner, player.getPlayerFaction())) {

            if (road.OWNER.toString().equals(player.getPlayerFaction().toString()) &&
                    !visitedRoads.containsKey(road.getUniqueID()) &&
                    !visitedRoads.containsKey(road.getUniqueIDreverse())) {

                if (road.getRoadStart().toString().equals(corner.toString())) {

                    visitedRoads.put(road.getUniqueID(), length);
                    visitedRoads.put(road.getUniqueIDreverse(), length);

                    if (player.getPlayerFaction().toString()
                            .equals(board.getCorner(road.getRoadEnd()))
                            || board.getCorner(road.getRoadEnd()) == null) {

                        searchForLongestRoadAtCorner(road.getRoadEnd(), player, length + 1);
                    }
                } else if (road.getRoadEnd().toString().equals(corner.toString())) {
                    visitedRoads.put(road.getUniqueID(), length);
                    visitedRoads.put(road.getUniqueIDreverse(), length);

                    if (player.getPlayerFaction().toString()
                            .equals(board.getCorner(road.getRoadStart()))
                            || board.getCorner(road.getRoadStart()) == null) {

                        searchForLongestRoadAtCorner(road.getRoadStart(), player, length + 1);
                    }
                }
            }
        }

    }

    /**
     * Returns the non-null roads of the roads that directly connect
     * to the specified corner.
     *
     * @param corner the position of the corner
     * @param player the faction of the player whose roads are checked
     * @return the non-null road elements
     */
    private List<Road> getAdjacentRoads(Point corner, Faction player) {

        List<Road> roads = new ArrayList<>();
        for (int i = 0; i < board.getNeighborsOfCornerAsPoints(corner).size(); i++) {
            if (board.hasEdge(corner, board.getNeighborsOfCornerAsPoints(corner).get(i))) {

                if (board.getEdge(corner, board.getNeighborsOfCornerAsPoints(corner).get(i)) != null) {

                    roads.add(new Road(player, corner,
                            board.getNeighborsOfCornerAsPoints(corner).get(i)));
                }
            }
        }
        return roads;
    }
}
