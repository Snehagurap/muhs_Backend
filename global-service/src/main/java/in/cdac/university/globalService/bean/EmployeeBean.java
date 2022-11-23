package in.cdac.university.globalService.bean;

import in.cdac.university.globalService.util.annotations.ComboKey;
import in.cdac.university.globalService.util.annotations.ComboValue;
import in.cdac.university.globalService.util.annotations.ListColumn;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class EmployeeBean {

    @ListColumn(omit = true)
    @ComboKey
    private Long unumEmpId;

    private Integer unumIsvalid;

    private String deanforpanel;

    private String lictlPgrecog;

    private String lictlUgapproved;

    private String selectedfor;

    private Integer selectedforlicId;

    private Date udtEffFrom;

    private Date udtEffTo;

    private Date udtEntryDate;

    private Date udtTDob;

    private Integer unumDeanforpanel;

    private Long unumEntryUid;

    @ListColumn(order = 3, name = "Mobile No.")
    @NotNull(message = "Mobile number is mandatory")
    private Long unumMobileNo;

    private Integer unumTAppointtypeId;

    private Integer unumTPgrecog;

    private Integer unumUgapproved;

    private Integer unumUnivId;

    private Integer unumWillingnessToWorkOnLic;

    private String ustrDescription;

    private String ustrTAppointtype;

    @ListColumn(order = 4, name = "E-mail")
    @NotBlank(message = "E-mail is mandatory")
    private String ustrTEmailid;

    private String ustrTPanNo;

    @ListColumn(order = 2, name = "Teacher Name")
    @ComboValue
    @NotBlank(message = "Teacher name is mandatory")
    private String ustrEmpName;

    private String willingnessToWorkOnLic;
    private Integer unumDeptId;
    private String ustrEmpId;
    private Integer unumIsTeacher;
    private Integer unumIsSelectedfor;
    private Integer unumTotLicChairmanCount;
    private Integer unumTotLicMember1Count;
    private Integer unumTotLicMember2Count;
    private Integer unumAyLicChairmanCount;
    private Integer unumAyLicMember1Count;
    private Integer unumAyLicMember2Count;

    private List<Long> employeesToFlag;


    private String ustrTAadharNo;
    private Integer unumEmpDesigid;
    private Date udtUgJoiningDate;
    private Date udtPgJoiningDate;

    private Long unumCollegeId;
    private List<EmployeeProfileBean> employeeProfileList;
}
