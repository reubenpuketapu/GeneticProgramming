import java.util.List;

import org.jgap.InvalidConfigurationException;
import org.jgap.gp.CommandGene;
import org.jgap.gp.GPFitnessFunction;
import org.jgap.gp.GPProblem;
import org.jgap.gp.IGPProgram;
import org.jgap.gp.function.Add;
import org.jgap.gp.function.Divide;
import org.jgap.gp.function.Multiply;
import org.jgap.gp.function.Subtract;
import org.jgap.gp.impl.GPConfiguration;
import org.jgap.gp.impl.GPGenotype;
import org.jgap.gp.terminal.Terminal;
import org.jgap.gp.terminal.Variable;

public class GPClassificationProblem extends GPProblem {

	private GPConfiguration config;
	private final static Class classType = CommandGene.IntegerClass;
	public static List<Patient> patients;

	private static Variable clumpThick;
	private static Variable cellSize;
	private static Variable cellShape;
	private static Variable mAdhesion;
	private static Variable epSize;
	private static Variable bNucl;
	private static Variable blChrom;
	private static Variable nNucl;
	private static Variable mitos;

	public GPClassificationProblem(List<Patient> patients, GPConfiguration config) {
		GPClassificationProblem.patients = patients;
		this.config = config;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public GPGenotype create() throws InvalidConfigurationException {
		Class[] typesClasses = { classType, classType };
		Class[][] argTypes = { {}, { classType, classType, classType } };

		CommandGene[][] functionSet = { { clumpThick = Variable.create(config, "clumpThick", classType),
				cellSize = Variable.create(config, "cellSize", classType),
				cellShape = Variable.create(config, "cellShape", classType),
				mAdhesion = Variable.create(config, "mAdhesion", classType),
				epSize = Variable.create(config, "epSize", classType),
				bNucl = Variable.create(config, "bNucl", classType),
				blChrom = Variable.create(config, "blChrom", classType),
				nNucl = Variable.create(config, "nNucl", classType),
				mitos = Variable.create(config, "mitos", classType),
				new Add(config, classType), new Subtract(config, classType),
				new Multiply(config, classType), new Divide(config, classType),
				new Terminal(config, classType, -10.0d, 10.0d, true),

				}, {} };

		return GPGenotype.randomInitialGenotype(config, typesClasses, argTypes, functionSet, 599, true);
	}

	public static class ClassificationFitnessFunction extends GPFitnessFunction {

		/**
		 * 
		 */
		private static final long serialVersionUID = 6292776977921419762L;

		@Override
		protected double evaluate(IGPProgram igp) {

			double correct = 0;

			for (int i = 0; i < GPClassificationProblem.patients.size(); i++) {

				clumpThick.set(patients.get(i).getCellSize());
				cellSize.set(patients.get(i).getCellSize());
				cellShape.set(patients.get(i).getCellShape());
				mAdhesion.set(patients.get(i).getmAdhesion());
				epSize.set(patients.get(i).getEpSize());
				bNucl.set(patients.get(i).getbNucl());
				blChrom.set(patients.get(i).getBlChrom());
				nNucl.set(patients.get(i).getnNucl());
				mitos.set(patients.get(i).getMitos());

				int result = igp.execute_int(0, new Object[0]);
				int classed = 0;

				if (result <= 0) {
					classed = 2;
				} else {
					classed = 4;
				}

				if (classed == patients.get(i).getClassed())
					correct++;

			}

			return correct / patients.size() * 100;
		}

	}

}
