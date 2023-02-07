package in.cdac.university.studentWelfare.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import in.cdac.university.studentWelfare.exception.ApplicationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Component
@RefreshScope
@Slf4j
public class RestUtility {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    private static String serviceCommitteeUrl;

    private static String servicePlanningBoardUrl;

    private static String serviceUsmUrl;

    private static String serviceGlobalUrl;

    @Value("${config.service.committee.url}")
    public void setServiceCommitteeUrl(String url) {
        serviceCommitteeUrl = url;
    }

    @Value("${config.service.usm.url}")
    public void setServiceUsmUrl(String url) {
        serviceUsmUrl = url;
    }

    @Value("${config.service.global.url}")
    public void setServiceGLobalUrl(String url) {
        serviceGlobalUrl = url;
    }

    @Value("${config.service.pb.url}")
    public void setServicePlanningBoardUrl(String url) {
        servicePlanningBoardUrl = url;
    }

    public enum SERVICE_TYPE {
        COMMITTEE(serviceCommitteeUrl),
        GLOBAL(serviceGlobalUrl),
        USM(serviceUsmUrl),

        PLANNING_BOARD(servicePlanningBoardUrl);

        public final String url;
        SERVICE_TYPE(String url) {
            this.url = url;
        }
    }

    public <T> T callService(SERVICE_TYPE serviceType, String url, Class<T> returnType, boolean shouldThrow) {
        try {
            HttpHeaders headers = getHttpHeaders();

            HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

            var restResponse = restTemplate.exchange(
                    serviceType.url + "/" + url, HttpMethod.GET, httpEntity, String.class
            );

            if (restResponse.getStatusCode() == HttpStatus.OK) {
                var jsonString = restResponse.getBody();
                ObjectMapper mapper = new ObjectMapper();
                JsonNode actualObject = mapper.readTree(jsonString);
                int status = actualObject.get("status").asInt();
                if (status == 0) {
                    String message = actualObject.get("message").asText();
                    log.error("Unable to call Web Service: {}", message);
                    return null;
                }
                if (actualObject.get("data") != null)
                    return objectMapper.convertValue(actualObject.get("data"), returnType);
            }
        } catch (HttpClientErrorException e) {
            if (shouldThrow)
                throw new ApplicationException("Service is not working");
            log.error("Client Error: {}", e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public <T> T get(SERVICE_TYPE serviceType, String url, Class<T> returnType) {
        return callService(serviceType, url, returnType, false);
    }

    public <T> T getOrThrow(SERVICE_TYPE serviceType, String url, Class<T> returnType) {
        return callService(serviceType, url, returnType, true);
    }

    public <T> T postForData(SERVICE_TYPE serviceType, String url, Object requestBody, Class<T> returnType) {
        try {
            HttpHeaders headers = getHttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            Gson gson = new Gson();
            HttpEntity<String> httpEntity = new HttpEntity<>(gson.toJson(requestBody), headers);

            var restResponse = restTemplate.exchange(
                    serviceType.url + "/" + url, HttpMethod.POST, httpEntity, String.class
            );

            log.debug("Response status: {}", restResponse.getStatusCode());
            if (restResponse.getStatusCode() == HttpStatus.OK) {
                var jsonString = restResponse.getBody();
                log.debug("Response Body: {}", jsonString);
                ObjectMapper mapper = new ObjectMapper();
                JsonNode actualObject = mapper.readTree(jsonString);
                int status = actualObject.get("status").asInt();
                if (status == 0) {
                    String message = actualObject.get("message").asText();
                    log.error("Unable to call Web Service: {}", message);
                    return null;
                }
                return objectMapper.convertValue(actualObject.get("data"), returnType);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public <T> T postForMessage(SERVICE_TYPE serviceType, String url, Object requestBody, Class<T> returnType) {
        try {
            HttpHeaders headers = getHttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            Gson gson = new Gson();
            HttpEntity<String> httpEntity = new HttpEntity<>(gson.toJson(requestBody), headers);

            var restResponse = restTemplate.exchange(
                    serviceType.url + "/" + url, HttpMethod.POST, httpEntity, String.class
            );

            log.debug("Response status: {}", restResponse.getStatusCode());
            if (restResponse.getStatusCode() == HttpStatus.OK) {
                var jsonString = restResponse.getBody();
                log.debug("Response Body: {}", jsonString);
                ObjectMapper mapper = new ObjectMapper();
                JsonNode actualObject = mapper.readTree(jsonString);
                int status = actualObject.get("status").asInt();
                if (status == 0) {
                    String message = actualObject.get("message").asText();
                    log.error("Unable to call Web Service: {}", message);
                    return null;
                }
                return objectMapper.convertValue(actualObject.get("message"), returnType);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private HttpHeaders getHttpHeaders() {
        // Get Session Details
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        String userDetail = request.getHeader("userDetail");
        String userId = request.getHeader("userId");

        HttpHeaders headers = new HttpHeaders();
        headers.set("userDetail", userDetail);
        headers.set("userId", userId);
        return headers;
    }
}
