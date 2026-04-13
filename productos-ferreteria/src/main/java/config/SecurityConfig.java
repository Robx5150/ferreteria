package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;


import lombok.RequiredArgsConstructor;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor 
public class SecurityConfig {
	
	
	private final JwtAuthConverter jwtAuthConverter;


    @Bean
    SecurityFilterChain filtroChain(HttpSecurity http) throws Exception{
		
		  http.csrf(cus->cus.disable()).authorizeHttpRequests(aut->aut
				  						  .requestMatchers(HttpMethod.POST,"/api/productos").hasAnyRole("ADMIN","OPERATOR") 
				  					      .requestMatchers(HttpMethod.POST,"/api/productos/**").hasAnyRole("ADMIN","OPERATOR")
				  					      .requestMatchers(HttpMethod.DELETE,"/api//productos/**").hasRole("ADMIN")
				  					      .requestMatchers(HttpMethod.PATCH,"/api//productos/**").hasAnyRole("ADMIN","OPERATOR")
				  						  .requestMatchers(HttpMethod.PUT,"/api//productos/**").hasAnyRole("ADMIN","OPERATOR")
				  						  .requestMatchers("/api/productos").authenticated()
				  						  .requestMatchers("/api/productos/**").authenticated()
				  						  .anyRequest().permitAll())
		  								.oauth2ResourceServer(oauth2ResourceServer->oauth2ResourceServer
		  										.jwt(jwt->jwt 
		  												.jwtAuthenticationConverter(jwtAuthConverter)))                          
		  				            					.sessionManagement(sessionManagement->sessionManagement 
		  				            			        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)); 
		 
			
		 return http.build();
		 
		
	}
	
	
	

}
