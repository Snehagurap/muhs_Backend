package in.cdac.university.globalService.bean;

import in.cdac.university.globalService.util.annotations.ComboKey;
import in.cdac.university.globalService.util.annotations.ComboValue;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class FacultyStreamDtlBean {

    @ComboKey
    private Long unumFacStreamId;

    private Integer unumIsvalid;

    private Date udtEffFrom;

    private Date udtEffTo;

    private Date udtEntryDate;

    private Date udtLstModDate;

    private Integer unumCfacultyId;

    private Long unumEntryUid;

    private Long unumLstModUid;

    private Long unumStreamId;

    private Integer unumUnivId;

    private String ustrDescription;

    @ComboValue(order = 1)
    private String ustrFacStreamCode;

    @ComboValue(order = 2)
    private String ustrFacStreamFname;

    private String ustrFacStreamSname;
}
