package in.cdac.university.apigateway.bean;

import lombok.Data;

@Data
public class CheckTokenResponse {
    private Integer applicationType;
    private String user_name;
    private String[] scope;
    private boolean active;
    private Integer userType;
    private long exp;
    private Integer userId;
    private String[] authorities;
    private String error;
    private String error_description;
}
