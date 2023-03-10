package in.cdac.university.apigateway.config;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class RouterValidator {

    public static final List<String> apiWithoutAuthentication = List.of(
            "/api/login",
            "/api/register",
            "/usm/state/",
            "/usm/district/",
            "/global/applicant/draft/",
            "/pb/notification/active",
            "/global/documentType/notification",
            "/global/file/download",
            "/global/college/count"
    );

    public Predicate<ServerHttpRequest> isSecured = request -> apiWithoutAuthentication.stream()
            .noneMatch(uri -> request.getURI().getPath().contains(uri));

    public boolean isLogoutRequest(ServerHttpRequest request) {
        return request.getURI().getPath().equals("/api/logout");
    }

    public Predicate<ServerHttpRequest> isUsmPath = request -> request.getURI().getPath().contains("usm");

    public Predicate<ServerHttpRequest> isApplicationPath = isUsmPath.negate();
}
