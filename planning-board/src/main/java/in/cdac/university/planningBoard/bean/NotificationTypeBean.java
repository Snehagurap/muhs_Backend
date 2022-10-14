package in.cdac.university.planningBoard.bean;

import lombok.Data;

import java.util.Date;

@Data
public class NotificationTypeBean {

    private Integer unumNtypeId;
    private Integer unumIsvalid;
    private Date udtEffFrm;
    private Date udtEffTo;
    private Date udtEntryDate;
    private Integer unumUnivId;
    private String ustrDescription;
    private String ustrNtypeFname;
    private String ustrNtypeSname;
}
