package in.cdac.university.globalService.bean;

import in.cdac.university.globalService.util.annotations.ComboKey;
import in.cdac.university.globalService.util.annotations.ComboValue;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Getter
@Setter
public class NotificationReceiptBean {

    @ComboKey
    private Long unumNrecId;
    private Integer unumIsvalid;
    private Date udtEffFrm;
    private Date udtEffTo;
    private Date udtEntryDate;
    private Long unumEntryUid;
    private Integer unumUnivId;
    private String ustrDescription;

    @ComboValue
    private String ustrNrecFname;
    private String ustrNrecSname;
}
