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
public class FacultyBean extends GlobalBean {
    @ComboKey
    private Integer unumCfacultyId;
    private Integer unumIsvalid;
    private Date udtEffFrom;
    private Date udtEffTo;
    private Date udtEntryDate;
    private String unumExonCfacultyid;
    private String unumIntraCfacultyid;
    private Integer unumUnivId;

    @ComboValue
    private String ustrCfacultyFname;
    private String ustrCfacultySname;
    private String ustrDescription;
}
