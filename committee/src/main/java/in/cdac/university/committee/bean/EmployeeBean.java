package in.cdac.university.committee.bean;

import in.cdac.university.committee.util.annotations.ComboKey;
import in.cdac.university.committee.util.annotations.ComboValue;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
public class EmployeeBean {

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
    private Long unumMobileNo;
    private Integer unumTAppointtypeId;
    private Integer unumTPgrecog;
    private Integer unumUgapproved;
    private Integer unumUnivId;
    private Integer unumWillingnessToWorkOnLic;
    private String ustrDescription;
    private String ustrTAadharNo;
    private String ustrTAppointtype;
    private String ustrTEmailid;
    private String ustrTPanNo;

    private Integer unumIsexternal;
    @ComboValue
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

    private Integer unumIsSelected;
    private List<Long> employeesToFlag;

    private Integer unumEmpDesigid;
    private Date udtUgJoiningDate;
    private Date udtPgJoiningDate;

    private Long unumCollegeId;
    private List<EmployeeProfileBean> employeeProfileList;
    private List<EmployeeCurrentDetailBean> employeeCurrentDetailBeanList;
}
