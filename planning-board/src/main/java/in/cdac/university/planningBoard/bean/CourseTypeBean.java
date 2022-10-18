package in.cdac.university.planningBoard.bean;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CourseTypeBean {

    private Integer unumCtypeId;
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
    private String ustrCtypeFname;
    private String ustrCtypeMinEligibility;
    private String ustrCtypeSname;
    private String ustrDescription;
}
