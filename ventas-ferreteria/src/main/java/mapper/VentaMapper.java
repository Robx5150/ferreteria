package mapper;

import java.util.List;
import java.util.Map;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import dto.VentaDTO;
import dto.VentaResumenDTO;
import model.Venta;



@Mapper(componentModel = "spring",uses = DetalleVentaMapper.class) 
public interface VentaMapper {
		
	@Mapping(target = "detalles") 
	VentaDTO toDTO(Venta venta, @Context Map<String, String> nombrePorSku); //Contexto para pasar nombrePorSku de tipo Map<String, String>
		
	List<VentaDTO> toDTOList(List<Venta> ventas, @Context Map<String, String> nombrePorSku);
	
	List<VentaResumenDTO> toResumenDTOList(List<Venta> ventas);
 
    
}
