package eu.t6nn.gester;

public interface TestCase {

	public enum Process {
		CONTINUE, STOP;
	}
	
	double calculateCost(Identity identity);
	
	Identity newIdentity();
	
	Process tick(Population pop);
}
