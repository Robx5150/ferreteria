package dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductoCreateDto {
	
	
	@NotBlank(message = "El sku no puede este vacío !!")
	private String sku;
	
	@NotBlank(message = "El nombre no puede este vacío !!")
	private String nombre;
	
	@NotBlank(message = "La marca no puede este vacía !!")
	private String marca;
	
	@NotBlank(message = "El modelo no puede este vacío !!")
	private String modelo;
	
	@NotNull(message = "El precio de compra no puede ser null !!")
	@Positive(message = "El precio de compra debe ser un número positivo !!")
	private Double preciocompra;
	
	@NotNull(message = "El precio de venta no puede ser null !!")
	@Positive(message = "El precio de venta debe ser un número positivo !!")
	private Double precioventa;

}
