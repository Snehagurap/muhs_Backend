package in.cdac.university.usm.bean;

import in.cdac.university.usm.util.annotations.ComboKey;
import in.cdac.university.usm.util.annotations.ComboValue;
import in.cdac.university.usm.util.annotations.ListColumn;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@ToString
public class UserTypeBean extends GlobalBean{

    @ListColumn(omit = true)
    @ComboKey(index = 1)
    private Integer gnumUserTypeId;

    private Date gdtEntryDate;
    private Integer gnumAlertFlag;

    @ComboKey(index = 4)
    @NotNull(message = "Default Dataset is mandatory")
    private Integer gnumDatasetId;
    private Integer gnumEntryBy;

    @NotNull(message = "Status is mandatory")
    private Integer gnumIsvalid;

    @NotNull(message = "Module is mandatory")
    private Integer gnumModuleId;

    @ComboKey(index = 2)
    @NotNull(message = "Default Role is mandatory")
    private Integer gnumRoleId;

    private Integer gnumTaskFlag;

    @NotNull(message = "User category is mandatory")
    private Integer gnumUserCatId;

    @NotBlank(message = "User Type name is mandatory")
    @ListColumn(order = 1, name = "User Type Name")
    @ComboValue
    private String gstrUserTypeName;

    @ComboKey(index = 3)
    @ListColumn(order = 2, name = "Default Role")
    private String defaultRoleName;

    @ComboKey(index = 5)
    @ListColumn(order = 2, name = "Default Dataset")
    private String defaultDataset;
}
