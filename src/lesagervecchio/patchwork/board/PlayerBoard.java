package lesagervecchio.patchwork.board;

import lesagervecchio.patchwork.display.TextualDisplay;
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
    int squares = board
      .stream()
      .mapToInt(Patches::size)
      .sum();
    if(squares >= 49){
      var truc = getValues();
      System.out.println("Plateau : ");
      System.out.println(truc);
      long truc2 = truc.values().stream().filter(value -> value >= 7).count();
      System.out.println(truc2);
      return truc2 >= 7;
    }
    return false;
  }

  private Map<Integer, Integer> getValues() {
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

  public static void main(String[] args) {
    var board = new PlayerBoard();
    for(int i = 0; i < 7; i++){
      board.put(new Patch(List.of(
        new Integer[]{0, i},
        new Integer[]{1, i},
        new Integer[]{2, i},
        new Integer[]{3, i},
        new Integer[]{4, i},
        new Integer[]{5, i},
        new Integer[]{7, i}
      ), 0, 0, 0));
    }
    new TextualDisplay().drawPlayerBoard(board);
    System.out.println(board.hasBonusPatch());
  }

}
