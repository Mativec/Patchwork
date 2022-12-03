package lesagervecchio.patchwork.global;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import lesagervecchio.patchwork.patch.Patch;

public class GlobalPatches {
//Cet objet est seulement composé d'une liste d'id de patch.
//Le but de cet objet est renseigner sur l'ordre des patchs sur le plateau.
	
	private static int nbPatch;
	private ArrayList<Integer> orderPatches;
	private HashMap<Integer, Patch> patchesById;
	
	public GlobalPatches() {
//On initialise dans le constructeur le positionnement des patchs les uns
//par rapport aux autres.
// Ici on ne prend donc que les id (de 0 à nbPatch - 1)
		patchesById = new HashMap<Integer, Patch>();
		var path = Path.of("stockage_patchs");
		try(var reader = Files.newBufferedReader(path)){
			String line;
			String numbers[];
			//while((line = reader.readLine()) != null) {
			nbPatch = Integer.valueOf(line = reader.readLine());
			while((line = reader.readLine()) != null) {
				numbers = line.split(" ");
				System.out.println(numbers + " " + numbers[1] + " " + numbers[2] + " " + numbers[3]);
				System.out.println(Integer.toBinaryString(Integer.valueOf(numbers[5])));
				System.out.println(Integer.toBinaryString(Integer.valueOf(numbers[6])));
				System.out.println(Integer.toBinaryString(Integer.valueOf(numbers[7])));
				System.out.println(Integer.toBinaryString(Integer.valueOf(numbers[8])));
				patchesById.put(
						Integer.valueOf(
								numbers[0]
								), new Patch(
										List.of(
												new Integer[] {0, 0}, new Integer[]{1, 1}
										), Integer.valueOf(
												numbers[1]
										), Integer.valueOf(
												numbers[2]
										), Integer.valueOf(
												numbers[3]
										)
								)
						);
				
				//0 -> index, 1 -> coupbutton
				//2 -> temps, 3 -> buttonplateau
				//4 -> nbcases
				//5 -> 1er 5*bit, 6 -> 2e 5*bit
				//7 -> 3e 5*bit,  8 -> 4e 5*bit 
			}
		}catch(IOException e) {
			System.err.println(e.getMessage());
			System.out.println("Non : (");
			System.exit(1);
			return;
		}
		System.out.println("Ici --> " + patchesById.get(1));
		System.out.println("Ici --> " + patchesById.get(21));
		orderPatches = (ArrayList<Integer>) IntStream.range(0, nbPatch).boxed().collect(Collectors.toList());
		Random r = new Random();
		for(var i = 0; i < nbPatch; i ++) {
			int index = r.nextInt(orderPatches.size());
			int tempo = orderPatches.get(index);
			orderPatches.set(index, orderPatches.get(i));
			orderPatches.set(i, tempo);
		}
	}
	
	public void removePatch(int indexPatch) {
		if(indexPatch < 0 || indexPatch > nbPatch - 1) {
			throw new IllegalArgumentException("indexPatch < 0 or indexPatch > nbPatch");
		}
		orderPatches.remove(indexPatch);
	}
}
