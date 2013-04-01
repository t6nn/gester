package eu.t6nn.gester;

public interface TestCase {

	double calculateCost(Identity identity);
	
	Identity newIdentity();
}
