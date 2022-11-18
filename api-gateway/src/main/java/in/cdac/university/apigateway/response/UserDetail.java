package in.cdac.university.apigateway.response;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Base64;

@Data
public class UserDetail {
    private Long gnumLogId;
    @NotNull(message = "Username is mandatory")
    private String username;

    @NotNull(message = "Password is mandatory")
    private String password;

    private String token;
    private String refresh_token;
    private Long userId;
    private Integer userType;
    private Integer universityId;
    private Integer expires_in;
    private String captchaId;
    private String captcha;
    private Integer isPostman;
    private Integer userCategory;      /* 1: University, 5: Applicant */

    @NotNull(message = "Application Type cannot be blank")
    @Min(1)
    @Max(3)
    private Integer applicationType;  // 1 for Application, 2 for User management, 3 for User management superuser
    private String ipAddress;

    public String getIpAddress() {
        try {
            if (this.ipAddress != null && !this.ipAddress.isBlank())
                return new String(Base64.getDecoder().decode(this.ipAddress));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public String getIpAddressEncoded() {
        return this.ipAddress;
    }
}
