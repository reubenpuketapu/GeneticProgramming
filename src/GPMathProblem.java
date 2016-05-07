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

/**
 * 
 * @author Reuben Puketapu
 *
 */
public class GPMathProblem extends GPProblem {

	@SuppressWarnings("rawtypes")
	private static final Class OUTPUT_CLASS = CommandGene.FloatClass;

	private static List<Float> x;
	private static List<Float> y;

	private GPConfiguration config;

	private static Variable xVar;

	public GPMathProblem(GPConfiguration config, List<Float> x, List<Float> y) throws InvalidConfigurationException {
		super(config);
		this.config = config;
		GPMathProblem.x = x;
		GPMathProblem.y = y;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public GPGenotype create() throws InvalidConfigurationException {
		Class[] typesClasses = { OUTPUT_CLASS };
		Class[][] argTypes = { {} };
		
		CommandGene[][] functionSet = { { xVar = Variable.create(config, "x", OUTPUT_CLASS),
				new Add(config, OUTPUT_CLASS), 
				new Subtract(config, OUTPUT_CLASS), 
				new Multiply(config, OUTPUT_CLASS),
				new Divide(config, OUTPUT_CLASS),
				

				new Terminal(config, OUTPUT_CLASS, 1.0d, 10.0d, false),

				} };

		return GPGenotype.randomInitialGenotype(config, typesClasses, argTypes, functionSet, x.size(), true);
	}

	public static class MathFitnessFunction extends GPFitnessFunction {

		/**
		 * 
		 */
		private static final long serialVersionUID = 4789357466855442473L;

		protected double evaluate(IGPProgram igp) {

			double error = 0.0f;
			Object[] noargs = new Object[0];

			for (int i = 0; i < x.size(); i++) {

				xVar.set(x.get(i));

				double result = igp.execute_float(0, noargs);

				error += Math.pow((result - y.get(i)), 2);

				if (Double.isInfinite(error)) {
					return Double.MAX_VALUE;
				}

			}
			// error is small enough
			if (error < 0.001f) { 
				error = 0f;
			}

			return error/y.size();
		}

	}

}
