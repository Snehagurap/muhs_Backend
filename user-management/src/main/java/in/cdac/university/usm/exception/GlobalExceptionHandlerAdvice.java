package in.cdac.university.usm.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import in.cdac.university.usm.util.ResponseHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandlerAdvice {

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Object> handleException(Exception e) {
        e.printStackTrace();
        log.error("Handling Exception: " + e.getClass().getCanonicalName());
        return ResponseHandler.generateErrorResponse("Unable to process request at this time. Please try again");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("Handling MethodArgumentNotValidException: " + e.getClass().getCanonicalName());
        e.printStackTrace();
        BindingResult result = e.getBindingResult();
        Map<String, String> errorMap = new HashMap<>();
        List<FieldError> fieldErrors = result.getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        String errorMessage = String.join(", ", errorMap.values());
        return ResponseHandler.generateResponse(0, errorMessage, errorMap);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleBadRequestException(HttpMessageNotReadableException e) {
        log.error("Handling HttpMessageNotReadableException: " + e.getClass().getCanonicalName());
        e.printStackTrace();
        String message = e.getMessage();
        Throwable cause = e.getCause();
        if (cause instanceof InvalidFormatException ife) {
            message = "Invalid Request Field: " + ife.getPath().get(0).getFieldName();
        }
        return ResponseHandler.generateResponse(0, "Parameter types mismatch: Bad Request", message);
    }
}
