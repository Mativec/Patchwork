package lesagervecchio.patchwork.display;

import lesagervecchio.patchwork.board.PlayerBoard;
import lesagervecchio.patchwork.patch.Patch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public final class TextualDisplay implements DisplayService {
  @Override
  public void drawPatch(Patch patch) {
    Objects.requireNonNull(patch, "patch is null");
    Map<Integer, ArrayList<Integer>> output = new HashMap<>();

    patch.squares().forEach(
      s -> output.computeIfAbsent(s[1], key -> new ArrayList<>()).add(s[0])
    );
    System.out.println(output
      .values()
      .stream()
      .map(this::drawSquares)
      .collect(Collectors.joining("\n"))
    );
  }

  private String drawSquares(ArrayList<Integer> list) {
    Objects.requireNonNull(list, "list is null");
    var line = new StringBuilder();
    for (int i = 0; i < 9; i++) {
      line.append(list.contains(i) ? "[-]" : "   ");
    }
    return line.toString();
  }

  @Override
  public void drawPlayerBoard(PlayerBoard board) {
    Objects.requireNonNull(board, "board is null");
    drawPatch(board.asOne());
  }
}
