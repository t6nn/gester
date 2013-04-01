package eu.t6nn.gester.operations;

import eu.t6nn.gester.Identity;
import eu.t6nn.gester.TestCase;

public interface InitializationStrategy {
	Identity initialize(TestCase testCase);
}
