package in.cdac.university.planningBoard.bean;


import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
public class StreamBean {

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
    
    private String ustrStreamFname;

    private String ustrStreamSname;

}
