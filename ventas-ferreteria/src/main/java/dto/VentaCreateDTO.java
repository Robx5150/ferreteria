package dto;

import java.util.List;

public record VentaCreateDTO(String numeroFactura, String usuario,  List<DetalleVentaCreateDTO> detalles) {

}
