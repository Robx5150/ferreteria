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

import exceptions.ProductoDuplicadoException.TipoDuplicado;

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
	
	
	
	 
	@ExceptionHandler(ProductoDuplicadoException.class)
	public ProblemDetail handleDuplicado(ProductoDuplicadoException e) { //Si devuelve ProblemDetail, no es necesario ResponseEntity

	    ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.CONFLICT);

	    if (e.getTipo() == TipoDuplicado.SKU) {
	    	
	        pd.setTitle("SKU duplicado");
	        pd.setType(URI.create("https://ferreteria.com/errors/sku-duplicado"));
	        
	    } else if (e.getTipo() == TipoDuplicado.NOMBRE) {
	    	
	        pd.setTitle("Nombre duplicado");
	        pd.setType(URI.create("https://ferreteria.com/errors/nombre-duplicado"));
	    }

	    pd.setDetail(e.getMessage());

	    return pd;
	}
	
	
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException ex) {

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
	
	
	

}
