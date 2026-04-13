package service;

import java.util.List;
import java.util.Set;

import dto.ProductoCreateDto;
import dto.ProductoDTO;
import dto.ProductoPatchDto;
import dto.ProductoSkuDTO;
import dto.ProductoUpdateDto;
import exceptions.ExceptionNoExiste;


public interface ProductoService {
   	
	ProductoDTO crearProducto(ProductoCreateDto postDto);
	
	List<ProductoDTO> listarProductos();
	
	ProductoDTO buscarPorId(Long id) throws ExceptionNoExiste;
		
	ProductoDTO buscarPorNombre(String nombre) throws ExceptionNoExiste; 
		
	ProductoDTO buscarPorSku(String sku) throws ExceptionNoExiste; 
			
	ProductoDTO actualizarProducto(String sku, ProductoUpdateDto putDto) throws ExceptionNoExiste;
		
	ProductoDTO actualizarParcialProducto(String sku, ProductoPatchDto patchDto)  throws ExceptionNoExiste;
		
	String eliminarProducto(String sku) throws ExceptionNoExiste;
	
	List<ProductoSkuDTO> obtenerSkus(Set<String> skus);

	

}
