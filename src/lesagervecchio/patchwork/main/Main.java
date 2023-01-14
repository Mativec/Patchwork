package lesagervecchio.patchwork.main;

import lesagervecchio.patchwork.game.Game;

import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Scanner scan = new Scanner(System.in);
    System.out.println("Le joueur1 commencera la partie.");
    System.out.println("Saisissez le nom du Joueur1.");
    String joueur1 = scan.nextLine();
    System.out.println("Saisissez le nom du Joueur2.");
    String joueur2 = scan.nextLine();

    Game partie = new Game(joueur1, joueur2);

    while (true) {
      if (!partie.takeAction(partie.idPlayerTurn())) break;
    }
    scan.close();
    partie.scoreAnnouncement();
  }
}
