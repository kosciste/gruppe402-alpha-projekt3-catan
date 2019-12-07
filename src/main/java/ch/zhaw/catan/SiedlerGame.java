package ch.zhaw.catan;

import ch.zhaw.catan.Config.Faction;
import ch.zhaw.catan.Config.Resource;
import ch.zhaw.hexboard.Label;
import org.w3c.dom.Node;

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
    Bank bank = new Bank();
    private int winPoints;
    private int numberOfPlayers;
    private List<Player> players = new ArrayList<>();


    public SiedlerGame(int winPoints, int numberOfPlayers) {
        this.winPoints = winPoints;
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
     * Builds an  initial settlement
     *
     * @param position the specified position for the settlement
     * @return true, if the settlement has been built
     */
    public boolean placeInitialSettlement(Point position, boolean payout) {

        Settlement settlement = new Settlement(getCurrentPlayer().getPlayerFaction());

        if (board.hasCorner(position)
                && board.getCorner(position) == null
                && getCurrentPlayer().hasAvailableSettlements()
                && isValidCorner(position)
                && board.getNeighboursOfCorner(position).isEmpty()) {

            getCurrentPlayer().initializeMeeple(settlement);
            board.setCorner(position, settlement.toString());

            if(payout) {

                    for(int i = 0; i < board.getFields(position).size();i++)
                    {
                        getCurrentPlayer().addRescourceFromSettlement
                                (board.getFields(position).get(i).getResource());
                        bank.removeBankResource(1,board.getFields(position)
                                .get(i).getResource());

                    }

            }
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


        Player currentPlayer = getCurrentPlayer();
        Road road = new Road(currentPlayer.getPlayerFaction(),roadStart,roadEnd);

        if (hasValidConditionsForRoad(currentPlayer,roadStart,roadEnd)) {
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
     * @param dicethrow The number of the dices
     * @return 'Map' with the players and the added resources
     */

    public Map<Player,List<Config.Resource>> throwDice(int dicethrow) {

        String dice = getFirstDigit(dicethrow).toString() + getLastDigit(dicethrow).toString();
        List<Point> affectedFields = new ArrayList<>();
        Map<Player,List<Config.Resource>> changes = new HashMap<>();

        if(!(dicethrow==7)) {

            for (Map.Entry<Point, Label> label : board.getLowerFieldLabel().entrySet()) {

                if (label.getValue().toString().equals(dice)) {

                    affectedFields.add(label.getKey());
                }
            }

            for(Player player : players) {
                List<Config.Resource> resources = new ArrayList<>();

                for(Point field : affectedFields) {

                    List<String> corners = board.getCornersOfField(field);

                    for(String corner : corners) {

                        if(corner.equals(player.getPlayerFaction().toString())){

                            player.addRescourceFromSettlement
                                    (board.getField(field).getResource());

                            resources.add(board.getField(field).getResource());
                            bank.removeBankResource(1,board.getField(field).getResource());

                        }

                        if(corner.equals(player.getPlayerFaction().toString().toUpperCase())){

                            player.addRescourceFromCity
                                    (board.getField(field).getResource());

                            resources.add(board.getField(field).getResource());
                            resources.add(board.getField(field).getResource());
                            bank.removeBankResource(2,board.getField(field).getResource());
                        }
                    }
                }
                changes.put(player,resources);
            }
        }

        else {
            changes.clear();
          stealResourcesFromPlayer();
        }

        return changes;
    }

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



    /**
     * This method builds a settlement at a specified position
     *
     * @param position the specified position for the settlement
     * @return
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
                && hasEnoughRessources(Config.Structure.SETTLEMENT.getCosts(),currentPlayer)
                && getCurrentPlayer().hasAvailableSettlements()
                && isValidCorner(position)
                && board.getNeighboursOfCorner(position).isEmpty()) {

            getCurrentPlayer().initializeMeeple(settlement);
            board.setCorner(position, settlement.toString());
            payWithRessources(Config.Structure.SETTLEMENT.getCosts(),currentPlayer);
            currentPlayer.setWinPoints(currentPlayer.getWinPoints() + 1);
            return true;
        } else {
            return false;
        }
    }


    /**
     * This method builds a road at a specicfied location
     *
     * @param roadStart the Point where the road starts
     * @param roadEnd   the Point where the road ends
     * @return true, if the road has been built
     */
    public boolean buildRoad(Point roadStart, Point roadEnd) {
        Player currentPlayer = getCurrentPlayer();
        Road road = new Road(currentPlayer.getPlayerFaction(),roadStart, roadEnd);

        if (hasValidConditionsForRoad(currentPlayer,roadStart,roadEnd)
                &&hasEnoughRessources(Config.Structure.ROAD.getCosts(),currentPlayer)) {

            getCurrentPlayer().initializeMeeple(road);
            board.setEdge(roadStart, roadEnd, road.toString());
            payWithRessources(Config.Structure.ROAD.getCosts(),currentPlayer);
            return true;
        } else {
            return false;
        }
    }

    /**
     * This Method build a city at a specified position 
     * 
     * @param position the specified position for the city
     * @return true, if the city has been built
     */
    public boolean buildCity(Point position) {
    	Player currentPlayer = getCurrentPlayer();
        City city = new City(getCurrentPlayer().getPlayerFaction());
        boolean hasSettlement = false;
         
        if(board.getCorner(position).equals(getCurrentPlayer().getPlayerFaction().toString())) {
        	hasSettlement = true;
        }
        if(hasSettlement 
        		&& hasEnoughRessources(Config.Structure.CITY.getCosts(), currentPlayer)
        		&& getCurrentPlayer().hasAvailableCities()) {

        	getCurrentPlayer().removeSettlement();
        	getCurrentPlayer().initializeMeeple(city);
        	board.setCorner(position, city.toString());
        	payWithRessources(Config.Structure.CITY.getCosts(), currentPlayer);
        	currentPlayer.setWinPoints(currentPlayer.getWinPoints() + 1);
    		return true;
    	} else {
    		return false;
    	}
    }

    private boolean hasValidConditionsForRoad(Player currentPlayer, Point roadStart, Point roadEnd) {
       return (board.hasEdge(roadStart, roadEnd) &&
                board.getEdge(roadStart, roadEnd) == null
                && (hasAdjacentElementsForRoad(currentPlayer, roadStart, roadEnd)
                ||hasAdjacentElementsForRoad(currentPlayer,roadEnd,roadStart)));
    }

    private boolean hasAdjacentElementsForRoad(Player currentPlayer, Point roadStart, Point roadEnd){

        boolean hasAdjacentElements = false;

        List<String> meeples = new ArrayList<>();
        for (Meeple meeple : currentPlayer.getMeepleList()) {
            meeples.add(meeple.toString());
        }

        for (int i = 0; i < meeples.size(); i++) {
            if (board.getAdjacentEdges(roadStart).contains(meeples.get(i))
                    || meeples.get(i).equals(board.getCorner(roadStart))
                    || meeples.get(i).toUpperCase().equals((board.getCorner(roadStart))))
            {
                if (board.getCorner(roadEnd) == null ||
                        meeples.contains(board.getCorner(roadEnd))) {
                    if (isValidCorner(roadEnd)) {
                        hasAdjacentElements = true;
                    }

                }
            }

        }

        return hasAdjacentElements;

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

    private boolean hasEnoughRessources(List<Config.Resource> list, Player currentPlayer) {

        boolean hasEnoughRessources = true;
        for (Resource resource : list) {

            if (getCurrentPlayer().getNumberOfSingleResource(resource) < 1) {
                hasEnoughRessources = false;
            }
        }

        return hasEnoughRessources;

    }

    private void payWithRessources(List<Config.Resource> list, Player currentPlayer){

        for (Resource resource : list) {

            getCurrentPlayer().removeResource(1, resource);
            bank.addBankResources(1,resource);
        }
    }

    /**
     * This method trade 4 offered resources to one wished resource.
     * @param offer The resource you offer 4 times.
     * @param want The resource you get if your offer is valid.
     * @return true if the trade went well, returns false if there wasn't enough resoureces offered.
     */
    public boolean tradeWithBankFourToOne(Resource offer, Resource want) {
        if(want != null
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

    private boolean hasEnoughResourcesToTrade(Resource offer)
    {
        if(offer == null) {return false;}

        int resourceCounter = 0;
        for(Resource resource : getCurrentPlayer().getResourceStock()) {
            if(resource.equals(offer)) {
                resourceCounter++;
            }
        }
        if(resourceCounter >= 4) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Searches for the Player who has won the game
     * @return 'Player' who has reached the max amount of points,
     * if there is no winner return 'null'
     */
    public Player getWinner() {
        Player winner = null;

        for (Player player : players) {

            if(player.getWinPoints() >= winPoints) {

                winner = player;
            }

        }

        return winner;
    }
    
    /**
     * The robber method is called when the number 7 is rolled. The method checks the number
     * of resources pro player. Players who have more than 7 resources will be robbed of half
     * the resources.
     * 
     * An example of the rounding system: A player with 9 resources must give 4 resources back.
     */
    private void stealResourcesFromPlayer() {
    	for(Player player : players) {
    		int numberOfResources = player.getNumberOfTotalResources();
    		if(numberOfResources > Config.MAX_CARDS_IN_HAND_NO_DROP) {
    			if(numberOfResources % 2 == 0) {  //even number
    				stealingResources(player, numberOfResources / 2);
    			}
    			else {	//uneven number
    				stealingResources(player, (numberOfResources - 1) / 2);
    			}
    		}
    	}
    }
    
    /**
     * This method performs the Robber's function. It removes a certain number of random resources 
     * from the player and add them back to the bank.
     * 
     * @param player The player who gets robbed.
     * @param numberOfResources Number of resources robbed
     */
    private void stealingResources(Player player, int numberOfResources) {
    	int index = 0;
    	for(int i = 0; i < numberOfResources ; i++) {
    		index = (int)(Math.random() * player.getNumberOfTotalResources());
    		bank.addBankResources(1, player.getResourceStock().get(index));
			player.getResourceStock().remove(index);
		}
    }


    private int getLongestRoad(Player player){
        int length = 1;

        //potential start nodes
        Set<Point> roadCorners = new HashSet<>();

        //fill in potential nodes
        for(Meeple meepple : player.getMeepleList()){

            if(meepple instanceof Road) {

                roadCorners.add(((Road) meepple).getRoadStart());
                roadCorners.add(((Road) meepple).getRoadEnd());
            }
        }


        //map of visited roads;


        Map<Integer,Integer> visitedRoads = new HashMap<>();

        for(Point corner : roadCorners) {
           visitedRoads.clear();

        }


    return 0;

        }

        private void searchForLongesRoad(Point corner, Player player, int currentLength){

            for(String road : board.getAdjacentEdges(corner)) {

                if(road.equals(player.getPlayerFaction().toString().substring(0, 1))) {

                }
            }


        }


}
