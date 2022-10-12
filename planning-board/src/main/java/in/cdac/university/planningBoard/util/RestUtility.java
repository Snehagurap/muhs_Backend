package in.cdac.university.planningBoard.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Component
@RefreshScope
@Getter
@Slf4j
public class RestUtility {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    private static String serviceCommitteeUrl;
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

    public enum SERVICE_TYPE {
        COMMITTEE(serviceCommitteeUrl),
        GLOBAL(serviceGlobalUrl),
        USM(serviceUsmUrl);

        public final String url;
        SERVICE_TYPE(String url) {
            this.url = url;
        }
    }

    public <T> T get(SERVICE_TYPE serviceType, String url, Class<T> returnType) {
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
                return objectMapper.convertValue(actualObject.get("data"), returnType);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public <T> T post(SERVICE_TYPE serviceType, String url, Object requestBody, Class<T> returnType) {
        try {
            HttpHeaders headers = getHttpHeaders();
            Gson gson = new Gson();
            HttpEntity<String> httpEntity = new HttpEntity<>(gson.toJson(requestBody), headers);

            var restResponse = restTemplate.exchange(
                    serviceType.url + "/" + url, HttpMethod.POST, httpEntity, String.class
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
                return objectMapper.convertValue(actualObject.get("data"), returnType);
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

        HttpHeaders headers = new HttpHeaders();
        headers.set("userDetail", userDetail);
        return headers;
    }
}
