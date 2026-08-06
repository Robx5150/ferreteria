package exceptions;


public class ProductoDuplicadoException extends RuntimeException {
	
	
	private static final long serialVersionUID = 1L;
	
	private final TipoDuplicado tipo;

    public enum TipoDuplicado {
        SKU, NOMBRE
    }

    public ProductoDuplicadoException(TipoDuplicado tipo, String message) {
        super(message);
        this.tipo = tipo;
    }

    public TipoDuplicado getTipo() {
        return tipo;
    }

}
