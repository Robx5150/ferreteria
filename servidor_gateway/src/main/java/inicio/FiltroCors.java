package inicio;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.cors.reactive.CorsUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import reactor.core.publisher.Mono;

@Configuration
public class FiltroCors {

    private static final String ALLOWED_HEADERS =
            "x-requested-with, authorization, Content-Type, Content-Length, Authorization, credential, X-XSRF-TOKEN";

    private static final String ALLOWED_METHODS =
            "GET, PUT, POST, DELETE, OPTIONS, PATCH";

    private static final List<String> ALLOWED_ORIGINS = Arrays.asList(
            "http://localhost",
            "http://localhost:5173");

    @Bean
    WebFilter corsFilter() {

        return (ServerWebExchange ctx, WebFilterChain chain) -> {

            ServerHttpRequest request = ctx.getRequest();

            if (CorsUtils.isCorsRequest(request)) {

                String origin = request.getHeaders().getOrigin();

                ServerHttpResponse response = ctx.getResponse();
                HttpHeaders headers = response.getHeaders();

                if (origin != null && ALLOWED_ORIGINS.contains(origin)) {
                    headers.set("Access-Control-Allow-Origin", origin);
                }

                headers.set("Access-Control-Allow-Methods", ALLOWED_METHODS);
                headers.set("Access-Control-Allow-Headers", ALLOWED_HEADERS);
                headers.set("Access-Control-Allow-Credentials", "true");

                if (request.getMethod() == HttpMethod.OPTIONS) {
                    response.setStatusCode(HttpStatus.OK);
                    return Mono.empty();
                }
            }

            return chain.filter(ctx);
        };
    }
}