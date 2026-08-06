package repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import model.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long>{
	
	Optional <Producto> findByNombre(String nombre); 
	
	Optional <Producto> findBySku(String sku); 
	
	boolean existsByNombre(String nombre);

    boolean existsBySku(String sku); 
    
    List<Producto> findBySkuIn(Set<String> skus);
    
    void deleteBySku(String sku);

}
