package in.cdac.university.globalService.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.sisyphsu.dateparser.DateParserUtils;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.Map;
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

    private static String serviceCommiteeUrl;

    @Value("${config.service.committee.url}")
    public void setServiceCommiteeUrl(String url) {
        serviceCommiteeUrl = url;
    }

    public enum SERVICE_TYPE {
        COMMITTEE(serviceCommiteeUrl);

        public final String url;
        SERVICE_TYPE(String url) {
            this.url = url;
        }
    }

    public <T> T get(SERVICE_TYPE serviceType, String url, Class<T> returnType) {
        try {
            // Get Session Details
            HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
            String userDetail = request.getHeader("userDetail");

            HttpHeaders headers = new HttpHeaders();
            headers.set("userDetail", userDetail);

            HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

            ResponseEntity<RestResponse> responseEntity = restTemplate.exchange(
                    serviceType.url + "/" + url, HttpMethod.GET, httpEntity, RestResponse.class
            );

            RestResponse restResponse = responseEntity.getBody();
            if (restResponse == null) {
                return null;
            }

            if (restResponse.getStatus() != 0) {
                T data = returnType.getDeclaredConstructor().newInstance();
                Map<String, String> objectMap = restResponse.getData();
                for (Field field : data.getClass().getDeclaredFields()) {
                    field.setAccessible(true);
                    if (objectMap.containsKey(field.getName())) {
                        Object obj = toObject(field.getType(), objectMap.get(field.getName()));
                        field.set(data, obj);
                    }
                }
                return data;
            }
            log.info("Error Message: {}", restResponse.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private Object toObject(Class<?> clazz, String value) {
        if (value == null || value.equals("null"))
            return null;
        if (Boolean.class == clazz)
            return Boolean.parseBoolean(value);
        if (Byte.class == clazz)
            return Byte.parseByte(value);
        if (Short.class == clazz)
            return Short.parseShort(value);
        if (Integer.class == clazz)
            return Integer.parseInt(value);
        if (Long.class == clazz)
            return Long.parseLong(value);
        if (Float.class == clazz)
            return Float.parseFloat(value);
        if (Double.class == clazz)
            return Double.parseDouble(value);
        if (Date.class == clazz) {
            return DateParserUtils.parseDate(value);
        }
        return value;
    }
}
