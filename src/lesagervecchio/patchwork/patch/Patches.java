package lesagervecchio.patchwork.patch;

import java.util.Collections;
import java.util.Objects;

public class Patches {
  /**
   * Move a Patch.
   */
  public static void move(Patch patch, int targetX, int targetY){
    Objects.requireNonNull(patch, "patch is null");
    for(var coordinate : patch.squares()){
      coordinate[0] = coordinate[0] + targetX;
      coordinate[1] = coordinate[1] + targetY;
    }
  }

  /**
   * turn the Patch on the right or the left.
   */
  public static void left(Patch patch, boolean left){
    Collections.reverse(patch.squares());
    patch.squares().forEach(coordinate -> move(patch, coordinate[1], coordinate[0]));
  }


  /**
   * Return True if p1 and p2 share a square
   */
  public static boolean conflict(Patch p1, Patch p2){
    Objects.requireNonNull(p1, "p1 is null");
    Objects.requireNonNull(p2, "p2 is null");
    for (Integer[] i1: p1.squares()) {
      for (Integer[] i2: p2.squares()) {
        if(i1[0].equals(i2[0]) && i1[1].equals(i2[1])){
          return true;
        }
      }
    }
    return false;
  }

  public static long size(Patch patch) {
    return patch
            .squares()
            .size()
            ;
  }
}
