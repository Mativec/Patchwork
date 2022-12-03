package lesagervecchio.patchwork.global;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GlobalPatches {
//Cet objet est seulement composé d'une liste d'id de patch.
//Le but de cet objet est renseigner sur l'ordre des patchs sur le plateau.
	
	private static int nbPatch;
	private ArrayList<Integer> patches;
	
	public GlobalPatches() {
//On initialise dans le constructeur le positionnement des patchs les uns
//par rapport aux autres.
// Ici on ne prend donc que les id (de 0 à nbPatch - 1)
		var path = Path.of("stockage_patchs");
		try(var reader = Files.newBufferedReader(path)){
			String line;
			String numbers[];
			//while((line = reader.readLine()) != null) {
			nbPatch = Integer.valueOf(line = reader.readLine());
			while((line = reader.readLine()) != null) {
				numbers = line.split(" ");
				//0 -> index, 1 -> coupbutton
				//2 -> temps, 4 -> buttonplateau
				//5 -> nbcases
			}
		}catch(IOException e) {
			System.err.println(e.getMessage());
			System.out.println("Non : (");
			System.exit(1);
			return;
		}
		patches = (ArrayList<Integer>) IntStream.range(0, nbPatch).boxed().collect(Collectors.toList());
		Random r = new Random();
		for(var i = 0; i < nbPatch; i ++) {
			int index = r.nextInt(patches.size());
			int tempo = patches.get(index);
			patches.set(index, patches.get(i));
			patches.set(i, tempo);
		}
	}
	
	public void removePatch(int indexPatch) {
		if(indexPatch < 0 || indexPatch > nbPatch - 1) {
			throw new IllegalArgumentException("indexPatch < 0 or indexPatch > nbPatch");
		}
		patches.remove(indexPatch);
	}
}
