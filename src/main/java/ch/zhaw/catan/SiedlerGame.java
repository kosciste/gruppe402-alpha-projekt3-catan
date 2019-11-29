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

  public void switchToNextPlayer() {
    if(playerAtTurn<4) {

      playerAtTurn++;
    }

    playerAtTurn = 0;
  }

  public void switchToPreviousPlayer() {
    if(playerAtTurn>0) {
      playerAtTurn--;

    }

    playerAtTurn = 4;
  }

  public List<Faction> getPlayer() {
    return players;
  }

  public SiedlerBoard getBoard() {

    return board;
  }

  public Faction getCurrentPlayer() {
   return players.get(playerAtTurn);
  }

  public int getCurrentPlayerResourceStock(Resource resource) {
    // TODO: Implement
    return 0;
  }

  public boolean placeInitialSettlement(Point position, boolean payout) {
    board.setCorner(position, null);
    if(board.getCorner(position)!=null){

      return true;
    }

    else {

      return false;
    }

  }

  public boolean placeInitialRoad(Point roadStart, Point roadEnd) {
    board.setEdge(roadStart, roadEnd, null);

    if(board.getEdge(roadStart,roadEnd)!=null) {

      return true;
    }

    else {
      return false;
    }

  }

  public Map<Faction, List<Resource>> throwDice(int dicethrow) {
    // TODO: Implement
    return null;
  }


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
