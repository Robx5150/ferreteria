package service;

import java.util.List;

import dto.VentaCreateDTO;
import dto.VentaDTO;
import dto.VentaPatchDTO;

public interface VentaService {
	
	VentaDTO crearVenta(VentaCreateDTO dto);
	
	List<VentaDTO> listarVentas();

    VentaDTO buscarPorId(Long id); 

    VentaDTO buscarPorNumeroFactura(String numeroFactura);

    VentaDTO actualizarEstadoPorId(Long id, VentaPatchDTO dto);
    
    VentaDTO actualizarEstadoPorNumeroFactura(String numeroFactura, VentaPatchDTO dto);

}
