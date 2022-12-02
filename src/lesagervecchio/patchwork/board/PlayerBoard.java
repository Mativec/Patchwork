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
    return newSquare[0] > sizeX && newSquare[1] > sizeY;
  }

  public boolean canBePlaced(Patch patch) {
    return patch.squares().stream().anyMatch(this::outOfBound) || contains(patch);
  }

  public boolean put(Patch newPatch) {
    Objects.requireNonNull(newPatch, "patch is null");

    if (canBePlaced(newPatch)) {
      return false;
    } else {
      board.add(newPatch);
      nbSquare += Patches.size(newPatch);
      return true;
    }
  }
}
