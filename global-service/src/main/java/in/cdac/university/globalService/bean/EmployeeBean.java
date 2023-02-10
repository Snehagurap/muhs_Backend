package in.cdac.university.globalService.bean;

import in.cdac.university.globalService.util.annotations.ComboKey;
import in.cdac.university.globalService.util.annotations.ComboValue;
import in.cdac.university.globalService.util.annotations.ListColumn;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Objects;

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

    private Integer unumIsexternal;
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

    private Integer unumIsSelected;
    private List<Long> employeesToFlag;


    private String ustrTAadharNo;
    private Integer unumEmpDesigid;
    private Date udtUgJoiningDate;
    private Date udtPgJoiningDate;

    private Long unumCollegeId;
    private List<EmployeeProfileBean> employeeProfileList;
    private List<EmployeeCurrentDetailBean> employeeCurrentDetailBeanList;

    private EmployeeProfileBean employeeProfileBean;

    private EmployeeCurrentDetailBean employeeCurrentDetailBean;

    private String ustrEmpLName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeBean that = (EmployeeBean) o;
        return Objects.equals(unumEmpId, that.unumEmpId) && Objects.equals(unumIsvalid, that.unumIsvalid) && Objects.equals(deanforpanel, that.deanforpanel) && Objects.equals(lictlPgrecog, that.lictlPgrecog) && Objects.equals(lictlUgapproved, that.lictlUgapproved) && Objects.equals(selectedfor, that.selectedfor) && Objects.equals(selectedforlicId, that.selectedforlicId) &&  Objects.equals(unumDeanforpanel, that.unumDeanforpanel) && Objects.equals(unumEntryUid, that.unumEntryUid) && Objects.equals(unumMobileNo, that.unumMobileNo) && Objects.equals(unumTAppointtypeId, that.unumTAppointtypeId) && Objects.equals(unumTPgrecog, that.unumTPgrecog) && Objects.equals(unumUgapproved, that.unumUgapproved) && Objects.equals(unumUnivId, that.unumUnivId) && Objects.equals(unumWillingnessToWorkOnLic, that.unumWillingnessToWorkOnLic) && Objects.equals(ustrDescription, that.ustrDescription) && Objects.equals(ustrTAppointtype, that.ustrTAppointtype) && Objects.equals(ustrTEmailid, that.ustrTEmailid) && Objects.equals(ustrTPanNo, that.ustrTPanNo) && Objects.equals(ustrEmpName, that.ustrEmpName) && Objects.equals(unumIsexternal, that.unumIsexternal) && Objects.equals(willingnessToWorkOnLic, that.willingnessToWorkOnLic) && Objects.equals(unumDeptId, that.unumDeptId) && Objects.equals(ustrEmpId, that.ustrEmpId) && Objects.equals(unumIsTeacher, that.unumIsTeacher) && Objects.equals(unumIsSelectedfor, that.unumIsSelectedfor) && Objects.equals(unumTotLicChairmanCount, that.unumTotLicChairmanCount) && Objects.equals(unumTotLicMember1Count, that.unumTotLicMember1Count) && Objects.equals(unumTotLicMember2Count, that.unumTotLicMember2Count) && Objects.equals(unumAyLicChairmanCount, that.unumAyLicChairmanCount) && Objects.equals(unumAyLicMember1Count, that.unumAyLicMember1Count) && Objects.equals(unumAyLicMember2Count, that.unumAyLicMember2Count) && Objects.equals(unumIsSelected, that.unumIsSelected) && Objects.equals(employeesToFlag, that.employeesToFlag) && Objects.equals(ustrTAadharNo, that.ustrTAadharNo) && Objects.equals(unumEmpDesigid, that.unumEmpDesigid) && Objects.equals(udtUgJoiningDate, that.udtUgJoiningDate) && Objects.equals(udtPgJoiningDate, that.udtPgJoiningDate) && Objects.equals(unumCollegeId, that.unumCollegeId) && Objects.equals(employeeProfileList, that.employeeProfileList) && Objects.equals(employeeCurrentDetailBeanList, that.employeeCurrentDetailBeanList) && Objects.equals(employeeProfileBean, that.employeeProfileBean) && Objects.equals(employeeCurrentDetailBean, that.employeeCurrentDetailBean) && Objects.equals(ustrEmpLName, that.ustrEmpLName) && Objects.equals(isDisabled, that.isDisabled);
    }



    private Boolean isDisabled;
}
