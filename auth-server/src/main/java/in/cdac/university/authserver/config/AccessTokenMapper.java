package in.cdac.university.authserver.config;

import lombok.Data;

@Data
public class AccessTokenMapper {
    private Long userId;
    private Integer userType;
    private Integer universityId;
    private Integer applicationType;
    private Integer userCategory;
}
