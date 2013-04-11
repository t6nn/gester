package eu.t6nn.gester.operations;

import eu.t6nn.gester.Population;

public interface FeedbackStrategy {
	
	void afterInitialization(Population pop);
	
	void afterUpdate(Population pop, int generation);
	
	void afterConvergence(Population pop, int generation);
}
