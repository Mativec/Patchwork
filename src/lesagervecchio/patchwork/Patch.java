package lesagervecchio.patchwork;

import java.util.*;

public class Patch{
  private static final Map<Integer, Patch> patchList = new HashMap<>();
  public static Patch of(int id) { return patchList.get(id); }

/***************************************************************************/
  private final List<Integer[]> squares;
  private final int buttons;
  private final int turns;
  public Patch (int id, List<Integer[]> squares, int buttons, int turns){
    Objects.requireNonNull(squares, "squares is null");
    this.squares = squares;

    if (buttons < 0){ throw new IllegalArgumentException("buttons < 0"); }
    this.buttons = buttons;

    if (turns < 0){ throw new IllegalArgumentException("turns < 0"); }
    this.turns = turns;

    patchList.put(id, this);
  }

  @Override
  public String toString() {
    var output = new StringBuilder();

    output.append("Patch : ");
    output.append("[");
    for (var square : squares){
      output.append("(");
      output.append(square[0]);
      output.append(",");
      output.append(square[1]);
      output.append(")");
    }

    output.append("]");
    output.append(", buttons: ");
    output.append(buttons);
    output.append(", turns: ");
    output.append(turns);

    return output.toString();
  }
}
