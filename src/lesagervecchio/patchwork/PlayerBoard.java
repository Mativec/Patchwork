package lesagervecchio.patchwork;

import lesagervecchio.patchwork.patch.Patch;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class PlayerBoard {
  private static final int SIZE_X = 9;
  private static final int SIZE_Y = 9;

  private final Map<Integer[], Integer> board = new HashMap<>();

  public static int getSizeX() {
    return SIZE_X;
  }

  public static int getSizeY() {
    return SIZE_Y;
  }

  public PlayerBoard() {
  }

  /**
   * @param patch : a patch in or out of the board
   * @return boolean : if the operation succeeded or not.
   * @throws NullPointerException : if patch is null
   */
  public boolean contains(Patch patch) {
    Objects.requireNonNull(patch, "patch is null");
    return patch.getSquares()
            .stream()
            .mapToInt(integers -> board.getOrDefault(integers, 0))
            .sum() != 0
            ;
  }

  /**
   * @param coordinate : coordinate of a Patch
   * @throws IllegalArgumentException : if coordinate out of bound
   */
  private void checkOutOfBound(Integer[] coordinate) {
    if (coordinate[0] >= SIZE_X && coordinate[1] >= SIZE_Y) {
      throw new IllegalArgumentException("patch(x, y) out of the board");
    }
  }

  /**
   * @param patch : a patch in or out the board
   * @param x     : X coordinate to put the Patch (~ corner SE)
   * @param y     : Y coordinate to put the Patch (~ corner SE)
   * @return boolean : contains or not
   * @throws NullPointerException : if patch is null
   */
  public boolean put(Patch patch, int x, int y) {
    Objects.requireNonNull(patch, "patch is null");

    patch.move(x, y);
    var squares = patch.getSquares();

    try {
      squares.forEach(this::checkOutOfBound);
    } catch (IllegalArgumentException e) {
      return false;
    }

    if (!contains(patch)) {
      squares.forEach(square -> board.put(square, 1));
      return true;
    }
    return false;
  }

  public Map<Integer[], Integer> getBoard() {
    return board;
  }

  /**
   * Make an action on every c
   * @param action
   */
  public void forEach(BiConsumer<Integer[], Integer> action) {
    var board = getBoard();
    Integer[] key;
    for (int x = 0; x < SIZE_X; x++) {
      for (int y = 0; y < SIZE_Y; y++) {
        key = new Integer[]{x, y};
        action.accept(key, board.getOrDefault(key, 0));
      }
    }
  }

  @Override
  public String toString() {
    //TODO :  Remake with Stream
    var output = new StringBuilder();

    output.append("PlayerBoard{");
    board.forEach((key, value) -> output.append("(").append(key[0]).append(",").append(key[1]).append(")"));
    output.append('}');

    return output.toString();
  }
}
