package mapper;

import java.util.List;
import java.util.Map;

import org.mapstruct.Context;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import dto.VentaDTO;
import model.Venta;



@Mapper(componentModel = "spring",uses = DetalleVentaMapper.class) 
public interface VentaMapper {
	
	@Named("simple") // Para POST /ventas y GET /ventas/{id}
	@Mapping(target = "detalles", qualifiedByName = "detalleSimple")
	VentaDTO toDTO(Venta venta);
	
	@Named("enriquecido") // Para GET /ventas y GET /ventas/{id} con nombres de productos
	@Mapping(target = "detalles", qualifiedByName = "detalleEnriquecido")
	VentaDTO toDTO(Venta venta, @Context Map<String, String> nombrePorSku); //Contexto para pasar nombrePorSku de tipo Map<String, String>
	
	@IterableMapping(qualifiedByName = "enriquecido")
	List<VentaDTO> toDTOList(List<Venta> ventas, @Context Map<String, String> nombrePorSku);
 
    
}
