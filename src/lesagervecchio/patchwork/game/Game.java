package lesagervecchio.patchwork.game;

import java.util.List;
import java.util.Objects;

import lesagervecchio.patchwork.global.GlobalBoard;
import lesagervecchio.patchwork.global.GlobalPatches;
import lesagervecchio.patchwork.player.Player;

public class Game {
// Une game est définit par une liste de ces joueurs,
// par sa globaleBoard, ca globalPatches.
	private static List<Player> listPlayer;
	private GlobalPatches globalPatches;
	private GlobalBoard globalBoard;
	
	public Game(String player1, String player2) {
		Objects.requireNonNull(player1);
		Objects.requireNonNull(player2);
		listPlayer = List.of(new Player(player1, 5, 0, true), new Player(player2, 5, 0, false));
		this.globalPatches = new GlobalPatches();
		this.globalBoard = new GlobalBoard();
	}
	
}
