package lesagervecchio.patchwork.board;

import lesagervecchio.patchwork.display.DisplayService;

import lesagervecchio.patchwork.patch.Patch;
import lesagervecchio.patchwork.patch.Patches;

import java.util.*;

/**
 * @author Mativec (Matias VECCHIO)
 */
public class PlayerBoard {
  private final int SIZE = 9; // 9 x 9
  private final List<Patch> board = new ArrayList<>();
  private int nbSquare = 0;

  /**
   * Get many squares has been placed on board
   */
  public int getNbSquare() {
    return nbSquare;
  }
  
  public List<Patch> getBoard(){
	  return board;
  }
  
  /**
   * Get the number of Bonus Buttons contained in all the pacthes of the board
   * 
   * @return the number of bonus buttons
   */
  public int getNumberBonusButtons() {
	  return board.stream().map(Patch::buttons).reduce(null, null);
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
    return newSquare[0] < 0 || newSquare[0] >= SIZE || newSquare[1] < 0 || newSquare[1] >= SIZE;
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
    List<Integer> index = new ArrayList<>();
    int noHole;

    //enough square to make a 7 x 7 patches ?
    if (board.stream().mapToInt(Patches::size).sum() >= 49) {
      // Get all column of the board longer or equal than 7 squares
      getColumn().entrySet().stream()
        .filter(entry -> entry.getValue() >= 7).forEach(entry -> index.add(entry.getKey()));

      // Check if there is 7 columns in a row
      noHole = 1;
      for (int i = 1; i < index.size() && noHole < 7; i++) {
        if (index.get(i) != index.get(i - 1) + 1) {
          noHole = 0;
        }
        noHole++;
      }
      return noHole >= 7;
    }
    return false;
  }


  /**
   * Return a Map which represent the number of squares by column on the board.
   *
   * @return : Map<ColumnNumber, NbSquares>
   */
  private Map<Integer, Integer> getColumn() {
    Map<Integer, Integer> output = new HashMap<>();
    board.forEach(
      patch -> patch.squares().forEach(
        integers -> output.merge(integers[0], 1, Integer::sum)
      )
    );
    return output;
  }

  /**
   * Return the size of this board
   *
   * @return int : horizontal size
   */
  public int getSIZE() {
    return SIZE;
  }


  /**
   * Manage the game phase where a player want to place a Patch on his board (this)
   *
   * @param patch : The Patch wanted to be placed on this.
   */
  public void patchPlacePhase(DisplayService display, Patch patch) {
    List<Character> inputList = List.of('p', 'l', 'r', 'n', 'w', 'e', 's', 'c');
    Character choice;

    boolean placePhase = true;
    while (placePhase) {
      display.moveCursor(50, 100);
      display.drawText("Voici votre plateau :");
      display.drawPlayerBoard(this);
      display.moveCursor(50, 300);
      display.drawText("Voici le patch que vous voulez\n ajouter au plateau :");
      display.moveCursor(50, 400);
      display.drawPatch(patch);
      display.drawText(
        "[p] -> placer",
        "[l/r] -> tourner [gauche/droite]",
        "[n/w/e/s] -> déplacer vers le [nord/ouest/est/sud]"
      );
      do {
        choice = display.waitInput();
      } while (inputList.stream().noneMatch(choice::equals));
      switch (choice) {
        case 'p' -> placePhase = !put(patch);
        case 'l' -> patch.left();
        case 'r' -> patch.right();
        case 'n' -> Patches.move(patch, 0, -1);
        case 'w' -> Patches.move(patch, -1, 0);
        case 'e' -> Patches.move(patch, 1, 0);
        case 's' -> Patches.move(patch, 0, 1);
        case 'c' -> placePhase = false;
      }
    }
    display.drawText("Voici donc votre plateau :");
    display.drawPlayerBoard(this);
    display.drawPatch(patch);
  }
}
