package eu.t6nn.gester.operations;

import eu.t6nn.gester.operations.impl.LoggerFeedbackStrategy;

public class Feedback {
	private Feedback() {
		
	}
	
	public static LoggerFeedbackStrategy log() {
		return new LoggerFeedbackStrategy();
	}
	
}
