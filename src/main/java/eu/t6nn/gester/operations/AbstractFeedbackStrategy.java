package eu.t6nn.gester.operations;

import eu.t6nn.gester.Population;

public abstract class AbstractFeedbackStrategy implements FeedbackStrategy {

	public FeedbackStrategy chain(FeedbackStrategy with) {
		return new ChainedFeedback(this, with);
	}

	private final static class ChainedFeedback extends AbstractFeedbackStrategy {

		private FeedbackStrategy lhs;
		private FeedbackStrategy rhs;

		public ChainedFeedback(FeedbackStrategy lhs, FeedbackStrategy rhs) {
			this.lhs = lhs;
			this.rhs = rhs;
		}

		@Override
		public void afterInitialization(Population pop) {
			lhs.afterInitialization(pop);
			rhs.afterInitialization(pop);
		}

		@Override
		public void afterUpdate(Population pop, int generation) {
			lhs.afterUpdate(pop, generation);
			rhs.afterUpdate(pop, generation);
		}

		@Override
		public void afterConvergence(Population pop, int generation) {
			lhs.afterConvergence(pop, generation);
			rhs.afterConvergence(pop, generation);
		}

	}

}
