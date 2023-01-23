package in.cdac.university.studentWelfare.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ResponseHandler {

    public static ResponseEntity<Object> generateResponse(int status, String message, Object responseObject) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", status);
        map.put("message", message);
        map.put("data", responseObject);

        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    public static ResponseEntity<Object> generateOkResponse(Object responseObject) {
        return generateResponse(1, null, responseObject);
    }

    public static ResponseEntity<Object> generateErrorResponse(String message) {
        return generateResponse(0, message, null);
    }

    public static ResponseEntity<Object> generateResponse(ServiceResponse serviceResponse) {
        return generateResponse(serviceResponse.getStatus(), serviceResponse.getMessage(), serviceResponse.getResponseObject());
    }
}
