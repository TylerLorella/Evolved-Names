/*
 * Tyler Lorella - Evolved Names
 * TCSS 342 - Spring 2019
 */

import java.util.ArrayList;
import java.util.Objects;

/**
 * Representation of a specific string that is stored as an array of chars. Contains the target string that 
 * all young strings hope to grow up to be one day. Implements Comparable based off the fitness of this 
 * Genome. The fitness is a measure of how close this Genome is to the target string.
 * 
 * @author Tyler Lorella (TLorella@uw.edu)
 *
 */
public class Genome implements Comparable<Genome> {

	public static final String target = "TYLER LORELLA";

	private ArrayList<Character> stringInList = new ArrayList<Character>();

	private final static char[] CHAR_LIST = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
			'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', ' ', '-', '\'' };

	private final double mutationRate;

	/**
	 * Assigns mutationRate as the percent chance that the Genome's string will undergo change. Default
	 * Genome string is "A".
	 * @param mutationRate The mutation rate of this Genome.
	 */
	public Genome(final double mutationRate) {
		this.mutationRate = mutationRate;
		stringInList.add('A');
	}

	/**
	 * Copy constructor, builds an exact immutable copy of the parent string and mutation rate.
	 * @param gene The other gene to copy.
	 */
	public Genome(Genome gene) {
		this.mutationRate = gene.mutationRate;
		for (int i = 0; i < gene.stringInList.size(); i++) {
			this.stringInList.add(gene.stringInList.get(i));
		}
	}

	/**
	 * Performs the following changes with a percent chance equal to the mutation rate. Add a random 
	 * character to a random position in the string representation, Delete a character at random, or for 
	 * each character the chance that it will be replaced with a random character. All of these operations 
	 * may happen. O(n).
	 */
	public void mutate() {
		//add
		if (Math.random() <= mutationRate) {
			stringInList.add(randoStringIndex(), randoChar());
		}

		//delete
		if (Math.random() <= mutationRate && stringInList.size() >= 2) {
			stringInList.remove(randoStringIndex());
		}

		//change for each character in the string
		for (int index = 0; index < stringInList.size(); index++) {
			if (Math.random() <= mutationRate) {
				stringInList.set(index, randoChar());
			}
		}

	}
	
	/**
	 * Sexually breeds the other Genome and the calling Genome, randomly copies a character from a parent, 
	 * if the selected parent doesn't have a character to copy, then the crossover finishes and replaces the 
	 * current string.
	 * @param other The other parent.
	 */
	public void crossover(final Genome other) {
		//parent a is this.stringInList and parent b is other.stringInList
		ArrayList<Character> tempList = new ArrayList<Character>();
		int pullingIndex = 0;
		while(true) {

			if (Math.random() >= 0.5) { //Parent A
				if (pullingIndex  < this.stringInList.size()) {
					tempList.add(this.stringInList.get(pullingIndex));
				} else {
					break;
				}
			} else { //Parent B
				if (pullingIndex  < other.stringInList.size()) {
					tempList.add(other.stringInList.get(pullingIndex));
				} else {
					break;
				}
			}
			pullingIndex++;
		}
		this.stringInList = tempList;
	}

	/**
	 * Heuristic that decides how close the Genome's string is compared to the target string. Difference in 
	 * length is penalized twice and incorrect character is once (for each incorrect character) this pushes 
	 * breeding algorithm to find the correct length faster. A fitness of 0 means the target string has been 
	 * generated.
	 * @return Integer fitness value.
	 */
	public Integer fitness() {
		int n = stringInList.size();
		int m = target.length();
		int l = Math.max(n, m);
		int fitness = Math.abs(n - m);

		for (int index = 0; index < l; index++) {
			if (stringInList.size() <= index || target.length() <= index) {
				fitness++;
			} else if (!stringInList.get(index).equals(target.charAt(index))) {
				fitness++;
			}
		}

		return fitness;
	}

	/**
	 * A secondary fitness algorithm that uses Wagner-Fischer algorithm for calculating the 
	 * Levenshtein edit distance. In practice it's slower, was implemented for extra credit.
	 * @return Integer fitness value.
	 */
	public Integer wagnerFitness() {
		int n = stringInList.size();
		int m = target.length();
		int[][] wagner = new int[n+1][m+1];
		for (int i = 1; i <= n; i++) {
			wagner[i][0] = i;
		}
		for (int i = 1; i <= m; i++) {
			wagner[0][i] = i;
		}
		for (int x = 1; x <= n; x++) {
			for (int y = 1; y <= m; y++) {
				if (stringInList.get(x-1).equals(target.charAt(y-1))) {
					wagner[x][y] = wagner[x-1][y-1];
				} else {
					int temp = Math.min(wagner[x-1][y]+1, wagner[x][y-1]+1);
					wagner[x][y] = Math.min(temp, wagner[x-1][y-1]+1);
				}
			}
		}
		return (int) (wagner[n][m] + (Math.abs(n-m) + 1) / 2);
	}

	/**
	 * Returns the string representation of this Genome with it's current fitness.
	 */
	@Override
	public String toString() {
		return stringInList.toString() + ", " + fitness();
	}

	/**
	 * Compares this Genome object to another Genome object, uses the natural ordering of the fitness Integer
	 *  for comparision.
	 */
	@Override
	public int compareTo(final Genome theOtherGenome) {
		int otherFit = Objects.requireNonNull(theOtherGenome).fitness();
		int thisFit = this.fitness();
		if (thisFit < otherFit) return -1;
		else if (thisFit == otherFit) return 0;
		else return 1;
	}

	private int randoStringIndex() {
		return (int) (Math.random() * stringInList.size());
	}

	private char randoChar() {
		return CHAR_LIST[(int) (Math.random() * CHAR_LIST.length)];
	}

}
