package service;

import java.util.List;
import java.util.Set;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dto.ProductoCreateDto;
import dto.ProductoDTO;
import dto.ProductoPatchDto;
import dto.ProductoSkuDTO;
import dto.ProductoUpdateDto;
import exceptions.ExceptionNoExiste;
import exceptions.ProductoDuplicadoException;
import exceptions.ProductoDuplicadoException.TipoDuplicado;
import lombok.RequiredArgsConstructor;
import mapper.ProductoMapper;
import model.Producto;
import repository.ProductoRepository;


@RequiredArgsConstructor 
@Service
public class ProductoServiceImpl implements ProductoService {
   
	
	private final ProductoRepository productoRepository;
	
	private final ProductoMapper productoMapper;

			
	@Override
	@Transactional
	public ProductoDTO crearProducto(ProductoCreateDto productoCreateDto)  {
		
				
        if (productoRepository.existsBySku(productoCreateDto.getSku())) {
            
        	throw new ProductoDuplicadoException(
            		          TipoDuplicado.SKU,"Ya existe un producto con el SKU '" + productoCreateDto.getSku()+"'"
           );
        }

        if (productoRepository.existsByNombre(productoCreateDto.getNombre())) {
            
        	throw new ProductoDuplicadoException(
            		          TipoDuplicado.NOMBRE,"Ya existe un producto con el nombre '" + productoCreateDto.getNombre()+"'"
           );
        }

        
        try {
            Producto producto = new Producto(
            		productoCreateDto.getSku(),
            		productoCreateDto.getNombre(),
            		productoCreateDto.getMarca(),
            		productoCreateDto.getModelo(),
            		productoCreateDto.getPreciocompra(),
            		productoCreateDto.getPrecioventa()
            );

            Producto productoGuardado = productoRepository.save(producto);
            
            return productoMapper.toDTO(productoGuardado); 

        } catch (DataIntegrityViolationException e) { // Captura cuando se rompe una restricción de integridad de la base de datos
           
            throw new RuntimeException("Violación de integridad inesperada", e);
        }
    }
	
	
	
	
	@Override
	@Transactional(readOnly = true) 
	public List<ProductoDTO> listarProductos() {
		
		List<Producto> productos = productoRepository.findAll();
		
		return productoMapper.toDTOList(productos);
	}
	
	
	@Override
	@Transactional(readOnly = true)
	public ProductoDTO buscarPorId(Long id) throws ExceptionNoExiste {
				
			Producto producto = productoRepository.findById(id)
		            .orElseThrow(() -> new ExceptionNoExiste("El producto con id '" + id + "' no existe"));
			
			return productoMapper.toDTO(producto);
		
	}
	
	@Override
	@Transactional(readOnly = true)
	public ProductoDTO buscarPorNombre(String nombre) throws ExceptionNoExiste {
		
		Producto producto = productoRepository.findByNombre(nombre)
		 	                         .orElseThrow(() -> new ExceptionNoExiste("El producto : '" + nombre + "'  no existe"));
				  					
		
		return productoMapper.toDTO(producto);
	
	}
	
	
	@Override
	@Transactional(readOnly = true)
	public ProductoDTO buscarPorSku(String sku) throws ExceptionNoExiste {
		
		Producto producto =  productoRepository.findBySku(sku)
		 	                 .orElseThrow(() -> new ExceptionNoExiste("El producto con el código : '" + sku.trim() + "'  no existe"));
				  					
		
		return productoMapper.toDTO(producto); 
	
	}
	
	
		

	@Override
	@Transactional
	public ProductoDTO actualizarProducto(String sku, ProductoUpdateDto productoUpdateDto) throws ExceptionNoExiste {
				
		
		Producto producto = productoRepository.findBySku(sku)
				   .orElseThrow(() -> new ExceptionNoExiste("El producto con código : '" + sku + "'  no existe"));
		
			
		
		producto.actualizarProducto(
				productoUpdateDto.getNombre(),
				productoUpdateDto.getMarca(),
				productoUpdateDto.getModelo(),
				productoUpdateDto.getPreciocompra(),
				productoUpdateDto.getPrecioventa()
		);
		 
		return productoMapper.toDTO(producto);
		
	}	
	
	@Override
	@Transactional
	public ProductoDTO actualizarParcialProducto(String sku, ProductoPatchDto productoPatchDto)  throws ExceptionNoExiste {
					
		
		Producto producto = productoRepository.findBySku(sku)
									   .orElseThrow(() -> new ExceptionNoExiste("El producto con código : '" + sku + "'  no existe"));
		
				
		productoMapper.actualizacionParcial(productoPatchDto, producto);
		
		Producto actualizado = productoRepository.save(producto);
			 
		return productoMapper.toDTO(actualizado);
		
	}
	
	
	@Override
	@Transactional
	public String eliminarProducto(String sku) throws ExceptionNoExiste {
		
		if (!productoRepository.existsBySku(sku)) {
	        throw new ExceptionNoExiste("Producto no encontrado con código: " + sku);
	    }
		
		productoRepository.deleteBySku(sku);
		return 	"Producto con código : '" + sku + "' fue eliminado !! ";
		
			
	}
	
	
	
	
	@Override
	public List<ProductoSkuDTO> obtenerSkus(Set<String> skus) {

	        List<Producto> productos = productoRepository.findBySkuIn(skus);

	        return productos.stream()
	                .map(p -> new ProductoSkuDTO(p.getSku(), p.getNombre())) // 
	                .toList();
	}
	

	

}
