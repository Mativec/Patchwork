package lesagervecchio.patchwork.game;

import lesagervecchio.patchwork.board.PlayerBoard;
import lesagervecchio.patchwork.display.DisplayService;
import lesagervecchio.patchwork.global.GlobalBoard;
import lesagervecchio.patchwork.global.GlobalPatches;
import lesagervecchio.patchwork.player.Player;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Class about a game and its components
 *
 * @author Paul LE SAGER
 */

public class Game { //nommer l'instance patchwork?
  // Une game est définit par une liste de ces joueurs,
// par sa globaleBoard, ca globalPatches.
  private ArrayList<Player> listPlayer;//Faire un objet de listplayer permettrait de faciliter les operation sur lui
  private final GlobalPatches globalPatches;
  private final GlobalBoard globalBoard;
  private final DisplayService displayService;

  /**
   * Initialisation of a Game
   *
   * @param player1        : the name of the first player
   * @param player2        : the name of the second player
   * @param displayService : Display Service (Graphical or Textual)
   */
  public Game(String player1, String player2, DisplayService displayService) {
    Objects.requireNonNull(player1, "player 1 is null");
    Objects.requireNonNull(player2, "player 2 is null");
    Objects.requireNonNull(displayService, "No display service chosen");

    this.displayService = displayService;
    this.globalPatches = new GlobalPatches("stockage_patchs", displayService);
    this.globalBoard = new GlobalBoard();
    listPlayer = new ArrayList<>();

    listPlayer.add(new Player(new PlayerBoard(9, 9), player1, 5, 0, true));
    listPlayer.add(new Player(new PlayerBoard(9, 9), player2, 5, 0, false));
  }

  /**
   * Method that return the id of the player that is meant to be the next to play
   *
   * @return : the id of the player which it is the turn to play.
   */
  public int idPlayerTurn() {
    //Méthode renvoyant l'id du joueur devant jouer
    if (listPlayer.get(0).position() == listPlayer.get(1).position()) {
      if (listPlayer.get(0).position() >= 52) {
        return -1;
      }
      if (listPlayer.get(0).onTop()) {
        return 0;
      }
      return 1;
    }
    if (listPlayer.get(0).position() > listPlayer.get(1).position()) {
      return 1;
    }
    return 0;
  }

  /**
   * Method that update the onTop parameter of all players in a list of player
   *
   * @param top : if one of the player in on top of the other, it would the one with the index top
   * @return : the new version of the listPlayer
   */
  public ArrayList<Player> updateListPlayer(int top) {
    //Méthode permettant de mettre a jour le onTop des joueurs dans listPayer
    //Si les deux players ont les même coordonnées, le topieme player dans listPlayer sera onTop
    var newArray = new ArrayList<Player>();
    if (listPlayer.get(0).position() == listPlayer.get(1).position()) {
      if (top == 0) {
        newArray.add(new Player(listPlayer.get(0).playerBoard(), listPlayer.get(0).name(), listPlayer.get(0).jetons(), listPlayer.get(0).position(), true));
        newArray.add(new Player(listPlayer.get(1).playerBoard(), listPlayer.get(1).name(), listPlayer.get(1).jetons(), listPlayer.get(1).position(), false));
      } else {
        newArray.add(new Player(listPlayer.get(0).playerBoard(), listPlayer.get(0).name(), listPlayer.get(0).jetons(), listPlayer.get(0).position(), false));
        newArray.add(new Player(listPlayer.get(1).playerBoard(), listPlayer.get(1).name(), listPlayer.get(1).jetons(), listPlayer.get(1).position(), true));
      }
    } else {
      newArray.add(new Player(listPlayer.get(0).playerBoard(), listPlayer.get(0).name(), listPlayer.get(0).jetons(), listPlayer.get(0).position(), false));
      newArray.add(new Player(listPlayer.get(1).playerBoard(), listPlayer.get(1).name(), listPlayer.get(1).jetons(), listPlayer.get(1).position(), false));
    }
    return newArray;
  }

  /**
   * Method that simulate all the action that a player can take
   *
   * @param joueur : the index of the player that is meant to play
   * @return : return true while the game is still going, return false if else.
   */
  public boolean takeAction(int joueur) {
    char choix;
    int index;
    boolean verif;

    if (joueur == -1) {
      return false;
    }
    verif = false;
    while (!verif) {
      //Affichage patchs dans la liste des patchs avec la bonne méthodes.
      displayService.drawOrderPatches(globalPatches);
      displayService.drawGlobalBoard(listPlayer);
      displayService.drawText(
        "C'est à " + listPlayer.get(joueur).name() + " de jouer.",
        "Que faites vous?\n",
        "1. Aller à la prochaine case boutton --> b",
        "2. Choisir un des patchs a mettre sur le plateau --> 1 / 2 / 3"
      );
      choix = displayService.waitInput();
      index = -1;
      switch (choix) {//Penser a mettre a jour les onTop a chaque deplacements
        case 'b' -> {
          verif = !verif;
          //Echange l'instance player d'index joueur par un player mise a jour par movePlayer,
          // incomplet par rapport a la nouvelle valeur du onTop de l'autre joueur (pas dans tous les cas, mais donc a verifier)
          listPlayer.set(
            joueur, listPlayer.get(joueur).movePlayer(globalBoard.nextCaseButton(listPlayer.get(joueur).position()))
          );
        }
        case '1' -> {
          if (globalPatches.checkPricePatch(listPlayer.get(joueur), 1)) {
            index = 0;
            verif = !verif;
          }
        }
        case '2' -> {
          if (globalPatches.checkPricePatch(listPlayer.get(joueur), 2)) {
            index = 1;
            verif = !verif;
          }
        }
        case '3' -> {
          if (globalPatches.checkPricePatch(listPlayer.get(joueur), 3)) {
            index = 2;
            verif = !verif;
          }
        }
        default -> System.err.println("L'action n'est pas valide.\n");
      }
      if (index != -1) {
        listPlayer.set(joueur, globalPatches.buyPatch(listPlayer.get(joueur), index));
      }
      listPlayer = updateListPlayer(joueur);
    }
    return true;
  }
}
