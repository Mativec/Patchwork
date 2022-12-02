package lesagervecchio.patchwork.global;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import lesagervecchio.patchwork.player.Player;

public class GlobalBoard {
	// Le plateau global est composé d'un tableau de int représentant les bouttons du plateau
	// Le même type de tableau sera implémenté pour les patches 1*1.
	// Composé également d'un int représentant le nombre de cases.(case finale comprise)
	// Implémentation dans ce record des patch classiques?
	// Si oui, ajout d'un ArrayList d'id de piece.
	// Sinon, est ce vraiment necessaire de faire un record juste pour ca?
	
	private static List<Integer> buttons;
	private static int numberCases;
	
	public GlobalBoard (){
			numberCases = 53;
			buttons = List.of(5, 11, 17, 23, 29, 35, 41, 47, 53);
	}
	
	public boolean isMoveButtonable(Player player, int move) {
		//Renvoie true si avec ce move le joueur va passer un boutton, false sinon		
		
		//stream retournant le premier boutton du plateau depassant la pos du joueur. Vaut -1 sinon.
		int postButton = buttons.stream().filter(u -> u > player.position()).findFirst().orElseGet(() -> -1);
		if(postButton == -1) {
			return false;
		}
		return (player.position() >= postButton);
	}
	
	public int idPlayerTurn(List <Player> players) {
		//Méthode renvoyant l'id du joueur devant jouer
		if(players.get(0).position() == players.get(1).position()) {
			if(players.get(0).onTop() == true){
				return 0;
			}
			return 1;
		}
		if(players.get(0).position() > players.get(1).position()) {
			return 1;
		}
		return 0;
	}
}
