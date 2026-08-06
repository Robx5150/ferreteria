package mapper;

import dto.ProductoDTO;
import dto.ProductoPatchDto;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import model.Producto;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-07-07T09:57:54-0400",
    comments = "version: 1.6.3, compiler: Eclipse JDT (IDE) 3.42.0.v20250526-2018, environment: Java 21.0.7 (Eclipse Adoptium)"
)
@Component
public class ProductoMapperImpl implements ProductoMapper {

    @Override
    public ProductoDTO toDTO(Producto producto) {
        if ( producto == null ) {
            return null;
        }

        ProductoDTO productoDTO = new ProductoDTO();

        productoDTO.setId( producto.getId() );
        productoDTO.setMarca( producto.getMarca() );
        productoDTO.setModelo( producto.getModelo() );
        productoDTO.setNombre( producto.getNombre() );
        productoDTO.setPreciocompra( producto.getPreciocompra() );
        productoDTO.setPrecioventa( producto.getPrecioventa() );
        productoDTO.setSku( producto.getSku() );

        return productoDTO;
    }

    @Override
    public List<ProductoDTO> toDTOList(List<Producto> producto) {
        if ( producto == null ) {
            return null;
        }

        List<ProductoDTO> list = new ArrayList<ProductoDTO>( producto.size() );
        for ( Producto producto1 : producto ) {
            list.add( toDTO( producto1 ) );
        }

        return list;
    }

    @Override
    public void actualizacionParcial(ProductoPatchDto patchDto, Producto producto) {
        if ( patchDto == null ) {
            return;
        }

        if ( patchDto.getMarca() != null ) {
            producto.setMarca( patchDto.getMarca() );
        }
        if ( patchDto.getModelo() != null ) {
            producto.setModelo( patchDto.getModelo() );
        }
        if ( patchDto.getNombre() != null ) {
            producto.setNombre( patchDto.getNombre() );
        }
        if ( patchDto.getPreciocompra() != null ) {
            producto.setPreciocompra( patchDto.getPreciocompra() );
        }
        if ( patchDto.getPrecioventa() != null ) {
            producto.setPrecioventa( patchDto.getPrecioventa() );
        }
    }
}
