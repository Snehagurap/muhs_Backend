package in.cdac.university.globalService.bean;

import in.cdac.university.globalService.util.annotations.ComboKey;
import in.cdac.university.globalService.util.annotations.ComboValue;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class AffiliationStatusBean {

    @ComboKey
    private Long unumAffStatusId;
    private Integer unumIsvalid;
    private Date udtEffFrom;
    private Date udtEffTo;
    private Date udtEntryDate;
    private Integer unumEntryUid;

    @ComboValue
    private String ustrAffFtatusFname;
    private String ustrAffStatusSname;
    private String ustrDescription;
}
