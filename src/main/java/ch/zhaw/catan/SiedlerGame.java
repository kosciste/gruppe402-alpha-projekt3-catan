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


        Player currentPlayer = getCurrentPlayer();
        Road road = new Road(currentPlayer.getPlayerFaction());

        if (hasValidConditionsForRoad(currentPlayer,roadStart,roadEnd)) {
            getCurrentPlayer().initializeMeeple(road);
            board.setEdge(roadStart, roadEnd, road.toString());

            return true;
        } else {
            return false;
        }

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

                                    player.addRescourceFromSettlement
                                            (board.getField(affectedFields.get(j)).getResource());
                                    System.out.println(player.getResourceStock().get(0).toString());
                                }
                    }
                }
            }
        }

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
        //TODO: Überprüfen und abziehen der Rohstoffe
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
        Road road = new Road(currentPlayer.getPlayerFaction());

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




    public boolean buildCity(Point position) {
        // TODO: OPTIONAL task - Implement
        City city = new City(getCurrentPlayer().getPlayerFaction());
        boolean hasSettlement = false;
        List<Meeple> meeples = getCurrentPlayer().getMeepleList();
//        Iterator<Meeple> it = meeples.iterator();	//suche nach Siedlung mit den selben Position 
//        while(it.hasNext()) {		
//        	Meeple m1 = it.next();
//        	if(m1 instanceof Settlement) {
//        		if(position ueberpruefen) {
        			
//        			hasSettlement = true;
//        		}
//       	}
//        }
        	
    	
        
        boolean hasEnoughResources = false;
        // ressourcen überprüfen
        
        if(hasSettlement 
        		&& hasEnoughResources
        		&& getCurrentPlayer().hasAvailableCities()) {
        	//removeSettlement braucht noch methode in player
        	
        	getCurrentPlayer().initializeMeeple(city);
        	
        	//removeResources
        	
        	//changeWinPoints
        
    		return true;
    	}
    	else {
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
