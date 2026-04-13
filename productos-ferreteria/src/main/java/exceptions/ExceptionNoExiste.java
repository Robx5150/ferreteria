package exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;


@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ExceptionNoExiste extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public ExceptionNoExiste(String mensaje) {
		super(mensaje);
	}

}
