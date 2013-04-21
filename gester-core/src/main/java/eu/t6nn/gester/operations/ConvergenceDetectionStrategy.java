package eu.t6nn.gester.operations;

import eu.t6nn.gester.Population;

public interface ConvergenceDetectionStrategy extends OperationStrategy {
	boolean detect(Population pop, int genIndex);
}
