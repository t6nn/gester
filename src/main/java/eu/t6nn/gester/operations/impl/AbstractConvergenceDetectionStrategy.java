package eu.t6nn.gester.operations.impl;

import eu.t6nn.gester.Population;
import eu.t6nn.gester.operations.ConvergenceDetectionStrategy;

public abstract class AbstractConvergenceDetectionStrategy implements ConvergenceDetectionStrategy {
	
	public ConvergenceDetectionStrategy or(ConvergenceDetectionStrategy strategy) {
		return new DisjunctiveStrategy(this, strategy);
	}
	
	public ConvergenceDetectionStrategy and(ConvergenceDetectionStrategy strategy) {
		return new ConjunctiveStrategy(this, strategy);
	}
	
	private final static class DisjunctiveStrategy extends AbstractConvergenceDetectionStrategy {
		
		private ConvergenceDetectionStrategy lhs;
		private ConvergenceDetectionStrategy rhs;

		public DisjunctiveStrategy(ConvergenceDetectionStrategy lhs, ConvergenceDetectionStrategy rhs) {
			this.lhs = lhs;
			this.rhs = rhs;
		}

		@Override
		public boolean detect(Population pop, int genIndex) {
			return lhs.detect(pop, genIndex) || rhs.detect(pop, genIndex);
		}
	}
	
	private final static class ConjunctiveStrategy extends AbstractConvergenceDetectionStrategy {
		
		private ConvergenceDetectionStrategy lhs;
		private ConvergenceDetectionStrategy rhs;

		public ConjunctiveStrategy(ConvergenceDetectionStrategy lhs, ConvergenceDetectionStrategy rhs) {
			this.lhs = lhs;
			this.rhs = rhs;
		}

		@Override
		public boolean detect(Population pop, int genIndex) {
			return lhs.detect(pop, genIndex) && rhs.detect(pop, genIndex);
		}
	}
	
}
