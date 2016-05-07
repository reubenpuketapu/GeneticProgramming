import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import org.jgap.InvalidConfigurationException;
import org.jgap.gp.impl.DefaultGPFitnessEvaluator;
import org.jgap.gp.impl.GPConfiguration;
import org.jgap.gp.impl.GPGenotype;

public class GPClassification {

	private List<Patient> allData;

	private List<Patient> training;
	private List<Patient> test;

	private int maxGenerations = 500;

	public GPClassification(String trainingData, String testData) {

		this.allData = new ArrayList<>();

		this.training = new ArrayList<>();
		this.test = new ArrayList<>();

		// Can use these methods then auto split the files randomly
		// readFile(trainingData, allData);
		// splitPatients();

		readFile(trainingData, training);
		readFile(testData, test);

		System.out.printf("Read files: %s and %s successfully\n", trainingData, testData);
		doAlgorithm();
	}

	private void doAlgorithm() {

		try {
			GPConfiguration config = new GPConfiguration();

			config.setFitnessEvaluator(new DefaultGPFitnessEvaluator());
			config.setPopulationSize(2000);
			config.setMaxInitDepth(6);
			config.setFitnessFunction(new GPClassificationProblem.ClassificationFitnessFunction());

			config.setMutationProb(0.05f);
			config.setCrossoverProb(0.90f);
			config.setReproductionProb(0.05f);

			GPClassificationProblem classificationProblem = new GPClassificationProblem(training, config);
			GPGenotype geneticProgram = classificationProblem.create();

			geneticProgram.setVerboseOutput(true);

			boolean done = false;
			for (int i = 0; i < maxGenerations; i++) {
				geneticProgram.evolve(1);

				if (geneticProgram.getAllTimeBest() != null
						&& geneticProgram.getAllTimeBest().getFitnessValue() >= 97) {
					System.out.println("\n\nStopped at " + i + " generations");
					System.out.printf("Fitness value: %.2f", geneticProgram.getAllTimeBest().getFitnessValue());
					System.out.println("%");
					done = true;
					break;
				}
			}

			if (!done) {
				System.out.println("\n\nStopped at max generations");
				System.out.printf("Fitness value: %.2f", geneticProgram.getAllTimeBest().getFitnessValue());
				System.out.println("%");
			}

			geneticProgram.outputSolution(geneticProgram.getAllTimeBest());

			GPClassificationProblem.patients = test;
			double correctness = new GPClassificationProblem.ClassificationFitnessFunction()
					.evaluate(geneticProgram.getAllTimeBest());

			System.out.print("\n\nPercentage correct on test set: ");
			System.out.printf("%.2f", correctness);
			System.out.println("%");
			System.out.println("From " + test.size() + " patients");

		} catch (InvalidConfigurationException e) {
			e.printStackTrace();
		}

	}

	@SuppressWarnings("unused")
	private void splitPatients() {

		for (int i = 0; i < 1000; i++) {
			Collections.shuffle(allData);
		}

		training.addAll(allData.subList(0, 599));
		test.addAll(allData.subList(599, allData.size()));

	}

	private void readFile(String filename, List<Patient> list) {
		try {
			Scanner sc = new Scanner(new File(filename));

			while (sc.hasNextLine()) {
				String lineString = sc.nextLine();
				Scanner line = new Scanner(lineString);
				line.useDelimiter(",");

				while (line.hasNext()) {

					int id = line.nextInt();

					int clumpThick = line.nextInt();
					int cellSize = line.nextInt();
					int cellShape = line.nextInt();
					int mAdhesion = line.nextInt();
					int epSize = line.nextInt();
					int bNucl = line.nextInt();
					int blChrom = line.nextInt();
					int nNucl = line.nextInt();
					int mitos = line.nextInt();
					int classed = line.nextInt();

					list.add(new Patient(id, clumpThick, cellSize, cellShape, mAdhesion, epSize, bNucl, blChrom, nNucl,
							mitos, classed));
				}
				line.close();
			}

			sc.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		// arg0 training data
		// arg1 test data
		new GPClassification(args[0], args[1]);
	}

}
