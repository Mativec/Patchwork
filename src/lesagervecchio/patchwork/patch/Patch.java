package lesagervecchio.patchwork.patch;

import java.util.List;
import java.util.Objects;

/**
 * @param squares : List of square that compose the Patch.
 * @param buttonCost : cost of button.
 * @param turns   : cost of turns.
 * @param buttons : buttons on the Patch (add to player board) [not implemented yet]
 * @author Mativec (Matias VECCHIO)
 */
public record Patch(List<Integer[]> squares, int buttonCost, int turns, int buttons) {
  public Patch {
    Objects.requireNonNull(squares, "squares is null");

    if (buttonCost < 0) {
      throw new IllegalArgumentException("buttonCost < 0");
    }

    if (turns < 0) {
      throw new IllegalArgumentException("turns < 0");
    }
    if (buttons < 0) {
    	throw new IllegalArgumentException("buttons < 0");
    }
  }


  /**
   * Turn this to the right
   */
  public Patch right() {
    int tmp;
    int indent = 0;
    for (var coordinate : squares) {
      indent = indent < coordinate[1] ? coordinate[1] : indent;
      tmp = coordinate[0];
      coordinate[0] = -coordinate[1];
      coordinate[1] = tmp;
    }
    return Patches.move(this, indent, 0);
  }

  /**
   * Turn this to the left
   */
  public Patch left() {
    int tmp;
    int indent = 0;
    for (var coordinate : squares) {
      indent = indent < coordinate[0] ? coordinate[0] : indent;
      tmp = coordinate[1];
      coordinate[1] = -coordinate[0];
      coordinate[0] = tmp;
    }
    return Patches.move(this, 0, indent);
  }

  @Override
  public String toString() {
    var output = new StringBuilder();

    output.append("Patch : ");

    output.append("[");
    squares.forEach(square -> output.append("(").append(square[0]).append(",").append(square[1]).append(")"));
    output.append("]");

    output.append(", buttonCost: ");
    output.append(buttonCost);

    output.append(", turns: ");
    output.append(turns);

    return output.toString();
  }
}
