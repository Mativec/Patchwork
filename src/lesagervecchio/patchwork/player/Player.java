package lesagervecchio.patchwork.player;

import java.util.Objects;

/**
 * Record about a player and his component
 * @author Paul LE SAGER
 * @author Mativec (Matias VECCHIO)
 */

public record Player (String name, int jetons, int position, boolean onTop) {//record ou class?
	//Un joueur doit être définit par son nom, son nombre de jetons en main,
	//son plateau de joueur et sa position sur le plateau global.
	
	//private Board;
	
	//onTop est vrai si se situe au dessus d'un autre joueur, sinon faux
	
	/**
	 * Initialization of a player
	 * @param name : a string that represente the player name
	 * @param jetons : the number of buttonCost that the player own
	 * @param position : the place of the player on the globalBoard
	 * @param onTop : allow to know, in the case of multiple player being at the same position, to know wich one in on top of the other.
	 */
	public Player {
		Objects.requireNonNull(name);
		if(jetons < 0) {
			throw new IllegalArgumentException("jetons < 0");
		}
		if(position < 0 || position > 39) {
			throw new IllegalArgumentException("position < 0 or > 39");
		}
		//var board = new Board();
	}
	
	/**
	 * Method that simulate a move from a player by changing his location.
	 * @param movement : the number of cases that the player cross
	 * @return : the new player
	 */
	public Player movePlayer(int movement) {
		int newPosition;
		
		if(movement + position > 52) {
			newPosition = 52;
		}else {
			newPosition = movement + position;
		}
		return new Player(name, jetons, newPosition, onTop());
	}
	
	/**
	 * Method that change the amount of buttonCost that a player own.
	 * @param upJetons : the number of buttonCost to change
	 * @return : the new Player
	 */
	public Player updateJetons(int upJetons) {
		int newJetons = jetons + upJetons;
		if(newJetons < 0) {
			return null;
		}
		return new Player(name, newJetons, position, onTop);
	}
}
