package in.cdac.university.apigateway.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.rewrite.ModifyRequestBodyGatewayFilterFactory;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RefreshScope
@Component
@Slf4j
public class SecurityFilter implements GatewayFilter {

    @Autowired
    private ModifyRequestBodyGatewayFilterFactory modifyRequestBodyFilter;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        MediaType contentType = exchange.getRequest().getHeaders().getContentType();
        if (contentType != null && contentType.includes(MediaType.MULTIPART_FORM_DATA)) {
            // File upload
        } else {
            ServerHttpRequest request = exchange.getRequest();
            if (this.isSecurityHeaderPresent(request)) {
                String fhttf = this.getSecurityHeader(request);
                GatewayFilter securityFilter = modifyRequestBodyFilter.apply(
                        new ModifyRequestBodyGatewayFilterFactory.Config()
                                .setRewriteFunction(String.class, String.class, new RequestBodyRewrite(fhttf)));

                return securityFilter.filter(exchange, chain);
            }
        }

        return chain.filter(exchange);
    }

    private String getSecurityHeader(ServerHttpRequest request) {
        return request.getHeaders().getOrEmpty("fhttf").get(0);
    }

    private boolean isSecurityHeaderPresent(ServerHttpRequest request) {
        log.debug("Request Headers: {}", request.getHeaders());
        return request.getHeaders().containsKey("fhttf");
    }

}
