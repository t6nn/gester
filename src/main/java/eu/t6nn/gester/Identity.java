package eu.t6nn.gester;

import eu.t6nn.gester.utils.MutableBitBuffer;

public interface Identity {
	
	MutableBitBuffer encode();
	
	Identity clone(MutableBitBuffer newState);
	
	Variable getTrait(String name);
	
	int size();

	double cachedCost(TestCase source, long key);
}
