package in.cdac.university.globalService.bean;

import in.cdac.university.globalService.util.annotations.ComboKey;
import in.cdac.university.globalService.util.annotations.ComboValue;
import in.cdac.university.globalService.util.annotations.ListColumn;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.*;
import java.util.Date;

@Getter
@Setter
@ToString
public class CollegeBean {

    @ListColumn(omit = true)
    @ComboKey
    private Long unumCollegeId;

    @NotNull(message = "Status is mandatory")
    private Integer unumIsvalid;

    @NotNull(message = "Effective From is mandatory")
    private Date udtEffFrom;

    private Date udtEffTo;

    private Date udtEntryDate;

    @NotNull(message = "Biometric Status is mandatory")
    private Integer unumBiometricStatus;

    @NotNull(message = "CCTV Status is mandatory")
    private Integer unumCctvStatus;

    @Pattern(regexp = "[0-9,/-]+", message = "Contact No. is invalid")
    @Length(max = 100, message = "Contact No. can contain maximum of 100 digits")
    @NotBlank(message = "Contact No. is mandatory")
    private String unumContactnumber1;

    private Long unumContactnumber2;
    private Long unumContactnumber3;
    private Long unumContactnumber4;
    private Long unumContactnumber5;

    @NotNull(message = "District is mandatory")
    private Integer unumDistrictId;

    private Long unumEntryUid;

    @NotNull(message = "Lab availability status is mandatory")
    private Integer unumLabAvailabilityStatus;

    @NotNull(message = "NKN availability status is mandatory")
    private Integer unumNknconnectivityStatus;

    @NotNull(message = "Pincode is mandatory")
    @Length(min = 6, max = 6, message = "Pincode must be of 6 digits")
    private String unumPincode;

    private Integer unumPresentAffStatusId;
    private Integer unumRegionid;

    @NotNull(message = "State is mandatory")
    private Integer unumStateId;

    @Range(min = 0, max = 999999, message = "No of Adhoc Teachers should be less than 999999")
    private Integer unumTotNoofAddhocteachers;

    @Range(min = 0, max = 999999, message = "No of Labs should be less than 999999")
    private Integer unumTotNoofLabs;

    @Range(min = 0, max = 999999, message = "No of Practical Capacity should be less than 999999")
    private Integer unumTotNoofPracticalCapacity;

    @Range(min = 0, max = 999999, message = "No of Present courses should be less than 999999")
    private Integer unumTotNoofPresentcourses;

    @Range(min = 0, max = 999999, message = "No of Present Faculties should be less than 999999")
    private Integer unumTotNoofPresentfaculties;

    @Range(min = 0, max = 999999, message = "No of Regular Teachers should be less than 999999")
    private Integer unumTotNoofRegularteachers;

    @Range(min = 0, max = 999999, message = "No of Theory Capacity should be less than 999999")
    private Integer unumTotNoofTheoryCapacity;

    private Integer unumUnivId;
    private String ustrAddress2;
    private String ustrAddress2Marathi;
    private String ustrAddress3;
    private String ustrAddress3Marathi;
    private String ustrAntiRaggingcellContactno;
    private String ustrAntiRaggingcellEmailid;

    @ListColumn(order = 3, name = "College Code")
    private String ustrColCode;

    @NotNull(message = "College Full Name is mandatory")
    @ListColumn(order = 2, name = "College Name")
    @ComboValue
    private String ustrColFname;
    private String ustrColFnameMarathi;
    private String ustrColSname;
    private String ustrColSnameMarathi;
    private String ustrDescription;

    @NotNull(message = "Email id is mandatory")
    @Email(message = "Email id 1 is not valid")
    private String ustrEmail1;

    @Email(message = "Email id 2 is not valid")
    private String ustrEmail2;

    @Email(message = "Email id 3 is not valid")
    private String ustrEmail3;

    @Email(message = "Email id 4 is not valid")
    private String ustrEmail4;

    @Email(message = "Email id 5 is not valid")
    private String ustrEmail5;
    private String ustrFax1;
    private String ustrFax2;
    private String ustrHostelContactno;
    private String ustrHostelEmailid;
    private String ustrIntranetBody;
    private String ustrIntranetFacultyid;
    private String ustrIntranetPin;
    private String ustrIntranetState;
    private String ustrLogo1;
    private String ustrLogo2;
    private String ustrLogo3;
    private String ustrNaacAccrediationno;
    private String ustrScstobccellContactno;
    private String ustrScstobccellEmailid;
    private String ustrSexHarrCellContactno;
    private String ustrSexHarrCellEmailid;
    private String ustrStaticIpaddress;

    @NotNull(message = "Street Address is Mandatory")
    private String ustrStreetAddress;
    private String ustrStreetAddressMarathi;
    private String ustrWebsite1;
    private String ustrWebsite2;
    private String ustrWebsite3;

    @ListColumn(order = 4, name = "District Name")
    private String districtName;

    @ListColumn(order = 5, name = "Region Name")
    private String regionName;
}
