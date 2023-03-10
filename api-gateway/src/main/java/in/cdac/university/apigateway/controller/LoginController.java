package in.cdac.university.apigateway.controller;

import cn.apiclub.captcha.Captcha;
import com.google.common.hash.Hashing;
import in.cdac.university.apigateway.bean.CaptchaBean;
import in.cdac.university.apigateway.bean.CustomUser;
import in.cdac.university.apigateway.bean.Token;
import in.cdac.university.apigateway.config.AccessTokenMapper;
import in.cdac.university.apigateway.config.JwtUtil;
import in.cdac.university.apigateway.exception.ErrorResponse;
import in.cdac.university.apigateway.response.ResponseHandler;
import in.cdac.university.apigateway.response.UserDetail;
import in.cdac.university.apigateway.util.CaptchaUtil;
import lombok.extern.slf4j.Slf4j;
import net.jodah.expiringmap.ExpirationPolicy;
import net.jodah.expiringmap.ExpiringMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.*;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api")
@Slf4j
@CrossOrigin(origins = "*")
@RefreshScope
public class LoginController {

    @Value("${config.oauth2.url}")
    private String oauthUrl;

    @Value("${config.service.usm.url}")
    private String usmUrl;

    @Value("${config.oauth2.clientId}")
    private String oauthClientId;

    @Value("${config.oauth2.clientSecret}")
    private String oauthClientSecret;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private JwtUtil jwtUtil;

    public final static ExpiringMap<String, String> blackListedTokens = ExpiringMap.builder()
            .maxSize(10000)
            .variableExpiration()
            .expirationPolicy(ExpirationPolicy.CREATED)
            .build();

