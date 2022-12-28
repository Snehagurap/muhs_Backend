package in.cdac.university.apigateway.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FormDataTamperedException extends RuntimeException {

    public FormDataTamperedException(String message) {
        this.message = message;
    }

    private String message;
    private String serverValue;

    private String clientToken;

    private String serverToken;

    @Override
    public String toString() {
        return "Server Value: " + serverValue + ", Server Token: " + serverToken + ", Client Token: " + clientToken;
    }
}
