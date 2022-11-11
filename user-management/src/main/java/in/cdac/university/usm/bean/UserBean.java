package in.cdac.university.usm.bean;

import in.cdac.university.usm.util.Constants;
import in.cdac.university.usm.util.annotations.ComboKey;
import in.cdac.university.usm.util.annotations.ComboValue;
import in.cdac.university.usm.util.annotations.ListColumn;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.util.Date;

@Getter
@Setter
@ToString
public class UserBean extends GlobalBean {

    @NotNull(message = "University is mandatory")
    private Integer unumUnivId;

    @ListColumn(omit = true)
    @ComboKey
    private Long gnumUserid;

    @ListColumn(omit = true)
    @NotNull(message = "Status is mandatory")
    private Integer gnumIsvalid;

    @DateTimeFormat(pattern = Constants.dateFormat)
    @NotNull(message = "Effective From Date is mandatory")
    @PastOrPresent(message = "Effective From date should be less than or equal to Current Date")
    private Date gdtEffectDate;

    private Date gdtEntrydate;

    @DateTimeFormat
    @ListColumn(order = 4, name = "Expiry Date")
    @FutureOrPresent(message = "Effective To date should be greater than or equal to Current Date")
    private Date gdtExpiryDate;

    @NotNull(message = "Dataset is mandatory")
    private Integer gnumDatasetId;

    private Integer gnumDefaultDataId;

    @Length(max = 500, message = "Default Data Value should have maximum of 500 characters")
    private String gnumDefaultDataValue;

    @NotNull(message = "Designation is mandatory")
    private Integer gnumDesignation;

    private Long gnumEntryBy;

    private Integer gnumIslock;

    @ListColumn(order = 5, name = "Lock Status")
    private String lockStatus;

    @NotBlank(message = "Mobile No. is mandatory")
    @Length(min = 10, max = 10, message = "Mobile No. should be of 10 digits")
    private String gnumMobileNumber;

    @NotNull(message = "Role is mandatory")
    private Integer gnumRoleId;

    @NotNull(message = "State Name is mandatory")
    private Integer gnumStatecode;

    @NotNull(message = "User Category is mandatory")
    private Integer gnumUserCatId;

    @NotNull(message = "User Type is mandatory")
    private Integer gnumUserTypeId;

    @ListColumn(order = 3, name = "User Type")
    private String userTypeName;

    private String gstrAadharNumber;

    @NotBlank(message = "Password is mandatory")
    @Pattern(regexp = "[a-f0-9]{64}", message = "Password must be hashed with SHA256", flags = {Pattern.Flag.CASE_INSENSITIVE})
    private String gstrPassword;

    private String gstrOldPassword;

    @NotBlank(message = "User/Employee Name is mandatory")
    @ListColumn(order = 2, name = "Full Name")
    @ComboValue
    private String gstrUserFullName;

    @NotBlank(message = "Login id is mandatory")
    @ListColumn(order = 1, name = "UserName")
    @Pattern(regexp = "\\w{3,}", message = "Login id can contain alphabets, numbers and underscore only")
    @ComboValue(startSeparator = " [", endSeparator = "]")
    private String gstrUserName;

    @NotNull(message = "District Name is mandatory")
    private Integer numDistId;

    @Email(message = "Please enter valid Email id")
    @NotBlank(message = "Email Id is mandatory")
    private String gstrEmailId;

    private String defaultDataSetName;
    private String defaultRoleName;

    private Date gdtChangepasswordDate;
    private Date gdtLstmodDate;
    private Long gnumLstmodBy;
    private Date gnumLastLoginDate;
}
