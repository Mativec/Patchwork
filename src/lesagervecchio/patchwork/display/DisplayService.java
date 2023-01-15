package lesagervecchio.patchwork.display;

import lesagervecchio.patchwork.board.PlayerBoard;
import lesagervecchio.patchwork.global.GlobalBoard;
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
  void drawPatch(Patch patch, float tailleCase);

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

  /**
   * Set up the font for text draw, needed by drawTest and waitText
   * @param FontName : Name of the font
   * @param fontSize : Size of the font
   */
  default void setupText(String FontName, int fontSize){}

  /**
   * Draw the text in parameter where the cursor is, one parameter per line.
   * @param text : The text to draw.
   */
  void drawText(String... text);

  /**
   * Wait for a text to be entered by the player
   *
   * @return
   */
  String waitText();

  /**
   * Wait for an input to be pressed
   *
   * @return : input pressed as a char.
   */
  char waitInput();

  /**
   * Move the cursor for the next to operate at a precise point of the screen
   * (0, 0) based on corner N-W
   * -1 on a coordinate to move the cursor at the center on this coordinate
   * @param x : horizontal coordinate wanted for the cursor
   * @param y : vertical coordinate wanted for the cursor
   */
  default void moveCursor(float x, float y){}

  /**
   * Clear the window
   */
  default void clearWindow(){}

  /**
   * Close the service.
   */
  void close();
}
