package eu.t6nn.gester;

import eu.t6nn.gester.utils.BitBuffer;
import eu.t6nn.gester.variables.Variable;

public interface Identity {
	
	BitBuffer encode();
	
	Identity clone(BitBuffer newState);
	
	<T extends Variable> T getTrait(String name);
	
	int size();

	double test(TestCase source, long key);
	
	double lastCost();
}
