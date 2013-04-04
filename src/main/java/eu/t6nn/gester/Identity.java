package eu.t6nn.gester;

import eu.t6nn.gester.utils.MutableBitBuffer;
import eu.t6nn.gester.variables.Variable;

public interface Identity {
	
	MutableBitBuffer encode();
	
	Identity clone(MutableBitBuffer newState);
	
	Variable getTrait(String name);
	
	int size();

	double test(TestCase source, long key);
}
