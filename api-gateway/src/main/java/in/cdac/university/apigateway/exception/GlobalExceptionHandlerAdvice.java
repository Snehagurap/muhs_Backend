package in.cdac.university.apigateway.exception;

import in.cdac.university.apigateway.response.ResponseHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        log.info("Handling Exception:, {} ", e.getClass().getCanonicalName());
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
        return ResponseHandler.generateResponse(HttpStatus.EXPECTATION_FAILED, "Unable to process request at this time. Please try again", errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        e.printStackTrace();
        BindingResult result = e.getBindingResult();
        Map<String, Object> errorMap = new HashMap<>();
        List<FieldError> fieldErrors = result.getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, "Required parameters are missing", errorMap);
    }

    @ExceptionHandler(FormDataTamperedException.class)
    public ResponseEntity<Object> handleFormDataTamperedException(FormDataTamperedException e) {
        e.printStackTrace();
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
        errorResponse.setDetailMessage(e.toString());
        return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, "Form data tampered", errorResponse);
    }
}
