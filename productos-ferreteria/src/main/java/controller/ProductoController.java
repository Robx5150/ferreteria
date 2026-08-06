package controller;

import java.net.URI;
import java.util.List;
import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import dto.ProductoCreateDto;
import dto.ProductoDTO;
import dto.ProductoPatchDto;
import dto.ProductoSkuDTO;
import dto.ProductoUpdateDto;
import exceptions.ExceptionNoExiste;
import exceptions.ProductoDuplicadoException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import service.ProductoService;

@RequiredArgsConstructor
@RequestMapping("/api/productos")
@RestController
public class ProductoController {
	
	private final ProductoService productoService;
	
	@PostMapping()
	public ResponseEntity<ProductoDTO> crearProducto(@Valid @RequestBody ProductoCreateDto postDto) throws ProductoDuplicadoException {
	    
		ProductoDTO productoNuevo  = productoService.crearProducto(postDto);  //Guardar el producto
		
		
		URI location = ServletUriComponentsBuilder 
	            .fromCurrentRequest()  
	            .path("/{id}") 
	            .buildAndExpand(productoNuevo.getId()) 
	            .toUri(); 

	    return ResponseEntity 
	            		.created(location) 
	            		.body(productoNuevo); 
		
	}
	
	
	
		
	@GetMapping()
	public List<ProductoDTO> ListaDeProductos() {
	   		    
		return productoService.listarProductos();
		
			
	}
	
	
	
	@GetMapping(value="/por-id/{id}")
	public ResponseEntity<ProductoDTO> buscarProductoPorId(@PathVariable Long id) throws ExceptionNoExiste {
	    
	    return ResponseEntity.ok(productoService.buscarPorId(id));
	}
	
	
	@GetMapping(value="/por-sku/{sku}")
	public ResponseEntity<ProductoDTO> buscarProductoPorSku(@PathVariable String sku) throws ExceptionNoExiste {
	  		
	    return ResponseEntity.ok(productoService.buscarPorSku(sku));
	
	}
	
	@GetMapping(value="/por-nombre/{nombre}")
	public ResponseEntity<ProductoDTO> buscarProductoPorNombre(@PathVariable String nombre) throws ExceptionNoExiste {
	   
	    return ResponseEntity.ok(productoService.buscarPorNombre(nombre));
	}
	
	
	
	@PutMapping(value="/{sku}")
	public ResponseEntity<ProductoDTO> actualizarProducto(@PathVariable String sku,
													      @Valid @RequestBody ProductoUpdateDto putDto) throws ExceptionNoExiste {
	    	   	   	       
	    return ResponseEntity.ok(productoService.actualizarProducto(sku, putDto));
	}
	
	@PatchMapping(value="/{sku}")
	public ResponseEntity<ProductoDTO> actualizarParcialProducto(@PathVariable String sku, 
															     @Valid @RequestBody ProductoPatchDto patchDto) throws ExceptionNoExiste {
	   	    
	    return ResponseEntity.ok(productoService.actualizarParcialProducto(sku, patchDto));
	    
	    
	}
	
	
	@DeleteMapping(value="/{sku}")
	public ResponseEntity<String> eliminarProducto(@PathVariable String sku) throws ExceptionNoExiste {
			   	    
	    return ResponseEntity.ok(productoService.eliminarProducto(sku));
	}
	
	
	@PostMapping("/por-skus")
    public List<ProductoSkuDTO> obtenerPorSkus(@RequestBody Set<String> skus) {

        return productoService.obtenerSkus(skus);
    }
	

}
