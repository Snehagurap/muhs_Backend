package in.cdac.university.apigateway.bean;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class RefreshToken {

    @NotNull(message = "Token is mandatory")
    private String token;

    @NotNull(message = "Application Type is mandatory")
    private Integer applicationType;
}
