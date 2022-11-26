package lesagervecchio.patchwork.global;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GlobalPatches {
//Cet objet est seulement composé d'une liste d'id de patch.
//Le but de cet objet est renseigner sur l'ordre des patchs sur le plateau.
	
	private ArrayList<Integer> patches;
	
	public GlobalPatches() {
//On initialise dans le constructeur le positionnement des patchs les uns
//par rapport aux autres.
// Ici on ne prend donc que les id (de 0 à 39)
		patches = (ArrayList<Integer>) IntStream.range(0, 40).boxed().collect(Collectors.toList());
		Random r = new Random();
		for(var i = 0; i < 40; i ++) {
			int index = r.nextInt(patches.size());
			int tempo = patches.get(index);
			patches.set(index, patches.get(i));
			patches.set(i, tempo);
		}
	}
	
	public void removePatch(int indexPatch) {
		if(indexPatch < 0 || indexPatch > 39) {
			throw new IllegalArgumentException("indexPatch < 0 or indexPatch > 39");
		}
		patches.remove(indexPatch);
	}
}
