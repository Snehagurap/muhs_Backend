package in.cdac.university.planningBoard.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class FacultyBean extends GlobalBean {
    private Integer unumCfacultyId;
    private Integer unumIsvalid;
    private Date udtEffFrom;
    private Date udtEffTo;
    private Date udtEntryDate;
    private String unumExonCfacultyid;
    private String unumIntraCfacultyid;
    private Integer unumUnivId;
    private String ustrCfacultyFname;
    private String ustrCfacultySname;
    private String ustrDescription;
}
