package lesagervecchio.patchwork.patch;

import java.util.Objects;

/**
 * @author Mativec (Matias VECCHIO)
 *
 * Some static methods on Patch
 */
public class Patches {
  /**
   * Move a patch
   *
   * @param patch   : The Patch we want to move
   * @param targetX : X coordinate where we want the Patch
   * @param targetY : Y coordinate where we want the Patch
   * @return patch moved
   */
  public static Patch move(Patch patch, int targetX, int targetY) {
    Objects.requireNonNull(patch, "patch is null");
    for (var coordinate : patch.squares()) {
      coordinate[0] = coordinate[0] + targetX;
      coordinate[1] = coordinate[1] + targetY;
    }
    return patch;
  }

  /**
   * Return True if p1 and p2 share a square
   */
  public static boolean conflict(Patch p1, Patch p2) {
    Objects.requireNonNull(p1, "p1 is null");
    Objects.requireNonNull(p2, "p2 is null");
    for (Integer[] i1 : p1.squares()) {
      for (Integer[] i2 : p2.squares()) {
        if (i1[0].equals(i2[0]) && i1[1].equals(i2[1])) {
          return true;
        }
      }
    }
    return false;
  }


  /**
   * Return how many squares composed patch
   */
  public static long size(Patch patch) {
    Objects.requireNonNull(patch, "patch is null");
    return patch
      .squares()
      .size()
      ;
  }
}
