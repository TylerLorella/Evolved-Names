/*
 * Tyler Lorella - Evolved Names
 * TCSS 342 - Spring 2019
 */

/**
 * Launches the Evolved Names program, the target string to generate from a population is currently, 
 * "TYLER LORELLA". Several tester methods and runtime analysis algorithms are provided.
 * 
 * @author Tyler Lorella (TLorella@uw.edu)
 *
 */
public class Main {

	public static void main(String[] args) {
		Population pop = new Population(100, 0.05);
		int i = 0;
		long startTime = System.nanoTime();
		while (pop.mostFit.fitness() > 0) {
			System.out.println("Generation: " + i + ", Most Fit Genome: " + pop.mostFit);
			pop.day();
			i++;
		}
		long endTime = System.nanoTime();
		int time = (int) ((endTime - startTime) / 1_000_000);
		System.out.println("Generation: " + i + ", Most Fit Genome: " + pop.mostFit);
		System.out.println("Generations: " + i);
		System.out.println("Running Time: " + time + " milliseconds");

		//testGenome();
		//testPopulation();
		
		//runMutationTest();
		//runBigSpeedTest();
	}

	/**
	 * Tests all methods in the Genome class.
	 */
	private static void testGenome() {
		Genome genes = new Genome(0.4);
		Genome B = new Genome(0.4);

		System.out.println(genes.toString());

		for (int i = 0; i < 10; i++) {
			genes.mutate();
			B.mutate();
			System.out.println(genes.fitness() + " and " + genes.toString());
			System.out.println("genes vs B: " + genes.compareTo(B));
		}
		System.out.println("Cross");
		System.out.println("Parent A: " + genes.toString() + " Parent B: " + B.toString());
		genes.crossover(B);
		System.out.println(genes.toString());
		
		System.out.println("****************************");
		System.out.println("Note: the different fitness methods are not neccessarily suppose to return the same value.");
		System.out.println("Normal Fitness evalution: " + genes.fitness());
		System.out.println("Levenshtein edit distance fitness: " + genes.wagnerFitness());
		System.out.println("****************************");

		Genome test = new Genome(genes);
		System.out.println("Test Gene: " + genes);
		System.out.println("Test: " + test);
		test.mutate();
		test.mutate();
		System.out.println("Test: " + test);
		genes.mutate();
		genes.mutate();
		System.out.println("Test Bad: " + test);
		System.out.println("Test Gene: " + genes);
	}
	
	/**
	 * Tests all methods in the Population class.
	 */
	private static void testPopulation() {
		Population pop = new Population(100, 0.05);
		pop.day();
		System.out.println(pop);
		
		for (int i = 0; i < 100; i++) {
			pop.day();
			System.out.println(pop.mostFit.toString());
		}
	}
	
	/**
	 * Runs 100 trials to get a more accurate analysis of the performance of the program for the current
	 * target string.
	 */
	private static void runBigSpeedTest() {
		int totalGen = 0;
		int totalMS = 0;
		int samples = 100;
		for (int r = 0; r < samples; r++) {
			Population pop = new Population(100, 0.05);
			int i = 0;
			long startTime = System.nanoTime();
			while (pop.mostFit.fitness() > 0) {
				//System.out.println("Most Fit: " + pop.mostFit);
				pop.day();
				i++;
			}
			long endTime = System.nanoTime();
			int time = (int) ((endTime - startTime) / 1_000_000);
			
				System.out.println("Completed: " + r);
			
			totalGen = totalGen + i;
			totalMS = totalMS + time;
		}
		int avgGen = (int) (totalGen / samples);
		int avgMS = (int) (totalMS / samples);
		System.out.println("Mutation Rate: " + 0.05);
		System.out.println("Avg Number of Generations: " + avgGen);
		System.out.println("Avg Running Time: " + avgMS + " milliseconds");
	}

	/**
	 * Performance test on different populations with different mutation rates.
	 */
	private static void runMutationTest() {
		double mutationRate = 0.01;
		for (int k = 0; k < 20; k++) {

			int totalGen = 0;
			int totalMS = 0;
			int samples = 10;

			for (int r = 0; r < samples; r++) {
				Population pop = new Population(100, mutationRate);
				int i = 0;
				long startTime = System.nanoTime();
				while (pop.mostFit.fitness() > 0) {
					//System.out.println("Most Fit: " + pop.mostFit);
					pop.day();
					i++;
				}
				//System.out.println("Completed: " + r);
				long endTime = System.nanoTime();
				int time = (int) ((endTime - startTime) / 1_000_000);
				//System.out.println("Generations: " + i);
				//System.out.println("Running Time: " + time + " milliseconds");
				totalGen = totalGen + i;
				totalMS = totalMS + time;
			}

			int avgGen = (int) (totalGen / samples);
			int avgMS = (int) (totalMS / samples);
			System.out.println("Mutation Rate: " + mutationRate);
			System.out.println("Avg Generations: " + avgGen);
			System.out.println("Avg Running Time: " + avgMS + " milliseconds");
			
			mutationRate = mutationRate + 0.01;
		}
	}

}
