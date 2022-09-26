package in.cdac.university.globalService.bean;

import in.cdac.university.globalService.util.annotations.ComboKey;
import in.cdac.university.globalService.util.annotations.ComboValue;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CourseTypeBean {

    @ComboKey
    private Long unumCtypeId;
    private Date udtEffFrom;
    private Date udtEffTo;
    private Date udtEntryDate;
    private Integer unumCtypeMinEligibilityCtypeId;
    private Integer unumCtypeSequence;
    private Long unumEntryUid;
    private Integer unumExonClevelid;
    private Integer unumIntraCtypeid;
    private Integer unumIsvalid;
    private Integer unumUnivId;
    private String ustrCtypeCode;
    private String ustrCtypeDescription;

    @ComboValue
    private String ustrCtypeFname;
    private String ustrCtypeMinEligibility;
    private String ustrCtypeSname;
    private String ustrDescription;
}
