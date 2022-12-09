package in.cdac.university.committee.bean;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class CommitteeDetailBean {

    private Long unumComroleid;
    private Integer unumIsvalid;
    private Date udtEntryDate;
    private Long unumComid;
    private Integer unumComrolsno;

    @NotNull(message = "Title/Role is mandatory")
    private Integer unumRoleId;

    @NotNull(message = "Faculty is mandatory")
    private Integer unumRoleCfacultyId;

    @NotNull(message = "Department is mandatory")
    private Integer unumRoleDepartmentId;

    @NotNull(message = "Designation is mandatory")
    private Integer unumRolePostId;

    @NotNull(message = "Experience is mandatory")
    private Integer unumRoleMinExpYears;

    @NotNull(message = "Max Yearly Occurrence is mandatory")
    private Integer unumRoleMaxYearlyOccurance;

    @NotNull(message = "Min Yearly Occurrence is mandatory")
    private Integer unumRoleMinYearlyOccurance;

    @NotNull(message = "Min Approved Experience is mandatory")
    private Integer unumMinApprovedExp;

    private Integer unumRoleIsExternal;
    private Integer unumRoleMaxExpYears;
    private Integer unumUnivId;
    private String ustrComroleDescription;
    private String departmentName;
    private String designationName;

    private String roleName;
    private String facultyName;

    private List<ComboBean> teachersCombo;
}
