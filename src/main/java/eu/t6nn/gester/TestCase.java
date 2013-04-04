package eu.t6nn.gester;

public interface TestCase {

	public enum Process {
		CONTINUE, STOP;
	}
	
	double test(Identity identity);
	
	Process tick(Population pop);
}
