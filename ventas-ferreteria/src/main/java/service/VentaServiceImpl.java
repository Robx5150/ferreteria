package service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dto.DetalleVentaCreateDTO;
import dto.VentaCreateDTO;
import dto.VentaDTO;
import dto.VentaPatchDTO;
import dto.VentaResumenDTO;
import enums.EstadoVenta;
import exceptions.BusinessException;
import exceptions.ExceptionNoExiste;
import exceptions.VentaDuplicadaException;
import lombok.RequiredArgsConstructor;
import mapper.VentaMapper;
import model.DetalleVenta;
import model.Venta;
import repository.VentaRepository;

@RequiredArgsConstructor  
@Service
public class VentaServiceImpl implements VentaService {
	
	private final VentaRepository ventaRepository;
	
	private final VentaMapper ventaMapper;
	
	private final ProductoService productoService;
	
	  @Transactional
	  @Override 
	  public VentaDTO crearVenta(VentaCreateDTO ventaCreateDto, String usuario ) { 
	  
		  if (ventaRepository.existsByNumeroFactura(ventaCreateDto.numeroFactura())) { 
			  throw new VentaDuplicadaException( "Ya existe la venta con la factura '" +  ventaCreateDto.numeroFactura() + "'" ); 
		  }
		  
		  
		  Set<String> skus = ventaCreateDto.detalles().stream() 
		           							   .map(DetalleVentaCreateDTO::sku) 
		            						   .collect(Collectors.toSet()); 
		    
		    
		  productoService.validarSkusExistentes(skus); 
		    
		  Venta venta = new Venta(ventaCreateDto.numeroFactura(), usuario);  

		  for (DetalleVentaCreateDTO d : ventaCreateDto.detalles()) { 
		    	
		    	DetalleVenta detalle = new DetalleVenta(d.sku(),d.cantidad(), d.precioUnitario()); 
		        venta.agregarDetalle(detalle);
		  }
		  
		  Venta ventaGuardada = ventaRepository.save(venta); 
		  
		  Map<String, String> nombrePorSku = productoService.obtenerNombrePorSku(skus); 
		 
		  return ventaMapper.toDTO(ventaGuardada, nombrePorSku);
	  
	  
	  }
	  
	 	 
	
	@Override
	@Transactional(readOnly = true) 
	public List<VentaDTO> listarVentas() {
		
	    List<Venta> ventas = ventaRepository.findAll(); 

	    
	    Set<String> skus = ventas.stream() 
	            				 .flatMap(v -> v.getDetalles().stream()) 
	            				 .map(DetalleVenta::getSku) 
	            				 .collect(Collectors.toSet()); 
	    
	    Map<String, String> nombrePorSku = productoService.obtenerNombrePorSku(skus); 
	   
	    return ventaMapper.toDTOList(ventas, nombrePorSku);
		
		
		
	   
	}
	
	
	@Override
	@Transactional(readOnly = true)
	public List<VentaResumenDTO> listarVentasResumen() {
		
		List<Venta> ventas = ventaRepository.findAll(); 
	    
	    return ventaMapper.toResumenDTOList(ventas);
		
	}
	
	
	@Override
	@Transactional(readOnly = true)
	public VentaDTO buscarPorId(Long id) {
		
		Venta venta = ventaRepository.findById(id)
	            .orElseThrow(() -> new ExceptionNoExiste("La Venta con id '" + id + "' no existe"));
		
		
	    Set<String> skus = venta.getDetalles().stream()
	               							  .map(DetalleVenta::getSku)
	               							  .collect(Collectors.toSet());
	    
	    Map<String, String> nombrePorSku = productoService.obtenerNombrePorSku(skus); 
	   
	    return ventaMapper.toDTO(venta, nombrePorSku);
		
		
	}
	
	
	@Override
	@Transactional(readOnly = true)
	public VentaDTO buscarPorNumeroFactura(String numeroFactura) {
		
		Venta venta =  ventaRepository.findByNumeroFactura(numeroFactura)
	 	    .orElseThrow(() -> new ExceptionNoExiste("La Venta con el N° de factura : '" + numeroFactura + "'  no existe"));
		
		
		Set<String> skus = venta.getDetalles().stream()
	               							  .map(DetalleVenta::getSku)
	                                          .collect(Collectors.toSet());
	    
	    Map<String, String> nombrePorSku = productoService.obtenerNombrePorSku(skus); 
	   
	    return ventaMapper.toDTO(venta, nombrePorSku);
		
			
	}
	
	
	@Override
	@Transactional
	public VentaDTO actualizarEstadoPorId(Long id, VentaPatchDTO ventaPatchDto) {
		
		Venta venta = ventaRepository.findById(id)
	            .orElseThrow(() -> new ExceptionNoExiste("Venta no encontrada"));

			
		EstadoVenta nuevoEstado;
	    
		try {
	        
	    	nuevoEstado = EstadoVenta.valueOf(ventaPatchDto.estado().toUpperCase());
	        
	    } catch (IllegalArgumentException | NullPointerException e) {
	        
	    	throw new BusinessException("Estado : " + ventaPatchDto.estado() + " no es válido." +
										"Estados permitidos: EMITIDA, PENDIENTE, ANULADA");
	    }

		
	   venta.cambiarEstado(nuevoEstado);
		
	    Set<String> skus = venta.getDetalles().stream()
	               							  .map(DetalleVenta::getSku)
	               							  .collect(Collectors.toSet());

	    Map<String, String> nombrePorSku = productoService.obtenerNombrePorSku(skus); 
	    
	    return ventaMapper.toDTO(venta, nombrePorSku);
	}
	
	
	@Override
	@Transactional
	public VentaDTO actualizarEstadoPorNumeroFactura(String numeroFactura, VentaPatchDTO ventaPatchDto) {
		
		Venta venta = ventaRepository.findByNumeroFactura(numeroFactura)
	            .orElseThrow(() -> new ExceptionNoExiste("Venta no encontrada"));

				
		EstadoVenta nuevoEstado;
	    
		try {
	        
	    	nuevoEstado = EstadoVenta.valueOf(ventaPatchDto.estado().toUpperCase());
	        
	    } catch (IllegalArgumentException | NullPointerException e) {
	        
	    	throw new BusinessException("Estado : " + ventaPatchDto.estado() + " no es válido." +
	    								"Estados permitidos: EMITIDA, PENDIENTE, ANULADA");
	    }

			   
	    venta.cambiarEstado(nuevoEstado);
	     
	    Set<String> skus = venta.getDetalles().stream()
	               							  .map(DetalleVenta::getSku)
	               							  .collect(Collectors.toSet());

	    Map<String, String> nombrePorSku = productoService.obtenerNombrePorSku(skus); 
	    

	   return ventaMapper.toDTO(venta,nombrePorSku);
	}

	
		

}
