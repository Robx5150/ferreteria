package mapper;

import java.util.Map;

import org.mapstruct.Context;
import org.mapstruct.Mapper;

import dto.DetalleVentaDTO;
import model.DetalleVenta;

@Mapper(componentModel = "spring")
public interface DetalleVentaMapper { 
	
	default DetalleVentaDTO toDTO(DetalleVenta detalle, @Context Map<String, String> nombrePorSku) { 
        
		return new DetalleVentaDTO(
                detalle.getId(),
                detalle.getSku(),
                nombrePorSku.get(detalle.getSku()),
                detalle.getCantidad(),
                detalle.getPrecioUnitario(),
                detalle.getSubtotal()
        );
    }


}
