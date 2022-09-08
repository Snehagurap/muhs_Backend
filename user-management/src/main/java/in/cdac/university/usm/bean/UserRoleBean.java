package in.cdac.university.usm.bean;

import in.cdac.university.usm.util.annotations.ComboKey;
import in.cdac.university.usm.util.annotations.ComboValue;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UserRoleBean {
    @ComboKey
    private Integer gnumRoleId;

    @NotNull(message = "User is mandatory")
    private Integer gnumUserId;
    private Integer gnumIsDefault;
    private Integer gblIsvalid;

    private Integer[] mappedRoles;

    @ComboValue
    private String roleName;
}
