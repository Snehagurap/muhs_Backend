package in.cdac.university.globalService.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@ToString
public class ApplicantBean {

    private Long unumApplicantId;

    private Integer unumIsvalid;

    private Date udtEffFrom;

    private Date udtEffTo;

    private Date udtEntryDate;

    private Date udtRegistrationDate;

    private Integer unumApplicantDistrictid;

    @NotNull(message = "Draft Applicant Id is mandatory")
    private Long unumApplicantDraftid;

    private Long unumApplicantMobile;

    private Long unumApplicantPincode;

    private Integer unumApplicantRegStatus;

    private Integer unumApplicantStateid;

    @NotNull(message = "Applicant Type is mandatory")
    private Long unumApplicantTypeId;

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
}
