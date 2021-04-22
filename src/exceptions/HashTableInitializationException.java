package exceptions;

public class HashTableInitializationException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public HashTableInitializationException(String message, Throwable e) {
		super(message, e);
		e.printStackTrace();
	}

	public HashTableInitializationException(String message) {
		super(message);
	}
	
	

}
