package in.cdac.university.usm.bean;

import in.cdac.university.usm.util.annotations.ComboKey;
import in.cdac.university.usm.util.annotations.ComboValue;
import in.cdac.university.usm.util.annotations.ListColumn;
import lombok.Data;

import java.util.Date;

@Data
public class UniversityBean {

    @ListColumn(omit = true)
    @ComboKey
    private Integer unumUnivId;

    private Date udtEffFrom;
    private Date udtEffTo;
    private Date udtEntryDate;
    private Long unumContactnumber1;
    private Long unumContactnumber2;
    private Long unumContactnumber3;
    private Long unumContactnumber4;
    private Long unumContactnumber5;
    private Integer unumDistrictId;
    private Integer unumIsvalid;
    private Integer unumPincode;
    private Integer unumStateId;
    private String ustrAddress2;
    private String ustrAddress2Marathi;
    private String ustrAddress3;
    private String ustrAddress3Marathi;
    private String ustrDescription;
    private String ustrEmail1;
    private String ustrEmail2;
    private String ustrEmail3;
    private String ustrEmail4;
    private String ustrEmail5;
    private String ustrFax1;
    private String ustrFax2;
    private String ustrLogo1;
    private String ustrLogo2;
    private String ustrLogo3;
    private String ustrStreetAddress;
    private String ustrStreetAddressMarathi;
    private String ustrUnivCode;

    @ListColumn(order = 1, name = "University name")
    @ComboValue
    private String ustrUnivFname;

    private String ustrUnivFnameMarathi;

    @ListColumn(order = 2, name = "University Short name")
    private String ustrUnivSname;

    private String ustrUnivSnameMarathi;
    private String ustrWebsite1;
    private String ustrWebsite2;
    private String ustrWebsite3;

    @ListColumn(order = 3, name = "Username")
    private String gstrUserName;
}
