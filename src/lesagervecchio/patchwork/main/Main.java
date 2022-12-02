package lesagervecchio.patchwork.main;

import lesagervecchio.patchwork.board.PlayerBoard;
import lesagervecchio.patchwork.display.TextualDisplay;
import lesagervecchio.patchwork.patch.Patch;

import java.util.List;

public class Main {
  public static void main(String[] args) {
    var display = new TextualDisplay();
    var board = new PlayerBoard(9, 9);

    System.out.println(board.put(new Patch(
      List.of(
        new Integer[]{0, 0},
        new Integer[]{1, 0},
        new Integer[]{0, 1},
        new Integer[]{1, 1}
      ), 0, 0)
    ));

    System.out.println(board.put(new Patch(
      List.of(
        new Integer[]{7, 7},
        new Integer[]{8, 7},
        new Integer[]{7, 8}
      ), 0, 0)
    ));

    System.out.println(board.put(new Patch(
      List.of(
        new Integer[]{0, 10},
        new Integer[]{10, 0}
      ), 0, 0)
    ));

    display.drawPlayerBoard(board);
    System.out.println(board.getNbSquare());
  }
}
