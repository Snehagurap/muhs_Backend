package in.cdac.university.globalService.bean;

import in.cdac.university.globalService.util.annotations.ComboKey;
import in.cdac.university.globalService.util.annotations.ComboValue;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class CollegeBean {

    @ComboKey
    private Long unumCollegeId;

    private Integer unumIsvalid;
    private Date udtEffFrom;
    private Date udtEffTo;
    private Date udtEntryDate;
    private Integer unumBiometricStatus;
    private Integer unumCctvStatus;
    private String unumContactnumber1;
    private Long unumContactnumber2;
    private Long unumContactnumber3;
    private Long unumContactnumber4;
    private Long unumContactnumber5;
    private Integer unumDistrictId;
    private Long unumEntryUid;
    private Integer unumLabAvailabilityStatus;
    private Integer unumNknconnectivityStatus;
    private String unumPincode;
    private Integer unumPresentAffStatusId;
    private Integer unumRegionid;
    private Integer unumStateId;
    private Integer unumTotNoofAddhocteachers;
    private Integer unumTotNoofLabs;
    private Integer unumTotNoofPracticalCapacity;
    private Integer unumTotNoofPresentcourses;
    private Integer unumTotNoofPresentfaculties;
    private Integer unumTotNoofRegularteachers;
    private Integer unumTotNoofTheoryCapacity;
    private Integer unumUnivId;
    private String ustrAddress2;
    private String ustrAddress2Marathi;
    private String ustrAddress3;
    private String ustrAddress3Marathi;
    private String ustrAntiRaggingcellContactno;
    private String ustrAntiRaggingcellEmailid;
    private String ustrColCode;

    @ComboValue
    private String ustrColFname;
    private String ustrColFnameMarathi;
    private String ustrColSname;
    private String ustrColSnameMarathi;
    private String ustrDescription;
    private String ustrEmail1;
    private String ustrEmail2;
    private String ustrEmail3;
    private String ustrEmail4;
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
    private String ustrStreetAddress;
    private String ustrStreetAddressMarathi;
    private String ustrWebsite1;
    private String ustrWebsite2;
    private String ustrWebsite3;

}
