package eu.t6nn.gester;

public interface Variable {

	/**
	 * Gets the encoded size of the variable in bits. This value needs to be
	 * constant throughout a test case.
	 * 
	 * @return
	 */
	int size();

	/**
	 * Gets the encoded version of the variable.
	 * 
	 * @return
	 */
	byte[] encode();

	/**
	 * Returns a clone of the variable, with a new state.
	 * 
	 * @param newState
	 */
	Variable clone(byte[] newState);
}
