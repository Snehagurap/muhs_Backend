package in.cdac.university.apigateway.controller;

import in.cdac.university.apigateway.config.AccessTokenMapper;
import in.cdac.university.apigateway.exception.ErrorResponse;
import in.cdac.university.apigateway.response.ResponseHandler;
import in.cdac.university.apigateway.response.UserDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api")
@Slf4j
public class LoginController {

    @Value("${config.oauth2.url}")
    private String oauthUrl;

    @Value("${config.oauth2.clientId}")
    private String oauthClientId;

    @Value("${config.oauth2.clientSecret}")
    private String oauthClientSecret;

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@RequestBody UserDetail userDetail) {
        final String url = oauthUrl + "/oauth/token";
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(oauthClientId, oauthClientSecret);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

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
                userDetailResponse.setToken(tokenResponse.getBody().getAccess_token());
                userDetailResponse.setUserId(tokenResponse.getBody().getUserId());
                userDetailResponse.setUserType(tokenResponse.getBody().getUserType());
                userDetailResponse.setUniversityId(tokenResponse.getBody().getUniversityId());
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
}
