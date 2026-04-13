package dto;

import java.math.BigDecimal;

public record DetalleVentaCreateDTO(String sku, Integer cantidad, BigDecimal precioUnitario) {

}
