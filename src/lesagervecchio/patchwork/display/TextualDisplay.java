package lesagervecchio.patchwork.display;

import lesagervecchio.patchwork.PlayerBoard;
import lesagervecchio.patchwork.patch.Patch;

import java.util.ArrayList;
import java.util.List;

public class TextualDisplay implements DisplayService {

  private static final int SIZE_X = 50;
  private static final int SIZE_Y = 15;

  private final List<String> screen = new ArrayList<>();

  @Override
  public void init() {
    for(int i = 0; i < SIZE_Y; i++){
      screen.add(" ".repeat(SIZE_X));
    }
  }

  /**
   * draw pix on screen at (x, y)
   */
  private void draw(int x, int y, char pix){
    if(x < SIZE_X && y < SIZE_Y){
      var newLine = screen.get(y).toCharArray();
      var tmp = new StringBuilder();

      newLine[x] = pix;

      for (char c : newLine) {
        tmp.append(c);
      }

      screen.set(y, tmp.toString());
    }
  }

  /**
   * draw a square on (x, y) point
   */
  private void drawSquare(Integer[] coord, int x, int y){
    draw(coord[0] + x, coord[1] + y, 'x');
  }

  @Override
  public void drawPatch(Patch patch, int x, int y) {
    patch.getSquares().forEach(square -> drawSquare(square, x, y));
  }

  @Override
  public void drawPlayerBoard(PlayerBoard board, int x, int y) {
    //Border
    for(int i = 0; i < (SIZE_X + 2); i++){
      draw(x + i, y, '-');
    }

    //board
    board.getBoard().keySet().forEach(square -> drawSquare(square, x, y + 1));

    //Final Border
    for(int i = 0; i < (SIZE_X + 2); i++){
      draw(x + i, y + PlayerBoard.getSizeY(), '-');
    }
  }

  @Override
  public void refresh() {
    screen.forEach(System.out::println);
  }
}
