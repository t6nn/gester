package eu.t6nn.gester.operations;

import eu.t6nn.gester.Identity;
import eu.t6nn.gester.IdentityDef;

public interface InitializationStrategy {
	Identity initialize(IdentityDef idDef);
}
