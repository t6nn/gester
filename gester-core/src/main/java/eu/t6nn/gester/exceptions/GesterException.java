package eu.t6nn.gester.exceptions;


public class GesterException extends RuntimeException
{

	private static final long serialVersionUID = 5861434844655021264L;

	public GesterException () {
	}

	public GesterException (String message) {
		super(message);
	}

	public GesterException (Throwable cause) {
		super(cause);
	}

	public GesterException (String message, Throwable cause) {
		super(message, cause);
	}

}
