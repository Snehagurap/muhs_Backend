package in.cdac.university.globalService.bean;

import in.cdac.university.globalService.util.annotations.ComboKey;
import in.cdac.university.globalService.util.annotations.ComboValue;
import lombok.Data;

import java.util.Date;

@Data
public class NotificationTypeBean {

    @ComboKey
    private Integer unumNtypeId;

    private Integer unumIsvalid;
    private Date udtEffFrm;
    private Date udtEffTo;
    private Date udtEntryDate;
    private Integer unumUnivId;
    private String ustrDescription;

    @ComboValue
    private String ustrNtypeFname;
    private String ustrNtypeSname;
}
