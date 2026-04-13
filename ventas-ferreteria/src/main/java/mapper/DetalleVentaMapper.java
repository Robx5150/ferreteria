package mapper;

import java.util.Map;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import dto.DetalleVentaDTO;
import model.DetalleVenta;

@Mapper(componentModel = "spring")
public interface DetalleVentaMapper { 
	
	@Named("detalleSimple") 
	default DetalleVentaDTO toDTO(DetalleVenta detalle) {
        return new DetalleVentaDTO(
                detalle.getId(),
                detalle.getSku(),
                null, // nombreProducto NO disponible aquí
                detalle.getCantidad(),
                detalle.getPrecioUnitario(),
                detalle.getSubtotal()
        );
    }
	
	
	@Named("detalleEnriquecido") 
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
