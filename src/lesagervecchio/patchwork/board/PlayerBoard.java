package lesagervecchio.patchwork.board;

import lesagervecchio.patchwork.display.TextualDisplay;
import lesagervecchio.patchwork.patch.Patch;
import lesagervecchio.patchwork.patch.Patches;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

/**
 * @author Mativec (Matias VECCHIO)
 */
public class PlayerBoard {
  private final int sizeX;
  private final int sizeY;
  private final List<Patch> board = new ArrayList<>();
  private int nbSquare = 0;

  public PlayerBoard(int sizeX, int sizeY) {
    this.sizeX = sizeX;
    this.sizeY = sizeY;
  }

  /**
   * Get many squares has been placed on board
   */
  public int getNbSquare() {
    return nbSquare;
  }


  /**
   * Check if we're trying to overwrite a patch on Board
   *
   * @param newPatch : New Patch trying to be placed on board
   */
  public boolean contains(Patch newPatch) {
    Objects.requireNonNull(newPatch, "patch is null");
    return board.stream().anyMatch(patch -> Patches.conflict(patch, newPatch));
  }


  /**
   * Check if a square is being put outside of the board
   */
  public boolean outOfBound(Integer[] newSquare) {
    Objects.requireNonNull(newSquare, "square array is null");
    return newSquare[0] < 0 || newSquare[0] >= sizeX || newSquare[1] < 0 || newSquare[1] >= sizeY;
  }


  /**
   * use contains and outOfBound to tell if a patch can be put on board
   *
   * @param patch : newer patch trying to be placed on board.
   * @return can be placed or not
   */
  public boolean canBePlaced(Patch patch) {
    Objects.requireNonNull(patch, "patch is null");
    return patch.squares().stream().noneMatch(this::outOfBound) && !contains(patch);
  }

  /**
   * Put newPatch on the board
   *
   * @return : succeeded or not
   */
  public boolean put(Patch newPatch) {
    Objects.requireNonNull(newPatch, "patch is null");

    if (!canBePlaced(newPatch)) {
      System.err.println(newPatch + " can't be placed");
      return false;
    } else {
      board.add(newPatch);
      nbSquare += Patches.size(newPatch);
      return true;
    }
  }

  /**
   * Convert the playerBoard as a Patch alone
   *
   * @return : a Patch with all squares on board
   */
  public Patch asOne() {
    List<Integer[]> list = new ArrayList<>();
    board.forEach(patch -> list.addAll(patch.squares()));
    return new Patch(list, 0, 0, 0);
  }

  /**
   * Check if this forms a square of 7x7
   *
   * @return Bool : yes or not
   */
  public boolean hasBonusPatch() {
    long squares = board
      .stream()
      .mapToLong(Patches::size)
      .sum();
    return squares >= 49;
  }

  /**
   * Return X size of this board
   *
   * @return int : horizontal size
   */
  public int getSizeX() {
    return sizeX;
  }

  /**
   * Return Y size of this board
   *
   * @return int : vertical size
   */
  public int getSizeY() {
    return sizeY;
  }


  /**
   * Manage the game phase where a player want to place a Patch on his board (this)
   *
   * @param patch : The Patch wanted to be placed on this.
   */
  public void patchPlacePhase(TextualDisplay display, Patch patch) {
    List<String> inputList = List.of("p", "l", "r", "n", "w", "e", "s", "c");
    var input = new Scanner(System.in);

    boolean placePhase = true;
    while (placePhase) {
      //System.out.println("Here's your board :");
      System.out.println("Voici votre plateau :");
      display.drawPlayerBoard(this);
      //System.out.println("here's the patch you want to place: ");
      System.out.println("Voici le patch que vous voulez ajouter au plateau :");
      display.drawPatch(patch);
      //System.out.println("[p] -> place \n[l/r] -> rotate [left/right]\n[n/w/e/s] -> move to the [north/west/east/south]");
      System.out.println("[p] -> placer \n[l/r] -> tourner [gauche/droite]\n[n/w/e/s] -> déplacer vers le [nord/ouest/est/sud]");
      String choice;
      do {
        choice = input.nextLine();
      } while (inputList.stream().noneMatch(choice::equals));
      switch (choice) {
        case "p" -> placePhase = !put(patch);
        case "l" -> patch.left();
        case "r" -> patch.right();
        case "n" -> Patches.move(patch, 0, -1);
        case "w" -> Patches.move(patch, -1, 0);
        case "e" -> Patches.move(patch, 1, 0);
        case "s" -> Patches.move(patch, 0, 1);
        case "c" -> placePhase = false;
      }
    }
    //System.out.println("Here's your board now :");
    System.out.println("Voici donc votre plateau :");
    display.drawPlayerBoard(this);
  }
}
