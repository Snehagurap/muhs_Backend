package in.cdac.university.globalService.util;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;


@Getter
@Builder
@ToString
public class ServiceResponse  {

    private final Integer status;
    private String message;
    private Object responseObject;

    public static ServiceResponse errorResponse(String message) {
        return ServiceResponse.builder()
                .status(0)
                .message(message)
                .build();
    }

    public static ServiceResponse successObject(Object responseObject) {
        return ServiceResponse.builder()
                .status(1)
                .responseObject(responseObject)
                .build();
    }

    public static ServiceResponse successMessage(String message) {
        return ServiceResponse.builder()
                .status(1)
                .message(message)
                .build();
    }

    public static ServiceResponse successResponse(String message, Object responseObject) {
        return ServiceResponse.builder()
                .status(1)
                .responseObject(responseObject)
                .message(message)
                .build();
    }
}
