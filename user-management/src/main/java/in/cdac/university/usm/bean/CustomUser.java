package in.cdac.university.usm.bean;

import lombok.Data;

@Data
public class CustomUser {
    private Long gnumLogId;
    private String userId;
    private String ipAddress;
    private Integer universityId;
    private String userAgent;
}
