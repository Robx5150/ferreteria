package dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductoDTO {
	
	@NotNull(message = "El id no puede ser nulo")
	private
	Long id;
	
	@NotBlank(message = "El código SKU es obligatorio")
	private String sku;
	
	@NotBlank(message = "El nombre es obligatorio")
	private String nombre;
	
	@NotBlank(message = "La marca es obligatorio")
	private String marca;
	
	@NotBlank(message = "El modelo es obligatorio")
	private String modelo;
	
	@NotNull(message = "El precio de compra es obligatorio")
	private Double preciocompra;
	
	@NotNull(message = "El precio de venta es obligatorio")
	private Double precioventa;
	
	
}
