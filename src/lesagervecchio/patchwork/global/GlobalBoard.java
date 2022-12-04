package lesagervecchio.patchwork.global;

import java.util.ArrayList;

import java.util.List;

import lesagervecchio.patchwork.player.Player;

/**
 * Class about the board game and its components
 *
 * @author Paul LE SAGER
 */

public class GlobalBoard {
  // Le plateau global est composé d'un tableau de int représentant les bouttons du plateau
  // Le même type de tableau sera implémenté pour les patches 1*1.
  // Composé également d'un int représentant le nombre de cases.(case finale comprise)
  // Implémentation dans ce record des patch classiques?
  // Si oui, ajout d'un ArrayList d'id de piece.
  // Sinon, est ce vraiment necessaire de faire un record juste pour ca?

  private static List<Integer> buttons;
  //private static int numberCases;

  /**
   * Initialization of the board game
   */
  public GlobalBoard() {
    //numberCases = 53;
    buttons = List.of(4, 10, 16, 22, 28, 34, 40, 46, 52);
  }

  /**
   * Method that determine if a move make by a player will make him pass a button case
   *
   * @param player : the player that will make the move
   * @param move   : the number of cases that the player will pass
   * @return : false if the player will pass a button case, and true if else
   */
  public boolean isMoveButtonable(Player player, int move) {
    //Renvoie true si avec ce move le joueur va passer un boutton, false sinon

    //stream retournant le premier boutton du plateau depassant la pos du joueur. Vaut -1 sinon.
    int postButton = buttons.stream().filter(u -> u > player.position()).findFirst().orElse(-1);
    if (postButton == -1) {
      return false;
    }
    return (player.position() + move >= postButton);
  }

  /**
   * Method that display the board game
   *
   * @param players : a list of all the players in the game
   */
  public void printGlobalBoard(ArrayList<Player> players) {
    var builder = new StringBuilder();
    builder.append("0|");
    if (players.get(0).position() <= players.get(1).position()) {
      builder.append("J1->").append(players.get(0).position());
      builder.append("|J2->").append(players.get(1).position());
    } else {
      builder.append("J2->").append(players.get(1).position());
      builder.append("|J1->").append(players.get(0).position());
    }
    builder.append("|53\n");
    System.out.println(builder);
  }

  /**
   * Method that return, for a specific position on the board, the position of the next case button
   *
   * @param pos : a position on the board game
   * @return the position of the next button case
   */
  public int nextCaseButton(int pos) {
    //Fonction renvoyant, en fonction de la position d'un joueur, la position de la prochaine
    //case boutton
    return buttons.stream().filter(u -> u > pos).findFirst().orElse(53);
  }
}