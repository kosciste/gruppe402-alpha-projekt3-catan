package ch.zhaw.catan;

import ch.zhaw.catan.Config.Faction;
import ch.zhaw.catan.Config.Resource;

import java.awt.Point;
import java.util.List;
import java.util.Map;

public class SiedlerGame {

  SiedlerBoard board = new SiedlerBoard();
  private int winPoints;
  private int players;


  public SiedlerGame(int winPoints, int players) {
    // TODO: Implement
  }

  public void switchToNextPlayer() {
    // TODO: Implement
  }

  public void switchToPreviousPlayer() {
    // TODO: Implement
  }

  public List<Faction> getPlayer() {
    // TODO: Implement
    return null;
  }

  public SiedlerBoard getBoard() {

    return board;
  }

  public Faction getCurrentPlayer() {
    // TODO: Implement
    return null;
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
