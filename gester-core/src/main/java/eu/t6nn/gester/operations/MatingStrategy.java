package eu.t6nn.gester.operations;

import java.util.Collection;

import eu.t6nn.gester.Identity;

public interface MatingStrategy extends OperationStrategy {
	
	Collection<Identity> mate(Identity id1, Identity id2);
}
