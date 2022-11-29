package lesagervecchio.patchwork.main;

import lesagervecchio.patchwork.Patch;
import lesagervecchio.patchwork.PlayerBoard;

import java.util.List;

public class Main {
  public static void main(String[] args) {
    var board = new PlayerBoard();
    var patch = new Patch(
      1,
      List.of(
        new Integer[]{0, 0},
        new Integer[]{0, 1},
        new Integer[]{1, 0},
        new Integer[]{1, 1}
      ),
      0,
      0
    );
    System.out.println(Patch.of(1));
    System.out.println(board);
    System.out.println(board.put(patch));
    System.out.println(board);
  }
}
