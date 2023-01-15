package lesagervecchio.patchwork.global;

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
  private static List<Integer> sPatches;
  //private static int numberCases;

  /**
   * Initialization of the board game
   */
  public GlobalBoard() {
    //numberCases = 53;
    buttons = List.of(4, 10, 16, 22, 28, 34, 40, 46, 52);
    sPatches = List.of(19, 25, 31, 43, 49);
  }

  public static List<Integer> getButtons() {
    return buttons;
  }

  public static List<Integer> getSpecialPatches() {
    return sPatches;
  }

  /**
   * Method that return the number of buttons that a player will collect if he make that move
   *
   * @param player : the player that will make the move
   * @param move   : the number of cases that the player will pass
   * @return : the number of buttons
   */
  public int isMoveButtonable(Player player, int move) {
    //Renvoie true si avec ce move le joueur va passer un boutton, false sinon
    //stream retournant le premier boutton du plateau depassant la pos du joueur. Vaut -1 sinon.
    return (int) buttons.stream().filter(u -> u > player.position()).count();
  }

  /**
   * Method that return the number of SepcialPatches that a player will collect if he make that move
   *
   * @param player : the player that will make the move
   * @param move   : the number of cases that the player will pass
   * @return : the number of special patches
   */
  public int isMoveSpecialPatchable(Player player, int move) {
    return (int) sPatches.stream().filter(u -> u > player.position()).count();
  }

  /**
   * Method that return, for a specific position on the board, the position of the next case button
   *
   * @param pos : a position on the board game
   * @return the position of the next button case
   */
  public int nextCaseButton(int pos) {
    //Fonction renvoyant, en fonction de la position d'un joueur, la position de la prochaine case bouton
    return buttons.stream().filter(u -> u > pos).findFirst().orElse(GlobalBoard.size());
  }

  public static Integer size() {
    return 54;
  }
}
