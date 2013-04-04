package eu.t6nn.gester;

import java.util.Map;

import eu.t6nn.gester.utils.MutableBitBuffer;
import eu.t6nn.gester.variables.Variable;

public interface IdentityDef {
	
	int size();

	Map<String, Variable> decode(MutableBitBuffer buffer);
}
