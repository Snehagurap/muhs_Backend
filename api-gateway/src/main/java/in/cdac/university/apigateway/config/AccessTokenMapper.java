package in.cdac.university.apigateway.config;

import lombok.Data;

@Data
public class AccessTokenMapper {
    private String access_token;
    private String token_type;
    private String expires_in;
    private String refresh_token;
    private String scope;

    private Integer userId;
    private Integer userType;
    private Integer universityId;
}
