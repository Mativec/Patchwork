package lesagervecchio.patchwork.display;

import lesagervecchio.patchwork.Patch;
import lesagervecchio.patchwork.PlayerBoard;

public interface DisplayService {
  void displaySquare();

  void displayPatch(Patch patch);

  void displayPlayerBoard(PlayerBoard board);
}
