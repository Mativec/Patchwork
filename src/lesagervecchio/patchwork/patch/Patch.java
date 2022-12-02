package lesagervecchio.patchwork.patch;

import java.util.List;
import java.util.Objects;

public record Patch(List<Integer[]> squares, int buttons, int turns) {

  public Patch{
    Objects.requireNonNull(squares, "squares is null");

    if (buttons < 0) {
      throw new IllegalArgumentException("buttons < 0");
    }

    if (turns < 0) {
      throw new IllegalArgumentException("turns < 0");
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
