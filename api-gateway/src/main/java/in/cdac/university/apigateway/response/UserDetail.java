package in.cdac.university.apigateway.response;

import lombok.Data;

@Data
public class UserDetail {
    private String username;
    private String password;
    private String token;
    private Integer userId;
    private Integer userType;
    private Integer universityId;
}
