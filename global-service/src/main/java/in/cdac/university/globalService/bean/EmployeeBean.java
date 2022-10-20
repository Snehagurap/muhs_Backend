package in.cdac.university.globalService.bean;

import in.cdac.university.globalService.util.annotations.ComboKey;
import in.cdac.university.globalService.util.annotations.ComboValue;
import in.cdac.university.globalService.util.annotations.ListColumn;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.Date;

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
    private Long unumMobileNo;

    private Integer unumTAppointtypeId;

    private Integer unumTPgrecog;

    private Integer unumUgapproved;

    private Integer unumUnivId;

    private Integer unumWillingnessToWorkOnLic;

    private String ustrDescription;

    private String ustrTAadharNo;

    private String ustrTAppointtype;

    @ListColumn(order = 4, name = "E-mail")
    private String ustrTEmailid;

    private String ustrTPanNo;

    @ListColumn(order = 2, name = "Teacher Name")
    @ComboValue
    @NotBlank(message = "Teacher name is mandatory")
    private String ustrEmpName;

    private String willingnessToWorkOnLic;
}
