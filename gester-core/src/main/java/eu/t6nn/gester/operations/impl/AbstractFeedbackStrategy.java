package eu.t6nn.gester.operations.impl;

import eu.t6nn.gester.Gester;
import eu.t6nn.gester.Population;
import eu.t6nn.gester.TestLifecycleListener;
import eu.t6nn.gester.operations.FeedbackStrategy;

public abstract class AbstractFeedbackStrategy implements FeedbackStrategy, TestLifecycleListener {

	public FeedbackStrategy chain(FeedbackStrategy with) {
		return new ChainedFeedback(this, with);
	}
	
	@Override
	public void beforeRun(Gester gester) {
	}
	
	@Override
	public void afterRun(Gester gester) {
		
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

		@Override
		public void afterPrune(Population pop, int generation) {
			lhs.afterPrune(pop, generation);
			rhs.afterPrune(pop, generation);
		}

		@Override
		public void beforeRun(Gester gester) {
			if(lhs instanceof TestLifecycleListener) {
				((TestLifecycleListener) lhs).beforeRun(gester);
			}
			if(rhs instanceof TestLifecycleListener) {
				((TestLifecycleListener) rhs).beforeRun(gester);
			}
		}

		@Override
		public void afterRun(Gester gester) {
			if(lhs instanceof TestLifecycleListener) {
				((TestLifecycleListener) lhs).afterRun(gester);
			}
			if(rhs instanceof TestLifecycleListener) {
				((TestLifecycleListener) rhs).afterRun(gester);
			}
		}

	}

}
