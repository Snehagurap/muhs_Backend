package in.cdac.university.apigateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Autowired
    AuthenticationFilter authenticationFilter;

    @Autowired
    SecurityFilter securityFilter;

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("login", r -> r.path("/api/login**")
                        .filters(f -> f.filter(securityFilter))
                        .uri("lb://api-gateway"))
                .route("usm", r -> r.path("/usm/**")
                        .filters(f -> f.filter(authenticationFilter)
                                .filter(securityFilter))
                        .uri("lb://user-management"))
                .route("global", r -> r.path("/global/**")
                        .filters(f-> f.filter(authenticationFilter)
                                .filter(securityFilter))
                        .uri("lb://global-service"))
                .route("committee", r -> r.path("/committee/**")
                        .filters(f-> f.filter(authenticationFilter)
                                .filter(securityFilter))
                        .uri("lb://committee"))
                .route("planning-board", r -> r.path("/pb/**")
                        .filters(f-> f.filter(authenticationFilter)
                                .filter(securityFilter))
                        .uri("lb://planning-board"))
                .build();
    }
}
