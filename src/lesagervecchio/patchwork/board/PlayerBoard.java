package lesagervecchio.patchwork.board;

import lesagervecchio.patchwork.patch.Patch;
import lesagervecchio.patchwork.patch.Patches;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    return newSquare[0] > sizeX || newSquare[1] > sizeY;
  }


  /**
   *use contains and outOfBound to tell if a patch can be put on board
   * @param patch : newer patch trying to be placed on board.
   * @return can be placed or not
   */
  public boolean canBePlaced(Patch patch) {
    Objects.requireNonNull(patch, "patch is null");
    return patch.squares().stream().noneMatch(this::outOfBound) && !contains(patch);
  }

  /**
   * Put newPatch on the board
   * @return : succeeded or not
   */
  public boolean put(Patch newPatch) {
    Objects.requireNonNull(newPatch, "patch is null");

    if (!canBePlaced(newPatch)) {
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
}
