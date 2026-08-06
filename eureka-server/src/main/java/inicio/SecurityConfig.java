package inicio;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Bean
    InMemoryUserDetailsManager usersdetais() throws Exception{
        
		List<UserDetails> users=List.of(User.withUsername("admin")
                    						.password("{noop}admeureka123")
                    						.roles("ADMIN")
                    						.build()
                    				   );      
		
        return new InMemoryUserDetailsManager(users);                   
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        
    	http.csrf(cus->cus.disable())
        	.authorizeHttpRequests(aut->aut
        			.requestMatchers("/eureka/**").permitAll() 
        			.anyRequest().authenticated())
        	.httpBasic(Customizer.withDefaults());
    	
        return http.build();
    }

}
