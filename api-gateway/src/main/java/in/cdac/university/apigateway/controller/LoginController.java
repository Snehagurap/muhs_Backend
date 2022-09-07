package in.cdac.university.apigateway.controller;

import in.cdac.university.apigateway.bean.Token;
import in.cdac.university.apigateway.config.AccessTokenMapper;
import in.cdac.university.apigateway.config.JwtUtil;
import in.cdac.university.apigateway.exception.ErrorResponse;
import in.cdac.university.apigateway.response.ResponseHandler;
import in.cdac.university.apigateway.response.UserDetail;
import lombok.extern.slf4j.Slf4j;
import net.jodah.expiringmap.ExpirationPolicy;
import net.jodah.expiringmap.ExpiringMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.*;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api")
@Slf4j
@CrossOrigin(origins = "*")
@RefreshScope
public class LoginController {

    @Value("${config.oauth2.url}")
    private String oauthUrl;

    @Value("${config.oauth2.clientId}")
    private String oauthClientId;

    @Value("${config.oauth2.clientSecret}")
    private String oauthClientSecret;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private JwtUtil jwtUtil;

    public final static ExpiringMap<String, String> blackListedTokens = ExpiringMap.builder()
            .maxSize(2000)
            .variableExpiration()
            .expirationPolicy(ExpirationPolicy.CREATED)
            .build();

    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserDetail userDetail,
                                   @RequestHeader(HttpHeaders.USER_AGENT) String userAgent,
                                   ServerHttpResponse httpResponse) {
        final String url = oauthUrl + "/oauth/token";
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(oauthClientId, oauthClientSecret);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("applicationType", userDetail.getApplicationType().toString());

        System.out.println("User Agent: " + userAgent);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("username", userDetail.getUsername());
        body.add("password", userDetail.getPassword());
        body.add("grant_type", "password");

        HttpEntity<Object> httpEntity = new HttpEntity<>(body, headers);
        try {
            ResponseEntity<AccessTokenMapper> tokenResponse = restTemplate.exchange(
                    url, HttpMethod.POST, httpEntity, AccessTokenMapper.class
            );
            UserDetail userDetailResponse = new UserDetail();
            if (tokenResponse.getBody() != null) {
                setUserDetailFromToken(userDetailResponse, tokenResponse, userDetail.getApplicationType());

                ResponseCookie responseCookie = ResponseCookie.from("refresh_token", tokenResponse.getBody().getRefresh_token())
                        .httpOnly(true)
                        .sameSite("None")
                        .secure(true)
                        .maxAge(86400)        // One Day
                        .build();
                httpResponse.addCookie(responseCookie);
                httpResponse.setStatusCode(HttpStatus.BAD_REQUEST);
            }

            return ResponseHandler.generateResponse(userDetailResponse);
        } catch (HttpClientErrorException e) {
            e.printStackTrace();
            ErrorResponse response = new ErrorResponse("Invalid username/password");
            response.setDetailMessage(e.getMessage());
            response.setResponseStatus(e.getStatusCode());
            response.setResponseCode(e.getRawStatusCode());
            return ResponseHandler.generateResponse(HttpStatus.UNAUTHORIZED, "Invalid username/password",response);
        }
    }

    private void setUserDetailFromToken(UserDetail userDetailResponse, ResponseEntity<AccessTokenMapper> tokenResponse, Integer applicationType) {
        if (tokenResponse.getBody() != null) {
            userDetailResponse.setToken(tokenResponse.getBody().getAccess_token());
            userDetailResponse.setUserId(tokenResponse.getBody().getUserId());
            userDetailResponse.setUserType(tokenResponse.getBody().getUserType());
            userDetailResponse.setUniversityId(tokenResponse.getBody().getUniversityId());
            userDetailResponse.setApplicationType(applicationType);
            userDetailResponse.setRefresh_token(tokenResponse.getBody().getRefresh_token());
            userDetailResponse.setExpires_in(tokenResponse.getBody().getExpires_in());
        }
    }

    @PostMapping(value = "/refreshToken")
    public ResponseEntity<?> refreshToken(@Valid @RequestBody Token token) {
        log.info("Checking Token " + token.getToken());
        final String url = oauthUrl + "/oauth/token";
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(oauthClientId, oauthClientSecret);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("refresh_token", token.getToken());
        body.add("grant_type", "refresh_token");
        HttpEntity<Object> httpEntity = new HttpEntity<>(body, headers);
        try {
            ResponseEntity<AccessTokenMapper> tokenResponse = restTemplate.exchange(
                    url, HttpMethod.POST, httpEntity, AccessTokenMapper.class
            );
            UserDetail userDetailResponse = new UserDetail();
            if (tokenResponse.getBody() != null) {
                setUserDetailFromToken(userDetailResponse, tokenResponse, tokenResponse.getBody().getApplicationType());
            }

            return ResponseHandler.generateResponse(userDetailResponse);
        } catch (HttpClientErrorException e) {
            e.printStackTrace();
            ErrorResponse response = new ErrorResponse("Invalid username/password");
            response.setDetailMessage(e.getMessage());
            response.setResponseStatus(e.getStatusCode());
            response.setResponseCode(e.getRawStatusCode());
            return ResponseHandler.generateResponse(HttpStatus.UNAUTHORIZED, "Invalid username/password",response);
        }
    }

    @PostMapping(value = "/checkToken")
    public ResponseEntity<?> checkToken(@Valid @RequestBody Token token) {
        log.info("Checking Token " + token.getToken());
        // Checking token validity
        if (jwtUtil.isInvalid(token.getToken())) {
            return ResponseHandler.generateResponse(HttpStatus.UNAUTHORIZED, "Invalid Token");
        }
        return ResponseHandler.generateResponse(HttpStatus.OK, "Token is Valid");
    }

    @PostMapping(value = "/logout")
    public ResponseEntity<?> logout(@Valid @RequestBody Token token) {
        log.info("Log out Token: " + token.getToken());
        // Checking token validity
        if (!jwtUtil.isInvalid(token.getToken())) {
            long ttlForToken = jwtUtil.getTTLForToken(token.getToken());
            log.info("TTL for token on logout " + ttlForToken);
            if (ttlForToken > 0) {
                log.info("Adding token to blacklist");
                blackListedTokens.put(token.getToken(), "1", ttlForToken, TimeUnit.MILLISECONDS);
            }
        }
        log.info("BlackListed Tokens: " + blackListedTokens);
        return ResponseHandler.generateResponse(HttpStatus.OK, "Logout Successful");
    }
}
