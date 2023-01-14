package lesagervecchio.patchwork.main;

import lesagervecchio.patchwork.display.DisplayService;
import lesagervecchio.patchwork.display.GraphicalDisplay;
import lesagervecchio.patchwork.display.TextualDisplay;
import lesagervecchio.patchwork.game.Game;

public class Main {
  public static void main(String[] args) {
    //Gestion des arguments
    DisplayService displayService;

    if(args.length != 0 && (args[0].equals("-c") || args[0].equals("--console"))){
      displayService = new TextualDisplay();
    }
    else if(args.length != 0 && (args[0].equals("-g") || args[0].equals("--graph"))){
      displayService = new GraphicalDisplay();
    }
    else {
      System.out.println("*".repeat(8));
      System.out.println("Patchwork");
      System.out.println("*".repeat(8));
      System.out.println("Pour jouer, merci de relancer ce .jar suivis de :");
      System.out.println("\t-g pour un affichage graphique.");
      System.out.println("\t-c pour un affichage textuel sur la console.");
      return;
    }

    displayService.drawText("Le joueur1 commencera la partie.");

    displayService.drawText("Saisissez le nom du Joueur1.");
    String player1 = displayService.waitText();

    displayService.drawText("Saisissez le nom du Joueur2.");
    String player2 = displayService.waitText();

    //Début du jeu
    var partie = new Game(player1, player2, displayService);
    while (true) {
      if (!partie.takeAction(partie.idPlayerTurn())) break;
    }
    displayService.close();
  }
}
