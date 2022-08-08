package in.cdac.university.apigateway.config;

import in.cdac.university.apigateway.response.ResponseHandler;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.Objects;

@RefreshScope
@Component
@Slf4j
public class AuthenticationFilter implements GatewayFilter {

    @Autowired
    private RouterValidator routerValidator;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        if (routerValidator.isSecured.test(request)) {
            log.info("Authentication Filter: checking for valid token");
            if (this.isAuthMissing(request)) {
                log.info("Authorization header is missing in the request.");
                return this.onError(exchange, "Authorization header is missing in the request", HttpStatus.UNAUTHORIZED);
            }

            final String token = this.getAuthHeader(request).substring(7);
            log.info("Token: " + token);
            log.info("Application Type: " + jwtUtil.getApplicationType(token));

            if (jwtUtil.isInvalid(token)) {
                log.info("Token expired");
                return this.onError(exchange, "Token expired", HttpStatus.UNAUTHORIZED);
            }
            this.populateRequestWithHeaders(exchange, token);
        }

        return chain.filter(exchange);
    }

    private void populateRequestWithHeaders(ServerWebExchange exchange, String token) {
        exchange.getRequest()
                .mutate()
                .header("userId", Integer.toString(jwtUtil.getUserId(token)))
                .header("Authorization", "Bearer " + token)
                .build();
    }

    private String getAuthHeader(ServerHttpRequest request) {
        return request.getHeaders().getOrEmpty("Authorization").get(0);
    }

    @SuppressWarnings("unchecked")
    private Mono<Void> onError(ServerWebExchange exchange, String error, HttpStatus httpStatus) {
        log.info("Error in validating Token: " + error);
        ServerHttpResponse response = exchange.getResponse();
        ResponseEntity<Object> responseEntity = ResponseHandler.generateResponse(httpStatus, error);
        error = new JSONObject((Map<String, Object>) Objects.requireNonNull(responseEntity.getBody())).toString();
        DataBuffer responseBody = response.bufferFactory().wrap(error.getBytes());
        response.setStatusCode(httpStatus);
        return response.writeWith(Mono.just(responseBody));
    }

    private boolean isAuthMissing(ServerHttpRequest request) {
        log.info("Request Headers: " + request.getHeaders().toString());
        return !request.getHeaders().containsKey("Authorization");
    }

}
