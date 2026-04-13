package dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductoPatchDto {
	
	private String sku;
	private String nombre;	
	private String marca;	
	private String modelo;	
	private Double preciocompra;	
	private Double precioventa;

}
