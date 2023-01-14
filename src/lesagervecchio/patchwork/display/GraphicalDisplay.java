package lesagervecchio.patchwork.display;

import lesagervecchio.patchwork.board.PlayerBoard;
import lesagervecchio.patchwork.global.GlobalPatches;
import lesagervecchio.patchwork.patch.Patch;
import lesagervecchio.patchwork.player.Player;

import java.util.ArrayList;

public final class GraphicalDisplay implements DisplayService{
  @Override
  public void drawPatch(Patch patch) {

  }

  @Override
  public void drawPlayerBoard(PlayerBoard board) {

  }

  @Override
  public void drawGlobalBoard(ArrayList<Player> players) {

  }

  @Override
  public void drawOrderPatches(GlobalPatches globalPatches) {

  }

  @Override
  public void drawText(Object... objects) {

  }

  @Override
  public String waitText() {
    return null;
  }

  @Override
  public char waitInput() {
    return 0;
  }

  @Override
  public void close() {

  }
}
