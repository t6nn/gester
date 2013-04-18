package eu.t6nn.gester.operations;

import eu.t6nn.gester.Population;

public interface ConvergenceDetectionStrategy {
	boolean detect(Population pop, int genIndex);
}
