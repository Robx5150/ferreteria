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

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

import dto.ProductoDTO;
import exceptions.BusinessException;


@Service
public class ProductoService {
	
	private final WebClient webClient; 
	
		
	
	public ProductoService(@Qualifier("productosClient") WebClient.Builder builder) {

        this.webClient = builder
                .baseUrl("http://productos-ferreteria")
                .build();
    }

	
	

	public Map<String, String> obtenerNombrePorSku(Set<String> skus) {

        String token = obtenerTokenUsuario();

        System.out.println("Token usuario: " + token);

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
                .collect(Collectors.toMap(
                        ProductoDTO::sku,
                        ProductoDTO::nombre));
    }
	
	   
    
	public void validarSkusExistentes(Set<String> skus) {

        if (skus == null || skus.isEmpty()) {
            throw new BusinessException(
                    "La venta no contiene productos");
        }

        String token = obtenerTokenUsuario();

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

            throw new BusinessException(
                    "No se pudo validar los productos");
        }

        Set<String> skusExistentes = productos.stream()
                .map(ProductoDTO::sku)
                .collect(Collectors.toSet());

        Set<String> skusInexistentes = new HashSet<>(skus);

        skusInexistentes.removeAll(skusExistentes);

        if (!skusInexistentes.isEmpty()) {

            throw new BusinessException(
                    "Los siguientes SKU no existen: "
                            + skusInexistentes);
        }
    }
	
	private String obtenerTokenUsuario() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Jwt jwt = (Jwt) authentication.getPrincipal();

        return jwt.getTokenValue();
    }
    

}
