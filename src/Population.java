/*
 * Tyler Lorella - Evolved Names
 * TCSS 342 - Spring 2019
 */

import java.util.ArrayList;

/**
 * Stores a number of different strings that will be mutated every cycle (day()). Evolutionary pressure is 
 * put on the strings to mutate in the desired target string found in the Genome class.
 * 
 * @author Tyler Lorella (TLorella@uw.edu)
 * 
 */
public class Population {


	public Genome mostFit;

	private ArrayList<Genome> geneList = new ArrayList<Genome>();

	/**
	 * Constructors a list of Genomes with size numGenomes and a mutation rate of mutationRate.
	 * @param numGenomes The number of Genomes stored.
	 * @param mutationRate The chance a genome may mutate.
	 */
	public Population (final Integer numGenomes, final Double mutationRate) {
		for (int i = 1; i <= numGenomes; i++) {
			Genome gene = new Genome(mutationRate);
			geneList.add(gene);
			mostFit = gene;
		}
	}

	/**
	 * Simulates a single breeding cycle on the population. The least fit half are killed, and the remainder
	 * either asexually or sexually and then are mutated. This continues until the population is restored to 
	 * the initial population size. Should function in O(n log n), implements the standard java ArrayList 
	 * sort method.
	 */
	public void day() {
		geneList.sort(null);
		mostFit = geneList.get(0);
		int geneHalfSize = (int) (geneList.size() / 2);

		for (int replace = geneHalfSize + 1; replace < geneList.size(); replace++) {
			int randoIndex = (int) (Math.random() * geneHalfSize);
			Genome gene = new Genome(geneList.get(randoIndex));
			if (Math.random() >= 0.5) {
				int randoOtherIndex = (int) (Math.random() * geneHalfSize);
				gene.crossover(geneList.get(randoOtherIndex));
			}
			gene.mutate();
			geneList.set(replace, gene);
		}

	} 

}
