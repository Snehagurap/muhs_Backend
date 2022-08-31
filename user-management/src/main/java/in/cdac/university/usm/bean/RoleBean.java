package in.cdac.university.usm.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import in.cdac.university.usm.util.annotations.ComboKey;
import in.cdac.university.usm.util.annotations.ComboValue;
import in.cdac.university.usm.util.annotations.ListColumn;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.util.Date;

@Getter
@Setter
@ToString
public class RoleBean extends GlobalBean {
    @ComboKey
    @ListColumn(omit = true)
    private Integer gnumRoleId;

    @ListColumn(order = 1, name = "Role Name")
    @ComboValue
    @NotBlank(message = "Role Name is mandatory")
    private String gstrRoleName;

    @NotNull(message = "Module Id is mandatory")
    private Integer gnumModuleId;

    private Integer gnumEntryBy;

    @JsonFormat(pattern = "dd-MMM-yyyy")
    @NotNull(message = "Effective Date is mandatory")
    @PastOrPresent(message = "Effective Date should be less than or equal to current date.")
    private Date gdtEffectDate;

    @NotNull(message = "Status is mandatory")
    private Integer gblIsvalid;

    @ListColumn(order = 2, name = "Module Name")
    private String moduleName;

    private Date gdtEntrydate;
}
