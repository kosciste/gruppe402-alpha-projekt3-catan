package ch.zhaw.catan;

import ch.zhaw.catan.Config.Faction;
import ch.zhaw.catan.Config.Resource;

import java.awt.Point;
import java.util.*;

public class SiedlerGame {

  SiedlerBoard board = new SiedlerBoard();
  private int winPoints;
  private int numberOfPlayers;
  private List<Player> players = new ArrayList<>();

  private static int playerAtTurn = 0;

  public SiedlerGame(int winPoints, int numberOfPlayers) {
    this.winPoints = winPoints;
    this.numberOfPlayers = numberOfPlayers;
    for(int i = 0; i < numberOfPlayers; i++){

     Player player =  new Player(Faction.values()[i]);
     players.add(player);
    }
  }

  /**
   * This method switches to next player who needs to make a turn.
   */
  public void switchToNextPlayer() {
    if(playerAtTurn<4) {

      playerAtTurn++;
    }

    playerAtTurn = 0;
  }

  /**
   * This method switches to the previous player who needs to make a turn.
   */
  public void switchToPreviousPlayer() {
    if(playerAtTurn>0) {
      playerAtTurn--;

    }

    playerAtTurn = 4;
  }


  /**
   * This method returns a list with all players.
   * @return List with all players
   */
  public List<Player> getPlayer() {

    return players;
  }


  /**
   * This method returns the GameBoard
   * @return the GameBoard
   */
  public SiedlerBoard getBoard() {

    return board;
  }


  /**
   * Returns the current player who needs to make a turn.
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
   * @param position the specified position for the settlement
   * @return true, if the settlement has been built
   */
  public boolean placeInitialSettlement(Point position) {

  return buildSettlement(position);

  }

  /**
   * Builds an initial road
   * @param roadStart the Point where the road starts
   * @param roadEnd the Point where the road ends
   * @return true, if the road has been built
   */
  public boolean placeInitialRoad(Point roadStart, Point roadEnd) {

    return buildRoad(roadStart,roadEnd);

  }


  public Map<Faction, List<Resource>> throwDice(int dicethrow) {
    // TODO: Implement
    return null;
  }


  /**
   * This method builds a settlement at a specified position
   * @param position the specified position for the settlement
   * @return
   */
  public boolean buildSettlement(Point position) {

    //TODO: Überprüfung ob die Strasse des Player an Corner grenzt

    Settlement settlement = new Settlement(getCurrentPlayer().getPlayerFaction());

      if(board.hasCorner((position))&&board.getCorner(position) == null && board.getAdjacentEdges(position)!=null
              && getCurrentPlayer().hasAvailableSettlements()) {

        getCurrentPlayer().initializeMeeple(settlement);

        board.setCorner(position,settlement.toString());
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
   * @param roadStart the Point where the road starts
   * @param roadEnd the Point where the road ends
   * @return true, if the road has been built
   */
  public boolean buildRoad(Point roadStart, Point roadEnd) {

    Road road = new Road(getCurrentPlayer().getPlayerFaction());

  if(board.hasEdge(roadStart,roadEnd)&&board.getEdge(roadStart, roadEnd)==null)  {

    board.setEdge(roadStart, roadEnd, road.toString());
    return true;
  }

  else {
    return false;
  }


  }

  public boolean tradeWithBankFourToOne(Resource offer, Resource want) {
    // TODO: Implement
    return false;
  }

  public Faction getWinner() {
    // TODO: Implement
    return null;
  }
}
