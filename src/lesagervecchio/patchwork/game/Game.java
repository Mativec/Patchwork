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
  // Une game est définit par une liste de ces joueurs, par sa globaleBoard, ca globalPatches.
  private ArrayList<Player> listPlayer;//Faire un objet de listplayer permettrait de faciliter les operation sur lui
  private final GlobalPatches globalPatches;
  private final GlobalBoard globalBoard;
  /* Tant que la valeur de cette variable reste a -1 aucun joueur n'a formé la tuile spécial,
  des que l'un d'entre eux l'a atteint, la variable prend la valeur du numero du joueur en question */
  private int theSpecialTile;
  private final DisplayService displayService;

  /**
   * Initialisation of a Game
   *
   * @param player1        : the name of the first player
   * @param player2        : the name of the second player
   * @param displayService : Display Service (Graphical or Textual)
   */
  public Game(String player1, String player2, DisplayService displayService, String nameDeck) {
    Objects.requireNonNull(player1, "player 1 is null");
    Objects.requireNonNull(player2, "player 2 is null");
    Objects.requireNonNull(displayService, "No display service chosen");
    Objects.requireNonNull(nameDeck, "name");

    this.displayService = displayService;
    this.globalPatches = new GlobalPatches(nameDeck, displayService);
    this.globalBoard = new GlobalBoard();
    listPlayer = new ArrayList<>();

    listPlayer.add(new Player(new PlayerBoard(), player1, 5, 0, true));
    listPlayer.add(new Player(new PlayerBoard(), player2, 5, 0, false));
    theSpecialTile = -1;
  }

  /**
   * Method that return the id of the player that is meant to be the next to play
   *
   * @return : the id of the player which it is the turn to play.
   */
  public int idPlayerTurn() {
    //Méthode renvoyant l'id du joueur devant jouer
    if (listPlayer.get(0).position() == listPlayer.get(1).position()) {
      if (listPlayer.get(0).position() >= GlobalBoard.size() - 1) {
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
    Player p1 = listPlayer.get(0);
    Player p2 = listPlayer.get(1);
    if (p1.position() == p2.position()) {
      if (top == 0) {
        newArray.add(new Player(p1.playerBoard(), p1.name(), p1.jetons(), p1.position(), true));
        newArray.add(new Player(p2.playerBoard(), p2.name(), p2.jetons(), p2.position(), false));
      } else {
        newArray.add(new Player(p1.playerBoard(), p1.name(), p1.jetons(), p1.position(), false));
        newArray.add(new Player(p2.playerBoard(), p2.name(), p2.jetons(), p2.position(), true));
      }
    } else {
      newArray.add(new Player(p1.playerBoard(), p1.name(), p1.jetons(), p1.position(), false));
      newArray.add(new Player(p2.playerBoard(), p2.name(), p2.jetons(), p2.position(), false));
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
      displayService.clearWindow();

      //Affichage patchs dans la liste des patchs avec la bonne méthodes.
      displayService.drawGlobalBoard(listPlayer);

      //displayService.moveCursor(50, 1000);
      displayService.moveCursor(10, 20);
      displayService.drawText(
        "C'est à " + listPlayer.get(joueur).name() + " de jouer.",
        "Que faites vous?\n",
        "1. Aller à la prochaine case bouton --> b",
        "2. Choisir un des patchs a mettre sur le plateau --> a / z / e",
        "3. Voir l'ensemble des patches --> q"
      );

      choix = displayService.waitInput();
      index = -1;
      switch (choix) {//Penser a mettre a jour les onTop a chaque deplacements
        case 'b' -> { // aller a la prochaine case
          verif = !verif;
          //listPlayer.set(joueur, listPlayer.get(joueur).movePlayer(globalBoard.nextCaseButton(listPlayer.get(joueur).position())));
          // Echange l'instance player d'index joueur par un player mise a jour par movePlayer,
          // incomplet par rapport a la nouvelle valeur du onTop de l'autre joueur (pas dans tous les cas, mais donc a verifier)
          if (joueur == 1)
            listPlayer.set(joueur, listPlayer.get(joueur).moveAndUpdate(globalPatches, globalBoard, (listPlayer.get(0).position() - listPlayer.get(1).position()) + 1));
          else
            listPlayer.set(joueur, listPlayer.get(joueur).moveAndUpdate(globalPatches, globalBoard, (listPlayer.get(1).position() - listPlayer.get(0).position()) + 1));
          listPlayer = updateListPlayer(joueur);
        }
        case 'q' -> {
          displayService.clearWindow();
          displayService.drawOrderPatches(globalPatches);
          displayService.waitInput();
        }
        case 'a' -> {// choix du patch 1
          if (globalPatches.checkPricePatch(listPlayer.get(joueur), 1)) {
        	displayService.clearWindow();
            index = 0;
            verif = !verif;
          }
        }
        case 'z' -> {// choix du patch 2
          if (globalPatches.checkPricePatch(listPlayer.get(joueur), 2)) {
          	displayService.clearWindow();
            index = 1;
            verif = !verif;
          }
        }
        case 'e' -> {// choix du patch 3
          if (globalPatches.checkPricePatch(listPlayer.get(joueur), 3)) {
          	displayService.clearWindow();
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
    if (listPlayer.get(joueur).playerBoard().hasBonusPatch()) {
      theSpecialTile = joueur;
    }
    return true;
  }

  /**
   * Method printing the score of the two players
   */
  public void scoreAnnouncement() {
    int scorePlayer1 = 0, scorePlayer2 = 0;
    displayService.clearWindow();
    displayService.moveCursor(-1, -1);
    switch (theSpecialTile) {
      case 0 -> {
        displayService.drawText("C'est " + listPlayer.get(0).name() + " qui a remporté la tuile !");
        scorePlayer1 += 7;
      }
      case 1 -> {
        displayService.drawText("C'est " + listPlayer.get(0).name() + " qui a remporté la tuile !");
        scorePlayer2 += 7;
      }
      default -> displayService.drawText("Personne n'a obtenue la tuile spéciale.");
    }

    displayService.moveCursor(10, 130);
    scorePlayer1 += listPlayer.get(0).jetons();
    scorePlayer2 += listPlayer.get(1).jetons();
    scorePlayer1 -= 2 * listPlayer.get(0).playerBoard().getNbSquare();
    scorePlayer2 -= 2 * listPlayer.get(1).playerBoard().getNbSquare();

    if (scorePlayer2 < scorePlayer1) {
      displayService.drawText("C'est " + listPlayer.get(0).name() + " qui remporte la partie !!!");
    } else if (scorePlayer2 > scorePlayer1) {
      displayService.drawText("C'est " + listPlayer.get(1).name() + " qui remporte la partie !!!");
    } else {
      displayService.drawText("Égalité, que des gagnants !!\n\n(Ou que des perdants...)");
    }
    displayService.waitInput();
  }
}
