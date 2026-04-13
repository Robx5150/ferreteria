package mapper;

import java.util.List;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import dto.ProductoDTO;
import dto.ProductoPatchDto;
import model.Producto;



@Mapper(componentModel = "spring") 
public interface ProductoMapper { 
	
	ProductoDTO toDTO(Producto producto);
	
	List<ProductoDTO> toDTOList(List<Producto> producto); 
	
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "sku", ignore = true)
	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)  
    void actualizacionParcial(ProductoPatchDto patchDto, @MappingTarget Producto producto); 
	

}
