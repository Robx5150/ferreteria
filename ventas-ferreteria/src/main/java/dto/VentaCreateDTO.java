package dto;

import java.util.List;

public record VentaCreateDTO(String numeroFactura, List<DetalleVentaCreateDTO> detalles) {

}
