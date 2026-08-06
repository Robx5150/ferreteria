package exceptions;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GestionExcepciones {
	
	@ExceptionHandler(ExceptionNoExiste.class)
	public ProblemDetail errorNoExiste(ExceptionNoExiste e) {
				
	    ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);

	    pd.setTitle("Recurso no encontrado");
	    pd.setDetail(e.getMessage());
	    pd.setType(URI.create("https://ferreteria.com/errors/recurso-no-existe"));

	    return pd;
	}
	
	
	@ExceptionHandler(VentaDuplicadaException.class)
	public ProblemDetail handleDuplicado(VentaDuplicadaException e) { //Si devuelve ProblemDetail, no es necesario ResponseEntity

	    ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.CONFLICT);
	    	
        pd.setTitle("N° Factura duplicado");
        pd.setType(URI.create("https://ferreteria.com/errors/factura-duplicada"));

	    pd.setDetail(e.getMessage());

	    return pd;
	}
	
		
	//Para manejar errores de validación ocurridos @NotBlank, @Size, etc.
		@ExceptionHandler(MethodArgumentNotValidException.class)
	    public ResponseEntity<Map<String, String>> handleValidationErrors(
	            MethodArgumentNotValidException ex) {

	        Map<String, String> errors = new HashMap<>(); 

	        ex.getBindingResult() 
	          .getFieldErrors() 
	          .forEach(error -> 
	              errors.put(error.getField(), error.getDefaultMessage()) 
	          );

	        return ResponseEntity
	                .status(HttpStatus.BAD_REQUEST)
	                .body(errors);
	    }
		
		@ExceptionHandler(BusinessException.class)
	    public ProblemDetail handleBusinessException(BusinessException e) {

	        ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);

	        pd.setTitle("Regla de negocio violada");
	        pd.setType(URI.create("https://ferreteria.com/errors/business-rule"));
	        pd.setDetail(e.getMessage());

	        return pd;
	    }
			

}
