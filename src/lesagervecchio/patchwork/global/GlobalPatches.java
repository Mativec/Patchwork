package lesagervecchio.patchwork.global;

import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import lesagervecchio.patchwork.display.DisplayService;
import lesagervecchio.patchwork.patch.Patch;
import lesagervecchio.patchwork.patch.Patches;
import lesagervecchio.patchwork.player.Player;

/**
 * Class about the patches and their storage
 *
 * @author Paul LE SAGER
 * @author Mativec (Matias VECCHIO)
 */

public class GlobalPatches {
//Cet objet est seulement composé d'une liste d'id de patch.
//Le but de cet objet est renseigner sur l'ordre des patchs sur le plateau.

  private static int nbPatch;
  private ArrayList<Integer> orderPatches;
  private final HashMap<Integer, Patch> patchesById;
  private final DisplayService displayService;
  private final Patch specialPatch;

  /**
   * Initialization of the class 'GlobalPatches'
   */
  public GlobalPatches(String deck, DisplayService displayService) {
    //On initialise dans le constructeur le positionnement des patchs les uns
    //par rapport aux autres.
    // Ici on ne prend donc que les id (de 0 à nbPatch - 1)
    Objects.requireNonNull(deck, "deck is null");
    Objects.requireNonNull(displayService, "no display Service Chosen");

    this.displayService = displayService;
    specialPatch = Patches.binToPatch(List.of("16", "0", "0", "0"), 0, 0, 0);
    patchesById = new HashMap<>();
    var path = Path.of("res/" + deck);
    try (var reader = Files.newBufferedReader(path)) {
      String line;
      String[] numbers;
      nbPatch = Integer.parseInt(reader.readLine());
      while ((line = reader.readLine()) != null) {
        numbers = line.split(" ");

        patchesById.put(
          Integer.valueOf(numbers[0]),
          Patches.binToPatch(
            List.of(numbers[5], numbers[6], numbers[7], numbers[8]),
            Integer.parseInt(numbers[1]),
            Integer.parseInt(numbers[2]),
            Integer.parseInt(numbers[3])
          )
        );
        //0 -> index, 1 -> coupbutton
        //2 -> temps, 3 -> buttonplateau
        //4 -> nbcases
        //5 -> 1er 5*bit, 6 -> 2e 5*bit
        //7 -> 3e 5*bit,  8 -> 4e 5*bit
      }
    } catch (IOException e) {
      System.err.println(e.getMessage());
      displayService.drawText("Non : (");
      System.exit(1);
      return;
    }
    orderPatches = (ArrayList<Integer>) IntStream.range(0, nbPatch).boxed().collect(Collectors.toList());
    Random r = new Random();
    for (var i = 0; i < nbPatch; i++) {
      int index = r.nextInt(orderPatches.size());
      int tempo = orderPatches.get(index);
      orderPatches.set(index, orderPatches.get(i));
      orderPatches.set(i, tempo);
    }
  }

  public ArrayList<Integer> getOrderPatches() {
    return orderPatches;
  }

  public HashMap<Integer, Patch> getPatchesById() {
    return patchesById;
  }

  /**
   * Method that removes the indexPatch patch from orderPatches.
   *
   * @param indexPatch : int representing the index of the right patch to remove from orderPatches
   */
  @Deprecated
  public void removePatch(int indexPatch) {
    if (indexPatch < 0 || indexPatch > nbPatch - 1) {
      throw new IllegalArgumentException("indexPatch < 0 or indexPatch > nbPatch");
    }
    orderPatches.remove(indexPatch);
  }

  /**
   * Method that check for the price to buy the Patch put in parameter
   *
   * @param player : the player that wish to buy the patch
   * @param index  : an int that represent the location of the needed patch in patchesById
   * @return : false if the player can not afford to buy the patch, and true if else.
   */
  public boolean checkPricePatch(Player player, int index) {
    return (patchesById.get(orderPatches.get(index))).buttonCost() <= player.jetons();
  }

  /**
   * Method call of the other method needed to a player to buy a patch
   *
   * @param player : the player that want (and can) buy a patch
   * @param index  : an int that represent the location of the needed patch in patchesById
   * @return : the new version of the player, with less buttonCost to spend and more patches
   */
  public Player buyPatch(Player player, int index) {
    // on considere qu'en rentrant dans cette fonction, le nombre de jetons du joueur est valide
    Objects.requireNonNull(player, "player is null");
    Patch oldPatch;
    if(index != -1) {
    	oldPatch = patchesById.get(orderPatches.get(index));
    }else {
    	oldPatch = specialPatch;
    }
    player = player.updateJetons(oldPatch.buttonCost());
    player = player.movePlayer(oldPatch.turns());
    //Ensuite, on supprime oldPatch de globalPatches
    if(index != -1) {
    	patchesById.remove(orderPatches.get(index));
    	orderPatches.remove(index);
    }
    //Et on envoie dans le plateau du joueur le vieux patch ici!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    player.playerBoard().patchPlacePhase(displayService, oldPatch);
    return player;
  }
}



