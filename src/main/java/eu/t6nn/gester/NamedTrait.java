package eu.t6nn.gester;

import eu.t6nn.gester.variables.Variable;


public class NamedTrait<T extends Variable> implements Trait<T>
{
	
	private String name;
	private Class<T> type;

	public NamedTrait (String name, Class<T> type) {
		this.name = name;
		this.type = type;
	}

	@Override
	public T var (Identity id) {
		Variable var = id.getTrait(name);
		if(type.isAssignableFrom(var.getClass())) {
			return type.cast(var);
		} else {
			throw new IllegalArgumentException(name + " is not of type " + type);
		}
	}
	

}
