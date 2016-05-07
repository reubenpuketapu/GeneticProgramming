import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.jgap.gp.impl.DeltaGPFitnessEvaluator;
import org.jgap.gp.impl.GPConfiguration;
import org.jgap.gp.impl.GPGenotype;

/**
 * 
 * @author Reuben Puketapu
 *
 */
public class GPRegression {

	private List<Float> x = new ArrayList<>();
	private List<Float> y = new ArrayList<>();

	private int maxGenerations = 2000;

	public GPRegression(String input) {
		readFile(input);
		doAlgorithm();
	}

	private void doAlgorithm() {

		try {
			GPConfiguration config = new GPConfiguration();
			config.setGPFitnessEvaluator(new DeltaGPFitnessEvaluator());
			config.setMaxInitDepth(6);
			config.setPopulationSize(4000);

			config.setFitnessFunction(new GPMathProblem.MathFitnessFunction());
			config.setMutationProb(0.05f);
			config.setCrossoverProb(0.90f);
			config.setReproductionProb(0.05f);

			GPMathProblem problem = new GPMathProblem(config, x, y);
			GPGenotype genotype = problem.create();
			
			genotype.setVerboseOutput(true);
			
			for (int i = 0; i < maxGenerations; i++) {

				genotype.evolve(1);

				if (genotype.getAllTimeBest() != null && genotype.getAllTimeBest().getFitnessValue() < 0.1) {
					System.out.println("\nFound a program with fitness of 0.0 after " + i + " generations\n");
					break;
				}

			}
			
			genotype.outputSolution(genotype.getAllTimeBest());

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void readFile(String input) {

		try {
			Scanner scanner = new Scanner(new File(input));

			scanner.nextLine();
			scanner.nextLine();

			while (scanner.hasNext()) {
				x.add(scanner.nextFloat());
				y.add(scanner.nextFloat());
			}

			System.out.println("Succesfully read " + y.size() + " items from " + input);
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} 

	}

	public static void main(String[] args) {
		//regression.txt
		new GPRegression(args[0]);
	}
}
