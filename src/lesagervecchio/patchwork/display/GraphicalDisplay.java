package lesagervecchio.patchwork.display;


import fr.umlv.zen5.Event;

import fr.umlv.zen5.*;
import lesagervecchio.patchwork.board.PlayerBoard;
import lesagervecchio.patchwork.global.GlobalBoard;
import lesagervecchio.patchwork.global.GlobalPatches;
import lesagervecchio.patchwork.patch.Patch;
import lesagervecchio.patchwork.player.Player;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;

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

  /**
   * Method that draw a Patch depending on the value of x and y;
   * 
   * @param patch : the patch to be drawn
   */
  @Override
  public void drawPatch(Patch patch) {
	float hauteurEcart, largeurEcart, largeurPlateau;
    ScreenInfo screen = context.getScreenInfo();
	hauteurEcart = screen.getHeight() / 10;
	largeurPlateau = screen.getHeight() - hauteurEcart * 2;
	largeurEcart = (screen.getWidth() - largeurPlateau) / 2;
    moveCursor(largeurEcart, hauteurEcart);
    context.renderFrame(graphics2D -> {
      Random random = new Random();
      Color couleur = new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
      for (var coord : patch.squares()) {
    	System.out.println(coord[0]);
    	System.out.println(coord[1]);
        drawSquare(couleur, graphics2D,
          x + coord[0] * (largeurPlateau / 9),
          y + coord[1] * (largeurPlateau / 9),
          (largeurPlateau / 9), 5);
      }
    });
  }
  
  /*
   * Method that display the grid for the personal board of a player
   * 
   * @param tailleCase : the width of a case 
   */
  public void drawGrille(Graphics2D graphics2D, float tailleCase) {
    for (var i = 0; i < 9; i++) {
      for (var z = 0; z < 9; z++) {
        drawSquare(Color.BLACK, graphics2D, x + i * tailleCase, y + z * tailleCase, tailleCase, 3);
      }
    }
  }

  /**
   * Method that draw the playerBoard of the current player.
   *
   * @param board : the board of the current player
   */
  @Override
  public void drawPlayerBoard(PlayerBoard board) {
    clearWindow();
    ScreenInfo screen = context.getScreenInfo();
    float hauteurEcart, largeurEcart, largeurPlateau;
    hauteurEcart = screen.getHeight() / 10;
    largeurPlateau = screen.getHeight() - hauteurEcart * 2;
    largeurEcart = (screen.getWidth() - largeurPlateau) / 2;
    moveCursor(largeurEcart, hauteurEcart);
    context.renderFrame(graphics2D -> {
      drawSquare(Color.BLACK, graphics2D, largeurEcart, hauteurEcart, largeurPlateau, 5);
      moveCursor(largeurEcart, hauteurEcart);
      drawGrille(graphics2D, largeurPlateau / 9);
      // var patch = Patches.binToPatch(List.of("16", "16", "0", "0"), 0, 0, 0);
      for (var playerPatch : board.getBoard()) {
        moveCursor(largeurEcart, hauteurEcart);
        drawPatch(playerPatch);
      }
    });
  }

  /**
   * Draw a square on the board
   *
   * @param graphics2D : frame where the square is drawn.
   * @param x          : horizontal coordinate which the square is drawn.
   * @param y          : vertical coordinate which the square is drawn.
   * @param c          : size of a side of the square.
   * @param w          : width of a side of the square.
   */
  void drawSquare(Color color, Graphics2D graphics2D, float x, float y, float c, float w) {
    graphics2D.setColor(color);
    graphics2D.fill(new Rectangle2D.Float(x, y, c, c));
    graphics2D.setColor(backgroundColor);
    graphics2D.fill(new Rectangle2D.Float(x + w, y + w, c - 2 * w, c - 2 * w));
  }

  @Override
  public void drawGlobalBoard(ArrayList<Player> players) {
    ScreenInfo screen = context.getScreenInfo();
    float sizeSquareSide = 50;
    float widthSquareSide = 5;
    float baseX = x;
    float baseY = y;
    moveCursor(screen.getWidth() / 10, screen.getHeight() / 4);
    drawText("Plateau de jeu : ");
    y += sizeSquareSide;
    context.renderFrame(graphics2D -> {
      float offset = sizeSquareSide;
      for (int i = 1; i <= GlobalBoard.size(); i++) {
        int position = i - 1;
        float squareX = x - widthSquareSide;
        float squareY = y - sizeSquareSide + widthSquareSide;
        Color color = getGlobalBoardColor(position, players);
        drawSquare(color, graphics2D, squareX, squareY, sizeSquareSide, widthSquareSide);
        if (color == Color.BLACK && GlobalBoard.getButtons().stream().anyMatch(integer -> integer.equals(position))) {
          drawSquare(Color.CYAN, graphics2D, squareX, squareY, sizeSquareSide, widthSquareSide / 2);
        }
        if (GlobalBoard.getSpecialPatches().stream().anyMatch(integer -> integer.equals(position))) {
          drawSquare(Color.GREEN, graphics2D, squareX, squareY, sizeSquareSide, widthSquareSide / 2);
        }
        drawText(String.valueOf(i));
        if (i % 9 == 0) {
          offset *= -1;
          y += sizeSquareSide;
        } else {
          x += offset;
        }
      }
    });
    drawGlobalBoardDescr(players);
    x = baseX;
    y = baseY;
  }

  /**
   * Draw a description of GlobalBoard's display
   *
   * @param players : an array of player
   */
  private void drawGlobalBoardDescr(ArrayList<Player> players) {
    int offset = 25;
    int size = 5;
    x = 10;
    float baseY = y;
    context.renderFrame(graphics2D -> {
      drawSquare(Color.PINK, graphics2D, x, y, offset, size);
      y += offset;
      drawSquare(Color.BLUE, graphics2D, x, y, offset, size);
      y += offset;
      drawSquare(Color.RED, graphics2D, x, y, offset, size);
      y += offset;
      drawSquare(Color.CYAN, graphics2D, x, y, offset, size);
      y += offset;
      drawSquare(Color.GREEN, graphics2D, x, y, offset, size);
      y += offset;
    });
    x += offset;
    y = baseY + offset;
    drawText(
      "Les deux joueurs",
      players.get(0).name(),
      players.get(1).name(),
      "+1 bouton en passant cette case",
      "+1  special Patch en passant cette case"
    );
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
    ScreenInfo screen = context.getScreenInfo();
    float baseX, baseY, tmpX;
    int offset = 20;
    double spaceBetweenPatch = offset * 2.5;
    baseX = x;
    baseY = y;
    tmpX = screen.getWidth() / 6;
    x = tmpX;
    y = screen.getHeight() / 6;
    drawText("Liste des patches présents dans le deck :");
    y += font.getSize();
    globalPatches.getPatchesById().values().forEach(patch -> {
      drawPatch(patch, offset);
      x += spaceBetweenPatch;
      if (x >= screen.getWidth() / 2) {
        x = tmpX;
        y += spaceBetweenPatch;
      }
      ;
    });
    x = baseX;
    y = baseY;
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
   *
   * @param line an argument of drawText(String ... text)
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
   *
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

  /**
   * Extract a char representing the input.
   *
   * @param key : the input to translate
   * @return : a char
   */
  private char getInput(KeyboardKey key) {
    return switch (key) {
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
    ScreenInfo screen = context.getScreenInfo();
    if (x >= screen.getWidth() || (x <= 0 && x != -1)) {
      System.err.println("Invalide x to move cursor");
    } else if (y >= screen.getHeight() || (y <= 0 && y != -1)) {
      System.err.println("Invalide y to move cursor");
    } else {
      this.x = (x == -1 ? context.getScreenInfo().getWidth() / 2 : x);
      this.y = (y == -1 ? context.getScreenInfo().getHeight() / 2 : y);
    }
  }

  @Override
  public void clearWindow() {
    ScreenInfo screenInfo = context.getScreenInfo();
    context.renderFrame(graphics2D -> {
      graphics2D.setColor(backgroundColor);
      System.out.println(screenInfo.getWidth());
      System.out.println(screenInfo.getHeight());
      graphics2D.fill(new Rectangle2D.Float(0, 0, screenInfo.getWidth(), screenInfo.getHeight()));
    });
  }

  @Override
  public void close() {
    context.exit(0);
  }
}
