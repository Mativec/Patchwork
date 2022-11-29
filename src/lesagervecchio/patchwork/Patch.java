package lesagervecchio.patchwork;

import java.util.*;

public class Patch {
  private static final Map<Integer, Patch> patchList = new HashMap<>();

  public static Patch of(int id) {
    return patchList.get(id);
  }

  /***************************************************************************/
  private final List<Integer[]> squares;
  private final int buttons;
  private final int turns;

  public Patch(int id, List<Integer[]> squares, int buttons, int turns) {
    Objects.requireNonNull(squares, "squares is null");
    this.squares = squares;

    if (buttons < 0) {
      throw new IllegalArgumentException("buttons < 0");
    }
    this.buttons = buttons;

    if (turns < 0) {
      throw new IllegalArgumentException("turns < 0");
    }
    this.turns = turns;

    patchList.put(id, this);
  }

  /**
   * Get coordinates(x, y) of the squares which form the patch.
   * @return : a list of the squares.
   */
  public List<Integer[]> getSquares() { return squares; }

  /**
   * Get the amoung of button required to buy this patch.
   * @return : int
   */
  public int getButtons() { return buttons; }

  /**
   * Get the amoung of turn to play to buy this patch.
   * @return : int
   */
  public int getTurns() { return turns; }

  @Override
  public String toString() {
    var output = new StringBuilder();

    output.append("Patch : ");

    output.append("[");
    squares.forEach(square -> output.append("(").append(square[0]).append(",").append(square[1]).append(")"));
    output.append("]");

    output.append(", buttons: ");
    output.append(buttons);

    output.append(", turns: ");
    output.append(turns);

    return output.toString();
  }
}
