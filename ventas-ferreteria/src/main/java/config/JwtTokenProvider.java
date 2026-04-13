package config;

import java.time.Duration;
import java.time.Instant;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;


@Component
public class JwtTokenProvider {
	
	@Qualifier("keycloakClient")
	private final WebClient webClient;
	
	public JwtTokenProvider(@Qualifier("keycloakClient") WebClient.Builder builder) {	
       		
		this.webClient = builder
        					.baseUrl("http://localhost:8080")
        					.build();
    }
	
	

    @Value("${productos.auth.user}")
    private String user;

    @Value("${productos.auth.pass}")
    private String pass;

    @Value("${productos.auth.url}")
    private String authUrl;
    
    @Value("${productos.auth.client.id}")
    private String clientId;
    
    @Value("${productos.auth.grandt.type}")
    private String grantType;

    
    
    

    private String token;
    private Instant obtenidoEn; 
    
    private static final Duration TOKEN_TTL = Duration.ofHours(23);

	   

    public synchronized String getToken() { 

        if (token == null || estadoToken()) { 
           
        	autenticar();
        }

        return token;
    }

    private void autenticar() {

    	ResultAuth nuevoToken = webClient.post()
                .uri(authUrl)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData("client_id", clientId)
                        .with("username", user)
                        .with("password", pass)
                        .with("grant_type", grantType))
                .retrieve() 
                .bodyToMono(ResultAuth.class)
                .block();
    	
    	if (nuevoToken == null || nuevoToken.getAccess_token() == null || nuevoToken.getAccess_token().isBlank()) {
            throw new IllegalStateException("No se pudo obtener el JWT desde Keycloak");
        }

    	 this.token = nuevoToken.getAccess_token();
         this.obtenidoEn = Instant.now();
        
    }

    private boolean estadoToken() {
       
    	return obtenidoEn == null || Instant.now().isAfter(obtenidoEn.plus(TOKEN_TTL));  
    }

    
}
