package glink.exceptions;

public class GlinkException extends Exception {
	
	private static final long serialVersionUID = 37703344227453170L;

	public GlinkException(String message, Exception e) {
		super(message, e);		
	}
	

}
