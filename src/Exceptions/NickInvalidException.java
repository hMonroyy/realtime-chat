package Exceptions;

public class NickInvalidException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String codigo;

	public NickInvalidException(String message, String codigo) {
		super(message);
		this.codigo = codigo;
	}
	
}
