package lesagervecchio.patchwork.display;

import lesagervecchio.patchwork.patch.Patch;

import lesagervecchio.patchwork.board.PlayerBoard;

public sealed interface DisplayService permits TextualDisplay, GraphicalDisplay{
  /**
   * Draw a Patch on screen
   * @param patch : Patch
   */
  void drawPatch(Patch patch);


  /**
   *
   * @param board : a Player Board
   */
  void drawPlayerBoard(PlayerBoard board);

}
