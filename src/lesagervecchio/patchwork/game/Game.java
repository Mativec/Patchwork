package lesagervecchio.patchwork.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import lesagervecchio.patchwork.global.GlobalBoard;
import lesagervecchio.patchwork.global.GlobalPatches;
import lesagervecchio.patchwork.player.Player;

public class Game { //nommer l'instance patchwork?
// Une game est définit par une liste de ces joueurs,
// par sa globaleBoard, ca globalPatches.
	private ArrayList<Player> listPlayer;
	private GlobalPatches globalPatches;
	private GlobalBoard globalBoard;
	
	public Game(String player1, String player2) {
		Objects.requireNonNull(player1);
		Objects.requireNonNull(player2);
		listPlayer = new ArrayList<Player>();
		listPlayer.add(new Player(player1, 5, 0, true));
		listPlayer.add(new Player(player2, 5, 0, false));
		this.globalPatches = new GlobalPatches();
		this.globalBoard = new GlobalBoard();
	}
	
	public int idPlayerTurn() {
		//Méthode renvoyant l'id du joueur devant jouer
		if(listPlayer.get(0).position() == listPlayer.get(1).position()) {
			if(listPlayer.get(0).onTop() == true){
				return 0;
			}
			return 1;
		}
		if(listPlayer.get(0).position() > listPlayer.get(1).position()) {
			return 1;
		}
		return 0;
	}
	
	public ArrayList<Player> updateListPlayer(int top){
		//Méthode permettant de mettre a jour le onTop des joueurs dans listPayer
		//Si les deux players ont les même coordonnées, le topieme player dans listPlayer sera onTop
		var newArray = new ArrayList<Player>();
		if(listPlayer.get(0).position() == listPlayer.get(1).position()) {
			if(top == 0) {
				newArray.add(new Player(listPlayer.get(0).name(), listPlayer.get(0).jetons(), listPlayer.get(0).position(), true));
				newArray.add(new Player(listPlayer.get(1).name(), listPlayer.get(1).jetons(), listPlayer.get(1).position(), false));
			}else {
				newArray.add(new Player(listPlayer.get(0).name(), listPlayer.get(0).jetons(), listPlayer.get(0).position(), false));
				newArray.add(new Player(listPlayer.get(1).name(), listPlayer.get(1).jetons(), listPlayer.get(1).position(), true));
			}
		}else{
			newArray.add(new Player(listPlayer.get(0).name(), listPlayer.get(0).jetons(), listPlayer.get(0).position(), false));
			newArray.add(new Player(listPlayer.get(1).name(), listPlayer.get(1).jetons(), listPlayer.get(1).position(), false));
		}
		return newArray;
	}
	
	public void takeAction(int joueur) {
		var verif = false;
		while(!verif) {
			//Affichage patchs dans la liste des patchs avec la bonne méthodes.
			globalPatches.printOrderPatches();
			System.out.println("C'est à " + listPlayer.get(joueur).name() + " de jouer.\nQue faites vous?\n\n1. Aller à la prochaine case boutton --> b\n\n2. Choisir un des patchs a mettre sur le plateau --> 1 / 2 / 3");
			Scanner scan = new Scanner(System.in);
			char choix = scan.nextLine().charAt(0);
			switch(choix) {//Penser a mettre a jour les onTop a chaque deplacements
				case 'b' -> {	verif = !verif;
								listPlayer.set(joueur, listPlayer.get(joueur).movePlayer(globalBoard.nextCaseButton(listPlayer.get(joueur).position()), listPlayer.get(joueur).position()));//Echange l'instance player d'index joueur par un player mise a jour par movePlayer, incomplet par rapport a la nouvelle valeur du onTop de l'autre joueur (pas dans tous les cas, mais donc a verifier)
								listPlayer = updateListPlayer(joueur);
							}
				case '1' -> {	verif = !verif;
				
				}
				case '2' -> {	verif = !verif;
				
				}
				case '3' -> {	verif = !verif;
				
				}
				default -> System.out.println("L'action n'est pas valide.\n");
			}
		}
		
	}
	
}