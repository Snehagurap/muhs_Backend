package in.cdac.university.globalService.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import in.cdac.university.globalService.util.annotations.ListColumn;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class TemplateComponentBean {
    @ListColumn(omit = true)
    private Long unumTemplCompId;

    @JsonIgnore
    private Integer unumIsvalid;

    @JsonIgnore
    private Date udtEffFrom;

    @JsonIgnore
    private Date udtEffTo;

    @JsonIgnore
    private Date udtEntryDate;

    private Integer unumCompDisplayOrder;

    private Integer unumCompIsmandy;

    private Integer unumComponentTypeId;

    @JsonIgnore
    private Long unumEntryUid;

    private Integer unumIsMergeWithParent;

    private Long unumTemplParentCompId;

    @JsonIgnore
    private Integer unumUnivId;

    private String ustrCompAllignment;

    @ListColumn(name = "Prefix Text")
    private String ustrCompPrintPrefixText;

    @ListColumn(name = "Print Text")
    private String ustrCompPrintText;

    private String ustrDescription;

    private String ustrTemplCompCode;

    private Integer unumIsHidden;

    private Long unumTempledtlId;

    private List<TemplateItemBean> items;
}
