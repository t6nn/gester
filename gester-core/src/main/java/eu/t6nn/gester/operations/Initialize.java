package eu.t6nn.gester.operations;

import eu.t6nn.gester.operations.impl.RandomInitializationStrategy;

public class Initialize {
	
	private Initialize() {
		
	}
	
	public static RandomInitializationStrategy random() {
		return new RandomInitializationStrategy();
	}
}
