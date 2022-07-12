package in.cdac.university.apigateway.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ResponseHandler {

    public static ResponseEntity<Object> generateResponse(HttpStatus status, String message, Object responseObject) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", status.value());
        map.put("message", message);
        map.put("data", responseObject);

        return new ResponseEntity<>(map, status);
    }

    public static ResponseEntity<Object> generateResponse(HttpStatus status, String message) {
        return generateResponse(status, message, null);
    }

    public static ResponseEntity<Object> generateResponse(String message) {
        return generateResponse(HttpStatus.OK, message, null);
    }

    public static ResponseEntity<Object> generateResponse(HttpStatus status, Object responseObject) {
        return generateResponse(status, null, responseObject);
    }

    public static ResponseEntity<Object> generateResponse(Object responseObject) {
        return generateResponse(HttpStatus.OK, null, responseObject);
    }
}
