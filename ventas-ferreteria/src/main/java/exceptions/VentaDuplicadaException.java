package exceptions;

public class VentaDuplicadaException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

    public VentaDuplicadaException(String message) {
        super(message);
    }

    public VentaDuplicadaException(String message, Throwable cause) {
        super(message, cause);
    }
	
	
	

}
