package in.cdac.university.apigateway.response;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class UserDetail {
    private String username;
    private String password;
    private String token;
    private String refresh_token;
    private Integer userId;
    private Integer userType;
    private Integer universityId;
    private Integer expires_in;

    @NotNull(message = "Application Type cannot be blank")
    @Min(1)
    @Max(3)
    private Integer applicationType;  // 1 for Application, 2 for User management, 3 for User management superuser
}
