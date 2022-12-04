package lesagervecchio.patchwork.main;

import lesagervecchio.patchwork.board.PlayerBoard;
import lesagervecchio.patchwork.display.TextualDisplay;
import lesagervecchio.patchwork.patch.Patch;

import java.util.List;

public class Main {
  public static void main(String[] args) {
    TextualDisplay display = new TextualDisplay();
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
        new Integer[]{0, 4},
        new Integer[]{10, 0}
      ), 0, 0)
    ));

    var l = new Patch(List.of(
      new Integer[]{0, 0},
      new Integer[]{0, 1},
      new Integer[]{1, 0},
      new Integer[]{0, 2},
      new Integer[]{2, 0}
    ), 0, 0);
    var l2 = new Patch(List.of(
      new Integer[]{0, 0},
      new Integer[]{0, 1},
      new Integer[]{1, 0},
      new Integer[]{0, 2},
      new Integer[]{2, 0}
    ), 0, 0);
    var l3 = new Patch(List.of(
      new Integer[]{0, 0},
      new Integer[]{0, 1},
      new Integer[]{1, 0},
      new Integer[]{0, 2},
      new Integer[]{2, 0}
    ), 0, 0);
    display.drawPlayerBoard(board);
    System.out.println(board.getNbSquare());

    System.out.println("\n");
    display.drawPatch(l);
    System.out.println("\n");
    display.drawPatch(l2.right());
    System.out.println("\n");
    display.drawPatch(l3.left());

    var first = new Patch(List.of(
      new Integer[]{0, 0},
      new Integer[]{0, 1},
      new Integer[]{1, 0},
      new Integer[]{1, 1}
    ), 0, 0);
    new PlayerBoard(9, 9).patchPlacePhase(display, first);
  }
}
