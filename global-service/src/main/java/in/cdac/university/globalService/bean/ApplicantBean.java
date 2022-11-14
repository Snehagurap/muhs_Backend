package in.cdac.university.globalService.bean;

import com.fasterxml.jackson.annotation.JsonProperty;
import in.cdac.university.globalService.util.annotations.ListColumn;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
public class ApplicantBean {

    @ListColumn(omit = true)
    private Long unumApplicantId;

    private Integer unumIsvalid;

    private Date udtEffFrom;

    private Date udtEffTo;

    private Date udtEntryDate;

    private Date udtRegistrationDate;

    private Integer unumApplicantDistrictid;

    @ListColumn(order = 2, name = "Draft Id")
    @NotNull(message = "Draft Applicant Id is mandatory")
    private Long unumApplicantDraftid;

    private Long unumApplicantMobile;

    private Long unumApplicantPincode;

    private Integer unumApplicantRegStatus;

    private Integer unumApplicantStateid;


    @NotNull(message = "Applicant Type is mandatory")
    private Long unumApplicantTypeId;

    @ListColumn(order = 3, name = "Type")
    private String ustrApplicantTypeName;

    private Long unumEntryUid;

    private Integer unumIsVerifiedApplicant;

    private Integer unumRegisteredDistrictid;

    @NotNull(message = "Mobile Number/Contact Number is mandatory")
    private Long unumRegisteredMobileno;

    private Long unumRegisteredPincode;

    @NotNull(message = "State of Registered Society/Trust/Company is mandatory")
    private Integer unumRegisteredStateid;

    @NotBlank(message = "Full Address of Registered Society/Trust/Company is mandatory")
    private String ustrAddrofRegisteredSoceity;

    private String ustrApplicantAddress;

    private String ustrApplicantCity;

    private String ustrApplicantEmail;

    @ListColumn(order = 4, name = "Name")
    private String ustrApplicantName;

    private String ustrDescription;

    private String ustrGeneratedEmailotp;

    private String ustrGeneratedMotp;

    @NotBlank(message = "Name of Registered Society/Trust/Company is mandatory")
    private String ustrNameofRegisteredSoceity;

    private String ustrPass;

    @NotBlank(message = "Name of President/Secretary is mandatory")
    private String ustrPresidentSecName;

    @NotBlank(message = "City of Registered Society/Trust/Company")
    private String ustrRegisteredCity;

    private String ustrRegistrationNo;

    private String ustrTempPass;

    private String ustrTempUid;

    private String ustrUid;

    @Valid
    @JsonProperty("docDetails")
    private List<ApplicantDetailBean> applicantDetailBeans;
}
