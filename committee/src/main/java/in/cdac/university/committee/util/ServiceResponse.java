package in.cdac.university.committee.util;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ServiceResponse {

    private Integer status;
    private String message;
    private Object responseObject;

    public static ServiceResponse errorResponse(String message) {
        return ServiceResponse.builder()
                .status(0)
                .message(message)
                .build();
    }
}
