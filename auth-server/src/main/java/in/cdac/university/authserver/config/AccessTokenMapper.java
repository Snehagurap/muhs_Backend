package in.cdac.university.authserver.config;

import lombok.Data;

@Data
public class AccessTokenMapper {
    private Integer userId;
    private Integer userType;
    private Integer universityId;
}
