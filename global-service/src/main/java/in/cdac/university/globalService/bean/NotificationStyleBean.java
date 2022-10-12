package in.cdac.university.globalService.bean;

import in.cdac.university.globalService.util.annotations.ComboKey;
import in.cdac.university.globalService.util.annotations.ComboValue;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class NotificationStyleBean {

    @ComboKey
    private Long unumNstyleId;

    private Integer unumIsvalid;
    private Date udtEffFrom;
    private Date udtEffTo;
    private Date udtEntryDate;
    private Long unumEntryUid;
    private String ustrDescription;

    @ComboValue
    private String ustrNstyleFname;
    private String ustrNstyleSname;
}
