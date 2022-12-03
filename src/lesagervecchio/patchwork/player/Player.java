package lesagervecchio.patchwork.player;

import java.util.Objects;

public record Player (String name, int jetons, int position, boolean onTop) {//record ou class?
	//Un joueur doit être définit par son nom, son nombre de jetons en main,
	//son plateau de joueur et sa position sur le plateau global.
	
	//private Board;
	
	//onTop est vrai si se situe au dessus d'un autre joueur, sinon faux
	
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
	
	public Player movePlayer(int movement, int positionOtherpPlayer) {
		int newPosition;
		boolean newOnTop;
		
		if(movement + position > 52) {
			newPosition = 52;
		}else {
			newPosition = movement + position;
		}
		if(positionOtherpPlayer == newPosition) {
			newOnTop = true;
		}else {
			newOnTop = false;
		}
		return new Player(name, jetons, newPosition, newOnTop);
	}
	
	public Player updateJetons(int upJetons) {
		int newJetons = jetons + upJetons;
		if(newJetons < 0) {
			return null;
		}
		return new Player(name, newJetons, position, onTop);
	}
}
