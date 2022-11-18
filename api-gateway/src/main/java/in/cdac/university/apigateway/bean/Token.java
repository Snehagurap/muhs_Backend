package in.cdac.university.apigateway.bean;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Base64;

@Data
public class Token {

    @NotNull(message = "Token is mandatory")
    private String token;

    private String ipAddress;

    private Long gnumLogId;

    private String applicationType;

    private Long userId;

    private Integer universityId;

    public String getIpAddress() {
        try {
            if (this.ipAddress != null && !this.ipAddress.isBlank())
                return new String(Base64.getDecoder().decode(this.ipAddress));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
