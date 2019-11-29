package ch.zhaw.catan;

import ch.zhaw.catan.Config.Faction;
import ch.zhaw.catan.Config.Resource;

import java.awt.Point;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

public class SiedlerGame {

  SiedlerBoard board = new SiedlerBoard();
  private int winPoints;
  private int numberOfPlayers;
  private List<Faction> players = new ArrayList<Faction>();

  private static int playerAtTurn = 0;

  public SiedlerGame(int winPoints, int numberOfPlayers) {
    this.winPoints = winPoints;
    this.numberOfPlayers = numberOfPlayers;
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
  public List<Faction> getPlayer() {
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
  public Faction getCurrentPlayer() {
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

    if (board.hasCorner(position) && board.getAdjacentEdges(position)!=null && board.getCorner(position) == null) {

      board.setCorner(position,null);
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

  if(board.hasEdge(roadStart,roadEnd)&&board.getEdge(roadStart, roadEnd)==null)  {

    board.setEdge(roadStart, roadEnd, null);
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
