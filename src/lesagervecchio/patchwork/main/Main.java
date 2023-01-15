package lesagervecchio.patchwork.main;

import fr.umlv.zen5.Application;
import fr.umlv.zen5.ScreenInfo;
import lesagervecchio.patchwork.display.DisplayService;
import lesagervecchio.patchwork.display.GraphicalDisplay;
import lesagervecchio.patchwork.display.TextualDisplay;
import lesagervecchio.patchwork.game.Game;

public class Main {
  public static void main(String[] args) {
    //Gestion des arguments
    DisplayService displayService;
    String message;
    int fontSize = 25;
    float x = 50;
    float y = 50;

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
    displayService.setupText("SansSerif", fontSize);
    message = "Saisissez le nom du Joueur1.";
    displayService.moveCursor(x, y);
    y += 20;
    displayService.drawText(
      "Le joueur1 commencera la partie.",
      message
    );
    displayService.moveCursor(x + message.length() * (fontSize/2), y);
    y += 20;
    String player1 = displayService.waitText();

    message = "Saisissez le nom du Joueur2.";
    displayService.moveCursor(50, y);
    displayService.drawText(message);

    displayService.moveCursor(55 + message.length() * (fontSize/2), y);
    String player2 = displayService.waitText();

    displayService.clearWindow();

    //Début du jeu
    var partie = new Game(player1, player2, displayService);
    while (true) {
      if (!partie.takeAction(partie.idPlayerTurn())) break;
    }
    partie.scoreAnnouncement();
    displayService.close();
  }
}
