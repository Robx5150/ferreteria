package dto;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
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
public class DetalleVentaDTO {
	
	@NotNull
	Long id;
	
	@NotEmpty
	@NotBlank
	@Column(nullable = false)
	private String sku;
	
	@NotEmpty
	@NotBlank
	private String nombreProducto;
	
    @NotNull
    @Positive
    private Integer cantidad;
    
	@NotNull
	@Positive
	private BigDecimal precioUnitario;
	
	@NotNull
	@Positive
	private BigDecimal subtotal;

}
