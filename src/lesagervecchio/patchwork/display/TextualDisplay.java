package lesagervecchio.patchwork.display;

import lesagervecchio.patchwork.board.PlayerBoard;
import lesagervecchio.patchwork.global.GlobalPatches;
import lesagervecchio.patchwork.patch.Patch;
import lesagervecchio.patchwork.player.Player;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Mativec (Matias VECCHIO)
 */
public final class TextualDisplay implements DisplayService {

  private static final Scanner scan = new Scanner(System.in);

  /**
   * Return a String which represent patch's display
   *
   * @param patch : The patch we want to show on screen
   * @return String : Text version of the display
   */
  private String sketchPatch(Patch patch) {
    Objects.requireNonNull(patch, "patch is null");
    Map<Integer, ArrayList<Integer>> output = new HashMap<>();

    patch.squares().forEach(s -> output.computeIfAbsent(s[1], key -> new ArrayList<>()).add(s[0]));

    return output
      .values()
      .stream()
      .map(this::drawSquares)
      .collect(Collectors.joining("\n"))
      ;
  }

  /**
   * Write the text display version of the patch on standard output.
   *
   * @param patch : Patch we want to show on screen.
   */
  @Override
  public void drawPatch(Patch patch) {
    Objects.requireNonNull(patch, "patch is null");
    System.out.print(sketchPatch(patch).replaceAll("\\.", " "));
    System.out.println(patch);
  }

  /**
   * Return a Text version of a square (that compose a Patch)
   *
   * @param list : list of squares of a patch
   */
  private String drawSquares(ArrayList<Integer> list) {
    Objects.requireNonNull(list, "list is null");
    var line = new StringBuilder();
    for (int i = 0; i < 9; i++) {
      line.append(list.contains(i) ? "[-]" : " . ");
    }
    return line.toString();
  }

  /**
   * Write on standard output, a text version of the board
   *
   * @param board : the Player Board we want to show
   */
  @Override
  public void drawPlayerBoard(PlayerBoard board) {
    Objects.requireNonNull(board, "board is null");

    var line = "-".repeat(board.getSIZE() * 3);
    System.out.println(line);
    System.out.println(sketchPatch(board.asOne()));
    System.out.println(line);
    System.out.println("Size : (" + board.getSIZE() + ", " + board.getSIZE() + ")");
  }

  @Override
  public void drawGlobalBoard(ArrayList<Player> players) {
    var builder = new StringBuilder();
    builder.append("0|");
    if (players.get(0).position() <= players.get(1).position()) {
      builder.append("J1->").append(players.get(0).position());
      builder.append("|J2->").append(players.get(1).position());
    } else {
      builder.append("J2->").append(players.get(1).position());
      builder.append("|J1->").append(players.get(0).position());
    }
    builder.append("|53\n");
    System.out.println(builder);
  }


  /**
   * Method that display orderPatches.
   */
  @Override
  public void drawOrderPatches(GlobalPatches globalPatches) {
    //affiche les 8 prochains patchs de orderPatches
    var builder1 = new StringBuilder();
    var builder2 = new StringBuilder();
    var patchesById = globalPatches.getPatchesById();
    var orderPatches = globalPatches.getOrderPatches();
    builder1.append("{");
    builder2.append("{");
    for (var i = 0; i < 10; i++) {
      String bringedButtons = String.valueOf(patchesById.get(orderPatches.get(i)).buttons());
      String button = String.valueOf(patchesById.get(orderPatches.get(i)).buttonCost());
      builder1.append(button);
      builder2.append(bringedButtons);
      if (button.length() == 1) {
        builder1.append(" ");
      }
      if (bringedButtons.length() == 1) {
        builder2.append(" ");
      }
      builder1.append("|");
      builder2.append("|  ");
      String turn = String.valueOf(patchesById.get(orderPatches.get(i)).turns());
      builder1.append(turn);
      if (turn.length() == 1) {
        builder1.append(" ");
      }
      if (i < 9) {
        builder1.append("}{");
        builder2.append("}{");
      }

    }
    builder1.append("}");
    builder2.append("}");
    System.out.println("+-----+".repeat(10));
    System.out.println(builder1);
    System.out.println("-".repeat(70));
    System.out.println(builder2);
    System.out.println("+-----+".repeat(10) + "\n");
  }

  @Override
  public void drawText(String... text) {
    Objects.requireNonNull(text, "arg null");
    Arrays.stream(text).forEach(System.out::println);
  }

  @Override
  public String waitText() {
    return scan.nextLine();
  }

  @Override
  public char waitInput() {
    return scan.nextLine().charAt(0);
  }

  @Override
  public void moveCursor(int x, int y) {
  }

  @Override
  public void close() {
    scan.close();
  }
}