    public final static ExpiringMap<UUID, String> captchaStore = ExpiringMap.builder()
            .maxSize(50000)
            .variableExpiration()
            .expirationPolicy(ExpirationPolicy.CREATED)
            .build();

    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserDetail userDetail,
                                   @RequestHeader(HttpHeaders.USER_AGENT) String userAgent,
                                   ServerHttpResponse httpResponse, ServerHttpRequest httpRequest) {
        // Check for data tampering
        if (httpRequest.getHeaders().containsKey("fhttf")) {
            String clientToken = Optional.ofNullable(httpRequest.getHeaders().get("fhttf")).orElse(List.of("")).get(0);
            if (!clientToken.isBlank()) {
                String serverData = userDetail.getUsername() + userDetail.getPassword() + userDetail.getCaptchaId()
                        + userDetail.getCaptcha() + userDetail.getUserCategory() + userDetail.getApplicationType() + userDetail.getIpAddressEncoded();
                String serverToken = Hashing.sha256().hashString(serverData, StandardCharsets.UTF_8).toString();
                if (!clientToken.equals(serverToken)) {
                    log.error("Server Data: {}", serverData);
                    log.error("Server Token: {}", serverToken);
                    log.error("Client Token: {}", clientToken);
                    return ResponseHandler.generateResponse(HttpStatus.UNAUTHORIZED, "Form data tampered", "1");
                }
            }
        }

        if (userDetail.getIsPostman() == null && userDetail.getApplicationType() == 1) {
            // Check for captcha
            String captchaId = userDetail.getCaptchaId();
            if (captchaId == null) {
                return ResponseHandler.generateResponse(HttpStatus.UNAUTHORIZED, "Invalid Captcha", "1");
            }

            UUID captchaUUID = UUID.fromString(captchaId);
            String sessionCaptcha = captchaStore.get(captchaUUID);
            String userCaptcha = userDetail.getCaptcha();
            if (!captchaStore.containsKey(captchaUUID) || !userCaptcha.equals(sessionCaptcha)) {
                return ResponseHandler.generateResponse(HttpStatus.UNAUTHORIZED, "Invalid Captcha", "1");
            }
        }

        userDetail.setUsername(new String(Base64.getDecoder().decode(userDetail.getUsername().getBytes())));

        String url = oauthUrl + "/oauth/token";
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(oauthClientId, oauthClientSecret);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("applicationType", userDetail.getApplicationType().toString());
        if (userDetail.getUserCategory() != null)
            headers.set("userCategory", userDetail.getUserCategory().toString());

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("username", userDetail.getUsername());
        body.add("password", userDetail.getPassword());
        body.add("grant_type", "password");

        HttpEntity<Object> httpEntity = new HttpEntity<>(body, headers);
        try {
            ResponseEntity<AccessTokenMapper> tokenResponse = restTemplate.exchange(
                    url, HttpMethod.POST, httpEntity, AccessTokenMapper.class
            );
            UserDetail userDetailResponse = setUserDetailFromToken(tokenResponse);
            // Log user Login Details
            try {
                if (tokenResponse.getBody() != null) {
                    url = usmUrl + "/usm/log/login";
                    headers = new HttpHeaders();
                    headers.setContentType(MediaType.APPLICATION_JSON);
                    CustomUser customUser = new CustomUser();
                    customUser.setUserId(tokenResponse.getBody().getUserId().toString());
                    customUser.setUniversityId(tokenResponse.getBody().getUniversityId());
                    customUser.setIpAddress(userDetail.getIpAddress());
                    httpEntity = new HttpEntity<>(customUser, headers);

                    ResponseEntity<CustomUser> response = restTemplate.exchange(url, HttpMethod.POST, httpEntity, CustomUser.class);
                    log.debug("Response Status: {}", response.getStatusCode());
                    if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                        userDetailResponse.setGnumLogId(response.getBody().getGnumLogId());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return ResponseHandler.generateResponse(userDetailResponse);
        } catch (HttpClientErrorException e) {
            e.printStackTrace();
            ErrorResponse response = new ErrorResponse("Invalid username/password");
            response.setDetailMessage(e.getMessage());
            response.setResponseStatus(e.getStatusCode());
            response.setResponseCode(e.getRawStatusCode());
            return ResponseHandler.generateResponse(HttpStatus.UNAUTHORIZED, "Invalid username/password", response);
        }
    }

    private UserDetail setUserDetailFromToken(ResponseEntity<AccessTokenMapper> tokenResponse) {
        UserDetail userDetailResponse = new UserDetail();
        if (tokenResponse.getBody() != null) {
            userDetailResponse.setToken(tokenResponse.getBody().getAccess_token());
            userDetailResponse.setApplicationType(tokenResponse.getBody().getApplicationType());
            userDetailResponse.setRefresh_token(tokenResponse.getBody().getRefresh_token());
            userDetailResponse.setExpires_in(tokenResponse.getBody().getExpires_in());
        }
        return userDetailResponse;
    }

    @PostMapping(value = "/refreshToken")
    public ResponseEntity<?> refreshToken(@Valid @RequestBody Token token) {
        log.debug("Checking Token {} ", token.getToken());
        final String url = oauthUrl + "/oauth/token";
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(oauthClientId, oauthClientSecret);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("applicationType", token.getApplicationType());
        headers.set("userCategory", token.getUserCategory());

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("refresh_token", token.getToken());
        body.add("grant_type", "refresh_token");
        HttpEntity<Object> httpEntity = new HttpEntity<>(body, headers);
        try {
            ResponseEntity<AccessTokenMapper> tokenResponse = restTemplate.exchange(
                    url, HttpMethod.POST, httpEntity, AccessTokenMapper.class
            );
            UserDetail userDetailResponse = setUserDetailFromToken(tokenResponse);

            return ResponseHandler.generateResponse(userDetailResponse);
        } catch (HttpClientErrorException e) {
            e.printStackTrace();
            ErrorResponse response = new ErrorResponse("Invalid username/password");
            response.setDetailMessage(e.getMessage());
            response.setResponseStatus(e.getStatusCode());
            response.setResponseCode(e.getRawStatusCode());
            return ResponseHandler.generateResponse(HttpStatus.UNAUTHORIZED, "Invalid username/password", response);
        }
    }

    @PostMapping(value = "/checkToken")
    public ResponseEntity<?> checkToken(@Valid @RequestBody Token token) {
        log.debug("Checking Token: {}", token.getToken());
        // Checking token validity
        if (jwtUtil.isInvalid(token.getToken())) {
            return ResponseHandler.generateResponse(HttpStatus.UNAUTHORIZED, "Invalid Token");
        }
        return ResponseHandler.generateResponse(HttpStatus.OK, "Token is Valid");
    }

    @PostMapping(value = "/logout")
    public ResponseEntity<?> logout(@Valid @RequestBody Token token,
                                    @RequestHeader(HttpHeaders.USER_AGENT) String userAgent) {
        log.debug("Log out Token: {}", token.getToken());
        // Checking token validity
        if (!jwtUtil.isInvalid(token.getToken())) {
            long ttlForToken = jwtUtil.getTTLForToken(token.getToken());
            log.debug("TTL for token on logout: {}", ttlForToken);
            if (ttlForToken > 0) {
                log.debug("Adding token to blacklist");
                blackListedTokens.put(token.getToken(), "1", ttlForToken, TimeUnit.MILLISECONDS);
            }
        }
        // Logout user form database
        try {
            final String url = usmUrl + "/usm/log/logout";
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            CustomUser customUser = new CustomUser();
            customUser.setUserId(token.getUserId().toString());
            customUser.setIpAddress(token.getIpAddress());
            customUser.setUniversityId(token.getUniversityId());
            customUser.setGnumLogId(token.getGnumLogId());
            HttpEntity<Object> httpEntity = new HttpEntity<>(customUser, headers);

            ResponseEntity<CustomUser> response = restTemplate.exchange(url, HttpMethod.POST, httpEntity, CustomUser.class);
            log.debug("Response Status: {}", response.getStatusCode());
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.debug("BlackListed Tokens: {}", blackListedTokens);
        return ResponseHandler.generateResponse(HttpStatus.OK, "Logout Successful");
    }

    @GetMapping(value = "/captcha/{captchaId}")
    public ResponseEntity<?> captcha(@PathVariable("captchaId") String captchaId) {
        Captcha captcha = CaptchaUtil.createCaptcha(150, 70);
        String answer = captcha.getAnswer();
        String captchaImage = CaptchaUtil.encodeCaptcha(captcha);
        UUID generatedCaptchaId;
        if ("0".equals(captchaId)) {
            generatedCaptchaId = UUID.randomUUID();
        } else {
            generatedCaptchaId = UUID.fromString(captchaId);
        }
        CaptchaBean captchaBean = new CaptchaBean(generatedCaptchaId, captchaImage);
        captchaStore.put(generatedCaptchaId, answer, 30, TimeUnit.MINUTES);
        return ResponseHandler.generateResponse(HttpStatus.OK, captchaBean);
    }
}
