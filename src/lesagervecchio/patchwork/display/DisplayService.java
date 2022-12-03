package lesagervecchio.patchwork.display;

import lesagervecchio.patchwork.patch.Patch;

import lesagervecchio.patchwork.board.PlayerBoard;

public interface DisplayService {
  /**
   * Init the Service (point (0, 0) is on N-W corner)
   */
  void init();

  /**
   * Draw a Patch on screen
   * @param patch : Patch
   * @param x : starting point of the drawing
   * @param y : starting point of the drawing
   */
  void drawPatch(Patch patch, int x, int y);


  /**
   *
   * @param board : a Player Board
   * @param x : starting point of the drawing
   * @param y : starting point of the drawing
   */
  void drawPlayerBoard(PlayerBoard board, int x, int y);

  /**
   * Refresh the screen
   */
  void refresh();
}
