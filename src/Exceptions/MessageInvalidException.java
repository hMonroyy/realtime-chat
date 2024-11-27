package Exceptions;

public class MessageInvalidException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String codigo;

	public MessageInvalidException(String message, String codigo) {
		super(message);
		this.codigo = codigo;
	}

}
