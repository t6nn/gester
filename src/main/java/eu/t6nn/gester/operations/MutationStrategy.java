package eu.t6nn.gester.operations;

import eu.t6nn.gester.Identity;

public interface MutationStrategy {
	Identity mutate(Identity identity);
}
