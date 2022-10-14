package in.cdac.university.globalService.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class TemplateSubHeaderBean {
    private Long unumTemplSubheadId;

    private Integer unumIsvalid;

    private Date udtEffFrom;

    private Date udtEffTo;

    private Date udtEntryDate;

    private Long unumEntryUid;

    private Integer unumIsMergeWithParent;

    private Integer unumSubheadDisplayOrder;

    private Integer unumSubheadIsmandy;

    private Long unumTemplParentSubheadId;

    private Integer unumUnivId;

    private String ustrDescription;

    private String ustrSubheadAllignment;

    private String ustrSubheadPrintPrefixText;

    private String ustrSubheadPrintText;

    private String ustrTemplSubheadCode;
}
