package lesagervecchio.patchwork.main;

import lesagervecchio.patchwork.display.TextualDisplay;
import lesagervecchio.patchwork.game.Game;

public class Main {
  public static void main(String[] args) {
    //Gestion des arguments
    var display = new TextualDisplay();

    display.drawText("Le joueur1 commencera la partie.");
    display.drawText("Saisissez le nom du Joueur1.");
    String player1 = display.waitText();
    display.drawText("Saisissez le nom du Joueur2.");
    String player2 = display.waitText();

    //Début du jeu
    var partie = new Game(player1, player2, display);
    while (true) {
      if (!partie.takeAction(partie.idPlayerTurn())) break;
    }
    display.close();
  }
}
