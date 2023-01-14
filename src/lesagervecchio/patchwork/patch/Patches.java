package lesagervecchio.patchwork.patch;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Mativec (Matias VECCHIO)
 * <p>
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
  public static int size(Patch patch) {
    Objects.requireNonNull(patch, "patch is null");
    return patch.squares().size();
  }


  /**
   * Method that convert a list of string and three int in a Patch
   *
   * @param listBin        : a list of String that represent a patch disposition
   * @param buttons        : the cost of the patch that will be created
   * @param turns          : the number of time that will give the patch
   * @param bringedButtons : the number of buttonCost that the patch will bring to a player personnal board
   * @return the patch that will be created.
   */
  public static Patch binToPatch(List<String> listBin, int buttons, int turns, int bringedButtons) {
    //Méthode traduisant un code binaire a 20 bits en un List<Integer[]>, represantant donc un patch
    int y = 0;
    var list = new ArrayList<Integer[]>();
    for (var bits : listBin) {
      String bin = Integer.toBinaryString(Integer.parseInt(bits));
      bin = String.format("%5s", bin).replaceAll(" ", "0");
      for (var x = 0; x < bin.length(); x++) {
        if (bin.charAt(x) == '1') {
          list.add(new Integer[]{x, y});
        }
      }
      y++;
    }
    return new Patch(List.copyOf(list), buttons, turns, bringedButtons);
  }
}
