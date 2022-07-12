package in.cdac.university.apigateway.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

@Data
public class ErrorResponse implements Serializable {
    private final String errorMessage;
    private String detailMessage;
    private int responseCode;
    private HttpStatus responseStatus;
}
