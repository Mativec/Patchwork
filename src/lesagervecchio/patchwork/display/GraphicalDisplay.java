package lesagervecchio.patchwork.display;

import fr.umlv.zen5.Event;
import fr.umlv.zen5.*;
import lesagervecchio.patchwork.board.PlayerBoard;
import lesagervecchio.patchwork.global.GlobalBoard;
import lesagervecchio.patchwork.global.GlobalPatches;
import lesagervecchio.patchwork.patch.Patch;
import lesagervecchio.patchwork.player.Player;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

public final class GraphicalDisplay implements DisplayService {
  private ApplicationContext context; // The window
  private final Color backgroundColor = Color.WHITE; // Background color of the screen
  private Font font; // The font used by drawText
  private float x; // Horizontal coordinate of the cursor
  private float y; // Vertical coordinate of the cursor

  public GraphicalDisplay() {
    x = 0;
    y = 0;
    Application.run(backgroundColor, applicationContext -> context = applicationContext);
  }

  @Override
  public void drawPatch(Patch patch) {

  }

  @Override
  public void drawPlayerBoard(PlayerBoard board) {

  }

  /**
   * Draw a square on the board
   * @param graphics2D : frame where the square is drawn.
   * @param x : horizontal coordinate which the squ&are is drawn.
   * @param y : vertical coordinate which the squ&are is drawn.
   * @param c : size of a side of the square.
   * @param w : width of a side of the square.
   */
  void drawSquare(Color color, Graphics2D graphics2D, float x, float y, float c, float w){
    graphics2D.setColor(color);
    graphics2D.fill(new Rectangle2D.Float(x, y, c, c));
    graphics2D.setColor(backgroundColor);
    graphics2D.fill(new Rectangle2D.Float(x + w, y + w, c - 2 * w, c - 2 * w));
  }

  @Override
  public void drawGlobalBoard(ArrayList<Player> players) {
    // TODO: 01/15/2023 Check if everything is fine after merge
    ScreenInfo screen = context.getScreenInfo();
    moveCursor(screen.getWidth() / 4, screen.getHeight() / 4);
    float baseX = x;
    float baseY = y;
    context.renderFrame(graphics2D -> {
      float offset = 50;
      for (int i = 0; i <= GlobalBoard.size(); i++) {
        Color color = getGlobalBoardColor(i, players);
        x += offset;
        drawSquare(color, graphics2D, x, y, 50, 5);
        if(i != 0 && i % 9 == 0){
          offset *= -1;
          x += offset;
          y += 50;
        }
      }
    });
    x = baseX;
    y = baseY;
  }

  private Color getGlobalBoardColor(int position, ArrayList<Player> players) {
    if (players.stream().allMatch(player -> player.position() == position)) {
      return Color.PINK;
    } else if (position == players.get(0).position()) {
      return Color.BLUE;
    } else if (position == players.get(1).position()) {
      return Color.RED;
    } else {
      return Color.BLACK;
    }
  }

  @Override
  public void drawOrderPatches(GlobalPatches globalPatches) {
    // TODO: 01/15/2023 Check if everything is fine after merge
    ScreenInfo screen = context.getScreenInfo();
    moveCursor(screen.getWidth() / 4, screen.getHeight() / 4);
  }

  @Override
  public void setupText(String FontName, int fontSize) {
    font = new Font(FontName, Font.PLAIN, fontSize);
  }

  @Override
  public void drawText(String... text) {
    int offset = 0;
    float baseY = y;
    for (String line : text) {
      y += offset;
      offset = font.getSize();
      drawText(line);
    }
    y = baseY;
  }

  /**
   * Draw one line of text
   * @param line : an argument of drawText(String ... text)
   */
  private void drawText(String line) {
    Objects.requireNonNull(font, "font has not been setup");
    context.renderFrame(graphics2D -> {
      graphics2D.setColor(Color.BLACK);
      graphics2D.setFont(font);
      graphics2D.drawString(line, x, y);
    });
  }

  @Override
  public String waitText() {
    StringBuilder output = new StringBuilder();
    char input;
    do {
      input = waitInput();
      if (input != 0) {
        output.append(input);
      }
      drawText(output.toString());
    } while (input != 0);

    return output.toString();
  }

  /**
   * Check if key is a valid input
   * @param key : the input
   * @return : valid or not
   */
  private boolean validKey(KeyboardKey key) {
    if (Objects.isNull(key)) {
      return false;
    }
    return switch (key) {
      case ALT, ALT_GR, CTRL, META, SPACE, SHIFT -> false;
      default -> true;
    };
  }

  @Override
  public char waitInput() {
    Event event;
    do {
      event = context.pollOrWaitEvent(Integer.MAX_VALUE);
    } while (Objects.isNull(event) || event.getAction() != Event.Action.KEY_RELEASED || !validKey(event.getKey()));
    return getInput(event.getKey());
  }

  private char getInput(KeyboardKey key) {
    return switch(key){
      case UNDEFINED -> 0;
      case UP -> 'n';
      case LEFT -> 'w';
      case RIGHT -> 'e';
      case DOWN -> 's';
      default -> key.name().toLowerCase(Locale.ROOT).charAt(0);
    };
  }

  @Override
  public void moveCursor(float x, float y) {
    this.x = (x == -1 ? context.getScreenInfo().getWidth() / 2 : x);
    this.y = (y == -1 ? context.getScreenInfo().getHeight() / 2 : y);
  }

  @Override
  public void clearWindow() {
    ScreenInfo screenInfo = context.getScreenInfo();
    context.renderFrame(graphics2D -> {
      graphics2D.setColor(backgroundColor);
      graphics2D.fill(new Rectangle2D.Float(0, 0, screenInfo.getWidth(), screenInfo.getHeight()));
    });
  }

  @Override
  public void close() {
    context.exit(0);
  }
}
