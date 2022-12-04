package lesagervecchio.patchwork.main;

import lesagervecchio.patchwork.board.PlayerBoard;
import lesagervecchio.patchwork.display.TextualDisplay;
import lesagervecchio.patchwork.patch.Patch;
import lesagervecchio.patchwork.patch.Patches;

import java.util.List;
import java.util.Scanner;

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

    var input = new Scanner(System.in);
    var first = new Patch(List.of(
      new Integer[]{0, 0},
      new Integer[]{0, 1},
      new Integer[]{1, 0},
      new Integer[]{1, 1}
    ), 0, 0);
    List<String> inputList = List.of("p", "l", "r", "n", "w", "e", "s", "c");

    board = new PlayerBoard(9, 9);
    boolean placePhase = true;
    while (placePhase) {
      System.out.println("Here's your board");
      display.drawPlayerBoard(board);
      System.out.println("here's the patch you want to place: ");
      display.drawPatch(first);
      System.out.println("[p] -> place \n[l/r] -> rotate [left/right]\n[n/w/e/s] -> move to the [north/west/east/south]");
      String choice;
      do {
        choice = input.nextLine();
      } while (inputList.stream().noneMatch(choice::equals));
      switch (choice) {
        case "p" -> placePhase = !board.put(first);
        case "l" -> first.left();
        case "r" -> first.right();
        case "n" -> Patches.move(first, 0, -1);
        case "w" -> Patches.move(first, -1, 0);
        case "e " -> Patches.move(first, 1, 0);
        case "s " -> Patches.move(first, 0, 1);
        case "c" -> placePhase = false;
      }
    }
    System.out.println("Here's your board now");
    display.drawPlayerBoard(board);
  }
}
