package in.cdac.university.usm.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@ToString
public class RoleMenuBean {
    private Integer gnumRoleMenuSlno;

    @NotNull(message = "Role Name is mandatory")
    private Integer gnumRoleId;

    @NotNull(message = "Module Name is mandatory")
    private Integer gnumModuleId;

    private Integer[] gnumMenuId;
    private Integer[] initialRightOptions;
    private Date gdtEntryDate;
    private Integer gnumIsvalid;
    private Integer gnumDisplayOrder;
    private Long gnumEntryBy;
}
