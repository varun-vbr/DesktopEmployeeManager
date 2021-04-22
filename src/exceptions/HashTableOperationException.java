package exceptions;

public class HashTableOperationException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public HashTableOperationException(String message, Throwable cause) {
		super(message, cause);
		cause.printStackTrace();
	}

	public HashTableOperationException(String message) {
		super(message);
	}
	

}
