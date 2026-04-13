package controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dto.VentaCreateDTO;
import dto.VentaDTO;
import dto.VentaPatchDTO;
import exceptions.ExceptionNoExiste;
import exceptions.VentaDuplicadaException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import service.VentaService;

@RequestMapping("/api/ventas")
@RequiredArgsConstructor
@RestController
public class VentasController {
	
	private final VentaService ventaService;
		
	@PostMapping() 
	public ResponseEntity<VentaDTO> crearVenta(@Valid @RequestBody VentaCreateDTO postDTO) throws VentaDuplicadaException{
		  
		  VentaDTO ventaCreada = ventaService.crearVenta(postDTO);
		  
		  return new ResponseEntity<VentaDTO>(ventaCreada, HttpStatus.CREATED);
	 
		  
	}
	
		
	@GetMapping()
	public List<VentaDTO> ListaDeVentas()  {
				
		return ventaService.listarVentas();
	}
	
		
	@GetMapping(value ="/por-id/{id}")	
	public ResponseEntity<VentaDTO> getVentaPorId(@PathVariable Long id) throws ExceptionNoExiste {
		
		return ResponseEntity.ok(ventaService.buscarPorId(id));
			
	}
	
	
	@GetMapping(value ="/por-factura/{factura}")	
	public ResponseEntity<VentaDTO> getVentaPorFactura(@PathVariable String factura) throws ExceptionNoExiste {
		
		return ResponseEntity.ok(ventaService.buscarPorNumeroFactura(factura));
			
	}
	
	
	
	
	@PatchMapping(value ="/por-id/{id}")
	public ResponseEntity<VentaDTO> actualizarEstadoPorId(@PathVariable Long id, 
													@Valid @RequestBody VentaPatchDTO patchDTO) throws ExceptionNoExiste {
		
		VentaDTO ventaActualizada = ventaService.actualizarEstadoPorId(id, patchDTO);
		
		return ResponseEntity.ok(ventaActualizada);
	}
	
	@PatchMapping(value ="/por-factura/{factura}")
	public ResponseEntity<VentaDTO> actualizarEstadoPorFactura(@PathVariable String factura, 
															   @Valid @RequestBody VentaPatchDTO patchDTO) throws ExceptionNoExiste {
		
		VentaDTO ventaActualizada = ventaService.actualizarEstadoPorNumeroFactura(factura, patchDTO);
		
		return ResponseEntity.ok(ventaActualizada);
	}
	 
	
	
}
