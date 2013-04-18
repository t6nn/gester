package eu.t6nn.gester;

public interface Population {
	
	int identitySize();
	
	int size();
	
	Identity remove(int i);
	
	Identity get(int i);
	
	void add(Identity id);
	
	void update();
}
