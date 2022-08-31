package in.cdac.university.usm.util;

import lombok.*;

@Getter
@Builder
@ToString
public class ServiceResponse {

    private final Integer status;
    private String message;
    private Object responeObject;

    public static ServiceResponse errorResponse(String message) {
        return ServiceResponse.builder()
                .status(0)
                .message(message)
                .build();
    }
}
