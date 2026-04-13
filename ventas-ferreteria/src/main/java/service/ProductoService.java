package service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import config.JwtTokenProvider;
import dto.ProductoDTO;
import exceptions.BusinessException;


@Service
public class ProductoService {
	
	private final WebClient webClient;
	
	private final JwtTokenProvider jwtTokenProvider;
	
	
	 public ProductoService(@Qualifier("productosClient") WebClient.Builder builder, JwtTokenProvider jwtTokenProvider) {

		 this.webClient = builder
	        		          .baseUrl("http://productos-ferreteria")
	                          .build();   
		 
		 
	     this.jwtTokenProvider = jwtTokenProvider;
	        
	        
	  }
	
	

    public Map<String, String> obtenerNombrePorSku(Set<String> skus) {

    	String token = jwtTokenProvider.getToken(); 
    	
    	System.out.println("Token obtenido: " + token);
    	
    	List<ProductoDTO> productos = webClient
                							.post()
                							.uri("/api/productos/por-skus")
                							.header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                							.bodyValue(skus)
                							.retrieve()  
                							.bodyToFlux(ProductoDTO.class) 
                							.collectList()
                							.block();

        return productos.stream()
                		.collect(Collectors.toMap(ProductoDTO::sku, ProductoDTO::nombre));
    }
    
    
    
    public void validarSkusExistentes(Set<String> skus) {

        if (skus == null || skus.isEmpty()) {
            throw new BusinessException("La venta no contiene productos");
        }
        
        String token = jwtTokenProvider.getToken(); 

        List<ProductoDTO> productos = webClient
                .post()
                .uri("/api/productos/por-skus")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .bodyValue(skus) 
                .retrieve() 
                .bodyToFlux(ProductoDTO.class) 
                .collectList() 
                .block(); 
        
        if (productos == null || productos.isEmpty()) {
            throw new BusinessException("No se pudo validar los productos");
        }

		
		Set<String> skusExistentes = productos.stream()
                							  .map(ProductoDTO::sku)
                							  .collect(Collectors.toSet());

        Set<String> skusInexistentes = new HashSet<>(skus);
        skusInexistentes.removeAll(skusExistentes);

        if (!skusInexistentes.isEmpty()) {
            throw new BusinessException("Los siguientes SKU no existen: " + skusInexistentes);
        }
		 
    }

}
