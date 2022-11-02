package in.cdac.university.globalService.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
public class TemplateHeaderBean {

    private Long unumTemplHeadId;

    @JsonIgnore
    private Integer unumIsvalid;

    @JsonIgnore
    private Date udtEffFrom;

    @JsonIgnore
    private Date udtEffTo;

    @JsonIgnore
    private Date udtEntryDate;

    @JsonIgnore
    private Long unumEntryUid;

    private Integer unumHeadDisplayOrder;

    private Integer unumHeadIsmandy;

    private Integer unumIsMergeWithParent;

    private Long unumTemplParentHeadId;

    @JsonIgnore
    private Integer unumUnivId;

    private String ustrDescription;

    private String ustrHeadAllignment;

    private String ustrHeadPrintPrefixText;

    private String ustrHeadPrintText;

    private String ustrTemplHeadCode;

    private Integer unumIsHidden;

    private Long unumTempledtlId;

    private Integer unumPageColumns;

    List<TemplateComponentBean> components;
}
