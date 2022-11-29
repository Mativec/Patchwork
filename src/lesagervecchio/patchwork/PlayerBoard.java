package lesagervecchio.patchwork;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class PlayerBoard {
  private static final int SIZE_X = 9;
  private static final int SIZE_Y = 9;

  private final Map<Integer[], Integer> board = new HashMap<>();

  public PlayerBoard() {}

  /**
   *
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
   *
   * @param coordinate : coordinate of a Patch
   * @throws IllegalArgumentException : if coordinate out of bound
   */
  private void checkOutOfBound(Integer[] coordinate){
    if(coordinate[0] >= SIZE_X && coordinate[1] >= SIZE_Y){
      throw new IllegalArgumentException("patch(x, y) out of the board");
    }
  }

  /**
   *
   * @param patch : a patch in or out the board
   * @return boolean : contains or not
   * @throws NullPointerException : if patch is null
   */
  public boolean put(Patch patch) {
    Objects.requireNonNull(patch, "patch is null");

    var squares = patch.getSquares();

    squares.forEach(this::checkOutOfBound);
    if (!contains(patch)) {
      squares.forEach(square -> board.put(square, 1));
      return true;
    }
    return false;
  }

  @Override
  public String toString() {
    //TODO :  Remake with Stram
    var output = new StringBuilder();

    output.append("PlayerBoard{");
    board.forEach((key, value) -> output.append("(").append(key[0]).append(",").append(key[1]).append(")"));
    output.append('}');

    return output.toString();
  }
}
