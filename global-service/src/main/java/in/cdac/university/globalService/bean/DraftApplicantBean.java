package in.cdac.university.globalService.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@ToString
public class DraftApplicantBean {

    private Long unumApplicantDraftid;

    private Integer unumIsvalid;

    private Date udtEffFrom;

    private Date udtEffTo;

    private Date udtEntryDate;

    @NotNull(message = "District is mandatory")
    private Integer unumApplicantDistrictid;

    @NotNull(message = "Applicant Mobile is mandatory")
    @Range(min = 1111111111L, max = 9999999999L, message = "Mobile No. must be of 10 digits.")
    private Long unumApplicantMobile;

    @NotNull(message = "Pincode is mandatory")
    @Range(min = 111111, max = 999999, message = "Pincode must be of 6 digits.")
    private Long unumApplicantPincode;

    private Integer unumApplicantRegStatus;

    @NotNull(message = "State is mandatory")
    private Integer unumApplicantStateid;

    private Long unumEntryUid;

    @NotBlank(message = "Applicant Full Address is mandatory")
    private String ustrApplicantAddress;

    @NotBlank(message = "City is mandatory")
    private String ustrApplicantCity;

    @NotBlank(message = "Applicant email is mandatory")
    @Email(message = "Invalid email address")
    private String ustrApplicantEmail;

    @NotBlank(message = "Applicant Name is mandatory")
    private String ustrApplicantName;

    private String ustrDescription;

    private String ustrGeneratedEmailotp;

    private String ustrGeneratedMotp;

    @JsonIgnore
    private String ustrTempPass;

    private String ustrTempUid;

    private Integer ignoreDuplicate = 0;

}
