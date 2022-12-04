package lesagervecchio.patchwork.main;

import lesagervecchio.patchwork.game.Game;

import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		System.out.println("Hello world !");
		Scanner scan = new Scanner(System.in);
		System.out.println("Le joueur1 commencera la partie.");
		System.out.println("Saisissez le nom du Joueur1.");
		String joueur1 = scan.nextLine();
		System.out.println("Saisissez le nom du Joueur2.");
		String joueur2 = scan.nextLine();
		
		Game partie = new Game(joueur1, joueur2);
		
		//var display = new TextualDisplay();
		//var board = new PlayerBoard(9, 9);

		//display.drawPlayerBoard(board);
		//System.out.println(board.getNbSquare());

		while(partie.takeAction(partie.idPlayerTurn())) {}	
		
		/*

		System.out.println(partie.idPlayerTurn());
	
		partie.takeAction(partie.idPlayerTurn());

		partie.takeAction(partie.idPlayerTurn());

		partie.takeAction(partie.idPlayerTurn());
		 */
		scan.close();
	}
}
