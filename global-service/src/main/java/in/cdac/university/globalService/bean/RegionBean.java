package in.cdac.university.globalService.bean;

import in.cdac.university.globalService.util.annotations.ComboKey;
import in.cdac.university.globalService.util.annotations.ComboValue;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class RegionBean {

    @ComboKey
    private Long unumRegionId;
    private Long unumIsvalid;
    private Date udtEffFrm;
    private Date udtEffTo;
    private Date udtEntryDate;
    private Long unumEntryUid;
    private Integer unumUnivId;
    private String ustrDescription;

    @ComboValue
    private String ustrRegionFname;
    private String ustrRegionSname;
}
