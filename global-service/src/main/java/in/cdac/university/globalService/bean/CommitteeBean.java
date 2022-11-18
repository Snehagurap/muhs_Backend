package in.cdac.university.globalService.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
public class CommitteeBean {

    private Long unumComid;
    private Integer unumIsvalid;
    private Integer unumComtypeId;
    private Integer unumComdurationDays;
    private Date udtComStartDate;
    private String ustrComName;
    private Integer unumNoOfMembers;
    private Integer unumComCfacultyId;
    private String ustrComForyear;
    private Date udtComEndDate;
    private Date udtEntryDate;
    private Integer unumUnivId;
    private String ustrComDescription;
    private Integer ustrComFormonth;
    private Long unumEntryUid;
    private String committeeTypeName;
    private String facultyName;
    private List<CommitteeDetailBean> committeeDetail;
}
