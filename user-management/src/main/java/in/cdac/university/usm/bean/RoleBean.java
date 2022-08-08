package in.cdac.university.usm.bean;

import in.cdac.university.usm.util.ComboKey;
import in.cdac.university.usm.util.ComboValue;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class RoleBean extends GlobalBean {
    @ComboKey
    private Integer gnumRoleId;
    @ComboValue
    private String gstrRoleName;
    private Integer gnumModuleId;
    private Integer gnumEntryBy;
    private Date gdtEffectDate;
    private Integer gblIsvalid;
}
