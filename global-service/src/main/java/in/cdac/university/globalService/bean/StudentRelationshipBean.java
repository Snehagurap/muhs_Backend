package in.cdac.university.globalService.bean;

import in.cdac.university.globalService.util.annotations.ComboKey;
import in.cdac.university.globalService.util.annotations.ComboValue;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class StudentRelationshipBean {

    @ComboKey
    private Integer unumRelationshipId;

    private String ustrRelSname;

    @ComboValue
    private String ustrRelFname;

    private Integer unumIsvalid;

    private Date udtEffFrom;

    private Date udtEffTo;

    private Integer unumUnivId;

    private Date udtEntryDate;

    private Long unumEntryUid;

    private Long unumLstModUid;

    private Date udtLstModDt;
}
