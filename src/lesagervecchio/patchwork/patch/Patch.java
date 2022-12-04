package lesagervecchio.patchwork.patch;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public record Patch(List<Integer[]> squares, int buttons, int turns, int bringedButtons) {

  public Patch {
    Objects.requireNonNull(squares, "squares is null");

    if (buttons < 0) {
      throw new IllegalArgumentException("buttons < 0");
    }

    if (turns < 0) {
      throw new IllegalArgumentException("turns < 0");
    }
    if (bringedButtons < 0) {
    	throw new IllegalArgumentException("bringedButtons < 0");
    }
  }


  /**
   * turn this to the right
   */
  public Patch right() {
    int tmp;
    int indent = 0;
    for (var coordinate : squares){
      indent = indent < coordinate[1]? coordinate[1] : indent;
      tmp = coordinate[0];
      coordinate[0] = -coordinate[1];
      coordinate[1] = tmp;
    }
    return Patches.move(this, indent, 0);
  }

  /**
   * Turn this to the left
   */
  public Patch left(){
    int tmp;
    int indent = 0;
    for (var coordinate : squares){
      indent = indent < coordinate[0]? coordinate[0] : indent;
      tmp = coordinate[1];
      coordinate[1] = -coordinate[0];
      coordinate[0] = tmp;
    }
    return Patches.move(this, 0, indent);
  }
  
  /**
   * Method that convert a list of string and three int in a Patch
   * @param listBin : a list of String that represent a patch disposition
   * @param buttons : the cost of the patch that will be created
   * @param turns : the number of time that will give the patch
   * @param bringedButtons : the number of buttons that the patch will bring to a player personnal board
   * @return the patch that will be created.
   */
  public static Patch binToPatch(List<String> listBin, int buttons, int turns, int bringedButtons) {
	  //Méthode tradusant un code binaire a 20 bits en un List<Integer[]>, represantant donc un patch
	  var list = new ArrayList<Integer[]>();
	  int y = 0;
	  for(var bits : listBin) {
		  String bin = Integer.toBinaryString(Integer.valueOf(bits));
		  bin = String.format("%5s", bin).replaceAll(" ", "0");
		  for(var x = 0; x < bin.length(); x ++) {
			  if(bin.charAt(x) == '1') {
				  list.add(new Integer[]{x, y});
			  }
		  }
		  y ++;
	  }
	  return new Patch(List.copyOf(list), buttons, turns, bringedButtons);
  }
  
  @Override
  public String toString() {
    var output = new StringBuilder();

    output.append("Patch : ");

    output.append("[");
    squares.forEach(square -> output.append("(").append(square[0]).append(",").append(square[1]).append(")"));
    output.append("]");

    output.append(", buttons: ");
    output.append(buttons);

    output.append(", turns: ");
    output.append(turns);

    return output.toString();
  }
}
