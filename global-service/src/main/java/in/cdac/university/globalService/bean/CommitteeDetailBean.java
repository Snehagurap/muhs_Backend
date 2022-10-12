package in.cdac.university.globalService.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class CommitteeDetailBean {

    private Long unumComroleid;
    private Integer unumIsvalid;
    private Date udtEntryDate;
    private Long unumComid;
    private Integer unumComrolsno;
    private Integer unumRoleId;
    private Integer unumRoleCfacultyId;
    private Integer unumRoleDepartmentId;
    private Integer unumRolePostId;
    private Integer unumRoleMinExpYears;
    private Integer unumRoleMaxYearlyOccurance;
    private Integer unumRoleMinYearlyOccurance;
    private Integer unumRoleIsExternal;
    private Integer unumRoleMaxExpYears;
    private Integer unumUnivId;
    private String ustrComroleDescription;
    private String departmentName;
    private String designationName;
    private String roleName;
    private String facultyName;
}
