package eu.t6nn.gester.operations;

import eu.t6nn.gester.Identity;
import eu.t6nn.gester.IdentityDef;

public interface InitializationStrategy extends OperationStrategy {
	Identity initialize(IdentityDef idDef);
}
