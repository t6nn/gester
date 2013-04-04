package eu.t6nn.gester;

public interface TestCase {

	public enum Process {
		CONTINUE, STOP;
	}
	
	double test(Identity identity);
	
	Identity newIdentity();
	
	Process tick(Population pop);
}
