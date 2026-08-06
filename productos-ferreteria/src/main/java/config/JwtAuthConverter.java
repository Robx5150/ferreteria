package config;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthConverter implements Converter<Jwt, AbstractAuthenticationToken> {

	@Value("${keycloak.clientId}")
	private String clientId;
	
	@Override
	public AbstractAuthenticationToken convert(Jwt jwt) {
		
		Collection<GrantedAuthority> autoridades = extraeRoles(jwt);
		
    	return new JwtAuthenticationToken(jwt, autoridades, jwt.getClaim("preferred_username")); 
	}
	
	private Collection<GrantedAuthority> extraeRoles(Jwt jwt) {   
				
		return Optional.ofNullable(jwt.getClaimAsMap("resource_access")) 
		        .map(m -> m.get(clientId)) 
		        .filter(Map.class::isInstance) 
		        .map(Map.class::cast)  
		        .map(m -> m.get("roles")) 
		        .filter(Collection.class::isInstance) 
		        .map(o -> (Collection<?>) o) 
		        .stream() 
		        .flatMap((Collection<?> c) -> c.stream()) 
		        .filter(String.class::isInstance) 
		        .map(String.class::cast) 
		        .map(role -> new SimpleGrantedAuthority("ROLE_" + role)) 
		        .collect(Collectors.toSet()); 
			
	}
	
	
}
