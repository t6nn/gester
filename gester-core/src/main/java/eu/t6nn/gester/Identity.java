package eu.t6nn.gester;

import java.util.Map;

import eu.t6nn.gester.utils.BitBuffer;
import eu.t6nn.gester.variables.Variable;

public interface Identity {
	
	BitBuffer encode();
	
	Map<String, Variable> decode();
	
	Identity clone(BitBuffer newState);
	
	<T extends Variable> T getTrait(String name);
	
	int size();

	double test(TestCase source, long key);
	
	double lastCost();
}
