package in.cdac.university.globalService.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class TemplateHeaderBean {
    private Long unumTemplHeadId;

    private Integer unumIsvalid;

    private Date udtEffFrom;

    private Date udtEffTo;

    private Date udtEntryDate;

    private Long unumEntryUid;

    private Integer unumHeadDisplayOrder;

    private Integer unumHeadIsmandy;

    private Integer unumIsMergeWithParent;

    private Long unumTemplParentHeadId;

    private Integer unumUnivId;

    private String ustrDescription;

    private String ustrHeadAllignment;

    private String ustrHeadPrintPrefixText;

    private String ustrHeadPrintText;

    private String ustrTemplHeadCode;
}
