package repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import enums.EstadoVenta;
import model.Venta;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Long> {
	
	Optional<Venta> findByNumeroFactura(String numeroFactura);

    List<Venta> findByUsuario(String usuario);
    
    List<Venta> findByEstado(EstadoVenta estado);
    
    boolean existsByNumeroFactura(String numeroFactura); 
	
	
}
