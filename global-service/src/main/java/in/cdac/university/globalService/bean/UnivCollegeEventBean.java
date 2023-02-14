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
public class UnivCollegeEventBean {
    @ComboKey
    private Integer unumColEventId;
    private String ustrColEventSname;
    @ComboValue
    private String ustrColEventFname;
    private String usteDescription;
    private Integer unumUnivId;
    private Date udtEntryDate;
    private Integer unumIsvalid;
    private Long unumEntryId;
    private Long unumLstModUid;
    private Date udtLstModDate;
}
