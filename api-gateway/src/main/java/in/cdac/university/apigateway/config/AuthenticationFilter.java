package in.cdac.university.apigateway.config;

import in.cdac.university.apigateway.controller.LoginController;
import in.cdac.university.apigateway.response.ResponseHandler;
import in.cdac.university.apigateway.response.UserDetail;
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
            if (this.isAuthMissing(request)) {
                log.debug("Authorization header is missing in the request.");
                return this.onError(exchange, "Authorization header is missing in the request");
            }

            final String token = this.getAuthHeader(request).substring(7);

            if (jwtUtil.isInvalid(token)) {
                log.debug("Token expired");
                return this.onError(exchange, "Token expired");
            }

            // Check for blacklisted tokens
            if (LoginController.blackListedTokens.containsKey(token)) {
                log.debug("User already logged out");
                return this.onError(exchange, "User already logged out");
            }

            this.populateRequestWithHeaders(exchange, token);
        }

        return chain.filter(exchange);
    }

    private void populateRequestWithHeaders(ServerWebExchange exchange, String token) {
        UserDetail userDetail = jwtUtil.getUserDetail(token);
        JSONObject userDetailObject = new JSONObject(userDetail);

        exchange.getRequest()
                .mutate()
                .header("userId", userDetail.getUserId().toString())
                .header("userDetail", userDetailObject.toString())
                .header("Authorization", "Bearer " + token)
                .build();
    }

    private String getAuthHeader(ServerHttpRequest request) {
        return request.getHeaders().getOrEmpty("Authorization").get(0);
    }

    @SuppressWarnings("unchecked")
    private Mono<Void> onError(ServerWebExchange exchange, String error) {
        log.error("Error in validating Token: {}", error);
        ServerHttpResponse response = exchange.getResponse();
        ResponseEntity<Object> responseEntity = ResponseHandler.generateResponse(HttpStatus.UNAUTHORIZED, error);
        error = new JSONObject((Map<String, Object>) Objects.requireNonNull(responseEntity.getBody())).toString();
        DataBuffer responseBody = response.bufferFactory().wrap(error.getBytes());
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        return response.writeWith(Mono.just(responseBody));
    }

    private boolean isAuthMissing(ServerHttpRequest request) {
        log.debug("Request Headers: {}", request.getHeaders());
        return !request.getHeaders().containsKey("Authorization");
    }

}
