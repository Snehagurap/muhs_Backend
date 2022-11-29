package in.cdac.university.committee.util;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.ByteArrayInputStream;
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

    public static ResponseEntity<?> generateFileResponse(byte[] fileBytes, String fileName) {
        if (fileBytes == null)
            return null;

        InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(fileBytes));
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileName)
                .contentLength(fileBytes.length)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}
