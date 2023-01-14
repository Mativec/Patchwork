package lesagervecchio.patchwork.display;

import lesagervecchio.patchwork.board.PlayerBoard;
import lesagervecchio.patchwork.global.GlobalPatches;
import lesagervecchio.patchwork.patch.Patch;
import lesagervecchio.patchwork.player.Player;

import java.util.ArrayList;

/**
 * @author Mativec (Matias VECCHIO)
 */
public sealed interface DisplayService permits TextualDisplay, GraphicalDisplay {

  /**
   * Draw a Patch on screen
   *
   * @param patch : Patch we want to show on screen.
   */
  void drawPatch(Patch patch);

  /**
   * @param board : the Player Board we want to show
   */
  void drawPlayerBoard(PlayerBoard board);


  /**
   * Method that display the board game
   *
   * @param players : a list of all the players in the game
   */
  void drawGlobalBoard(ArrayList<Player> players);


  /**
   * Method that display orderPatches.
   */
  void drawOrderPatches(GlobalPatches globalPatches);

  void drawText(Object... objects);

  String waitText();

  char waitInput();

  void close();
}
