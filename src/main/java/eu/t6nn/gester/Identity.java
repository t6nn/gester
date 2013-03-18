package eu.t6nn.gester;

public interface Identity {
	
	<T extends Variable> T getTrait(Class<T> traitType);
}
