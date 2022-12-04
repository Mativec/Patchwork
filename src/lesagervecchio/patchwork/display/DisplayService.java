package lesagervecchio.patchwork.display;

import lesagervecchio.patchwork.board.PlayerBoard;
import lesagervecchio.patchwork.patch.Patch;


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
}
