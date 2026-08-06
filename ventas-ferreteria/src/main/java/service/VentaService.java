package service;

import java.util.List;

import dto.VentaCreateDTO;
import dto.VentaDTO;
import dto.VentaPatchDTO;
import dto.VentaResumenDTO;

public interface VentaService {
	
	VentaDTO crearVenta(VentaCreateDTO dto, String usuario);
	
	List<VentaDTO> listarVentas();
	
	List<VentaResumenDTO> listarVentasResumen();

    VentaDTO buscarPorId(Long id); 

    VentaDTO buscarPorNumeroFactura(String numeroFactura);

    VentaDTO actualizarEstadoPorId(Long id, VentaPatchDTO dto);
    
    VentaDTO actualizarEstadoPorNumeroFactura(String numeroFactura, VentaPatchDTO dto);

}
