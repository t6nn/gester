package eu.t6nn.gester.operations.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.t6nn.gester.Population;

public class LoggerFeedbackStrategy extends AbstractFeedbackStrategy {

	private static final Logger LOG = LoggerFactory.getLogger(LoggerFeedbackStrategy.class);
	
	@Override
	public void afterInitialization(Population pop) {
		LOG.info("Init complete");
	}

	@Override
	public void afterUpdate(Population pop, int generation) {
		
	}

	@Override
	public void afterConvergence(Population pop, int generation) {
		LOG.info("Test converged after {} iterations", generation);
		LOG.info("Best solution: {}", pop.get(0));
	}

}
