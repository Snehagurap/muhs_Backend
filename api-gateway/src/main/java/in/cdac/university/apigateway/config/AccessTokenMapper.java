package in.cdac.university.apigateway.config;

import lombok.Data;

@Data
public class AccessTokenMapper {
    private String access_token;
    private String token_type;
    private Integer expires_in;
    private String refresh_token;
    private String scope;

    private Long userId;
    private Integer userType;
    private Integer universityId;
    private Integer applicationType;
    private String error;
    private String error_description;
    private Integer userCategory;
}
