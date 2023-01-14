package lesagervecchio.patchwork.player;

import java.util.Objects;

import lesagervecchio.patchwork.board.PlayerBoard;
import lesagervecchio.patchwork.global.GlobalBoard;
import lesagervecchio.patchwork.global.GlobalPatches;

/**
 * Record about a player and his component
 *
 * @author Paul LE SAGER
 */

public record Player(PlayerBoard playerBoard, String name, int jetons, int position, boolean onTop) {//record ou class?
  //Un joueur doit être définit par son nom, son nombre de jetons en main,
  //son plateau de joueur et sa position sur le plateau global.

  //private Board;

  //onTop est vrai si se situe au dessus d'un autre joueur, sinon faux

  /**
   * Initialization of a player
   *
   * @param playerBoard : the personal board of this player
   * @param name        : a string that represente the player name
   * @param jetons      : the number of buttonCost that the player own
   * @param position    : the place of the player on the globalBoard
   * @param onTop       : allow to know, in the case of multiple player being at the same position, to know wich one in on top of the other.
   */
  public Player {
    Objects.requireNonNull(name);
    if (jetons < 0) {
      throw new IllegalArgumentException("jetons < 0");
    }
    if (position < 0 || position > maxSize()) {
      throw new IllegalArgumentException("position < 0 or > " + maxSize());
    }
    Objects.requireNonNull(playerBoard);
    //var board = new Board();
  }

  /**
   * Method using movePlayer and other methods to update the position of the player, but also to update other attributes changed by the move.
   * 
   * @param movement : the number of cases that the player cross
   * @return : the new player
   */
  public Player moveAndUpdate(GlobalPatches globalPatches, GlobalBoard globalBoard, int movement) {
	  Player newPlayer = this;
	  int specialPatches = globalBoard.isMoveSpecialPatchable(this, movement);
	  int buttons = globalBoard.isMoveButtonable(this, movement);
	  int changeJetons;
	  
	  if(specialPatches != 0) {
		  for(var i = 0; i < specialPatches; i ++) {
			  newPlayer = globalPatches.buyPatch(newPlayer, -1);
		  }
	  }
		  
	  if(buttons != 0) {
		  for(var i = 0; i < buttons; i ++) {
			  changeJetons = newPlayer.playerBoard.getNumberBonusButtons();
			  newPlayer = new Player(newPlayer.playerBoard(), newPlayer.name(), newPlayer.jetons() + changeJetons, newPlayer.position(), newPlayer.onTop());
		  }
	  }		  
	  
	  return newPlayer.movePlayer(movement);
  }
  
  /**
   * Method that simulate a move from a player by changing his location.
   *
   * @param movement : the number of cases that the player cross
   * @return : the new player
   */
  public Player movePlayer(int movement) {
    return new Player(playerBoard, name, jetons, Math.min(movement + position, maxSize()), onTop());
  }

  /**
   * Method that change the amount of buttonCost that a player own.
   *
   * @param upJetons : the number of buttonCost to change
   * @return : the new Player
   */
  public Player updateJetons(int upJetons) {
    int newJetons = jetons + upJetons;
    if (newJetons < 0) {
      throw new IllegalArgumentException("jetons + upJetons de " + name + "< 0");
    }
    return new Player(playerBoard, name, newJetons, position, onTop);
  }

  private static int maxSize() {
    return 52;
  }
}
