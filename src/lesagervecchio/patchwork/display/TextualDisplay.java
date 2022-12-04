package lesagervecchio.patchwork.display;

import lesagervecchio.patchwork.board.PlayerBoard;
import lesagervecchio.patchwork.patch.Patch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public final class TextualDisplay implements DisplayService {
  /**
   * Return a String which represent patch's display
   *
   * @param patch : The patch we want to show on screen
   * @return String : Text version of the display
   */
  private String sketchPatch(Patch patch) {
    Objects.requireNonNull(patch, "patch is null");
    Map<Integer, ArrayList<Integer>> output = new HashMap<>();

    patch.squares().forEach(s -> output.computeIfAbsent(s[1], key -> new ArrayList<>()).add(s[0]));

    return output
      .values()
      .stream()
      .map(this::drawSquares)
      .collect(Collectors.joining("\n"))
      ;
  }

  /**
   * Write the text display version of the patch on standard output.
   *
   * @param patch : Patch we want to show on screen.
   */
  @Override
  public void drawPatch(Patch patch) {
    Objects.requireNonNull(patch, "patch is null");
    System.out.print(sketchPatch(patch).replaceAll("\\.", " "));
    System.out.println("Buttons : " + patch.buttons() + " Turns: " + patch.turns());
  }

  /**
   * Return a Text version of a square (that compose a Patch)
   *
   * @param list : list of squares of a patch
   */
  private String drawSquares(ArrayList<Integer> list) {
    Objects.requireNonNull(list, "list is null");
    var line = new StringBuilder();
    for (int i = 0; i < 9; i++) {
      line.append(list.contains(i) ? "[-]" : " . ");
    }
    return line.toString();
  }

  /**
   * Write on standard output, a text version of the board
   *
   * @param board : the Player Board we want to show
   */
  @Override
  public void drawPlayerBoard(PlayerBoard board) {
    Objects.requireNonNull(board, "board is null");
    var line = "-".repeat(board.getSizeX());
    System.out.println(line);
    System.out.println(sketchPatch(board.asOne()));
    System.out.println(line);
    System.out.println("Size : (" + board.getSizeX() + ", " + board.getSizeY() + ")");
  }
}
