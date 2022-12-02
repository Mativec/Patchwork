package lesagervecchio.patchwork.patch;

import java.util.*;

public class Patch {
  private static final Map<Integer, Patch> patchList = new HashMap<>();

  public static Patch of(int id) {
    return patchList.get(id);
  }

  /***************************************************************************/
  private List<Integer[]> squares;
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
   * Get the amount of button required to buy this patch.
   * @return : int
   */
  public int getButtons() { return buttons; }

  /**
   * Get the amount of turn to play to buy this patch.
   * @return : int
   */
  public int getTurns() { return turns; }

  /**
   * Move a Patch.
   */
  public void move(int targetX, int targetY){
    for(var coordinate : squares){
      coordinate[0] = coordinate[0] + targetX;
      coordinate[1] = coordinate[1] + targetY;
    }
  }

  /**
   * turn the Patch on the right or the left.
   */
  public void turn(boolean left){
    if(left){
      squares = squares.stream().parallel().toList(); //reverse the list
    }
    for(var coordinate : squares){
      //switch X & Y coordinate
      var tmp = coordinate[0];
      coordinate[0] = coordinate[1];
      coordinate[1] = tmp;
    }
  }

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
