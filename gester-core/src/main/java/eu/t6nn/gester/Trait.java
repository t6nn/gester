package eu.t6nn.gester;

import eu.t6nn.gester.variables.Variable;


public interface Trait<T extends Variable>
{
	T var(Identity id);
}
