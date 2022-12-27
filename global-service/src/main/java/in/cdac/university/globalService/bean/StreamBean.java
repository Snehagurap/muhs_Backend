package in.cdac.university.globalService.bean;

import in.cdac.university.globalService.util.annotations.ComboKey;
import in.cdac.university.globalService.util.annotations.ComboValue;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class StreamBean {

    @ComboKey
    private Long unumStreamId;

    private Integer unumIsvalid;

    private Date udtEffFrom;

    private Date udtEffTo;

    private Date udtEntryDate;

    private Date udtLstModDate;

    private Long unumEntryUid;

    private Long unumLstModUid;

    private Integer unumUnivId;

    private String ustrDescription;

    private String ustrStreamCode;

    @ComboValue
    private String ustrStreamFname;

    private String ustrStreamSname;
}
