package in.cdac.university.apigateway.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FormDataTamperedException extends RuntimeException {
    private String message;
}
