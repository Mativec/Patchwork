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

  public int getNbSquare() {
    return nbSquare;
  }

  public boolean contains(Patch newPatch) {
    Objects.requireNonNull(newPatch, "patch is null");
    for (Patch patch : board) {
      if (Patches.conflict(patch, newPatch)) {
        return true;
      }
    }
    return false;
  }

  public boolean outOfBound(Integer[] newSquare) {
    Objects.requireNonNull(newSquare, "square array is null");
    return newSquare[0] > sizeX || newSquare[1] > sizeY;
  }

  public boolean canBePlaced(Patch patch) {
    Objects.requireNonNull(patch, "patch is null");
    return patch.squares().stream().noneMatch(this::outOfBound) && !contains(patch);
  }

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
   * Convert the playerBoard as one Patch
   *
   * @return : a Patch with all squares on board
   */
  public Patch asOne() {
    List<Integer[]> list = new ArrayList<>();
    board.forEach(patch -> list.addAll(patch.squares()));
    return new Patch(list, 0, 0);
  }
}
