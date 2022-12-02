package lesagervecchio.patchwork.display;

import lesagervecchio.patchwork.board.PlayerBoard;
import lesagervecchio.patchwork.patch.Patch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public final class TextualDisplay implements DisplayService {
  @Override
  public void drawPatch(Patch patch) {
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
    var line = new StringBuilder();
    for (int i = 0; i < 9; i++) {
      line.append(list.contains(i) ? "[-]" : "   ");
    }
    return line.toString();
  }

  @Override
  public void drawPlayerBoard(PlayerBoard board) {
    drawPatch(board.asOne());
  }
}
