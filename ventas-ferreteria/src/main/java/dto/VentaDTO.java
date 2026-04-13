package dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import enums.EstadoVenta;
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
public class VentaDTO {
	
	@NotNull(message = "El id no puede ser nulo")
	private
	Long id;
	
	@NotBlank(message = "El número de factura es obligatorio")
    private String numeroFactura;
	
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm", timezone = "America/Santiago")
	@NotNull(message = "La fecha es obligatoria")
	private LocalDateTime fecha;
	
	
	@NotNull(message = "El total es obligatorio")
	@Positive
	private BigDecimal total;
	
	@NotNull(message = "El estado es obligatorio")
    private EstadoVenta estado;
	
    @NotBlank(message = "El usuario es obligatorio")
    private String usuario;
	
	@NotEmpty(message = "La venta debe tener al menos un producto")
	private List<DetalleVentaDTO> detalles;
	
}
