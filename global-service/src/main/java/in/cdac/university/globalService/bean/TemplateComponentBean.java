package in.cdac.university.globalService.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;

import in.cdac.university.globalService.util.annotations.ComboKey;
import in.cdac.university.globalService.util.annotations.ComboValue;
import in.cdac.university.globalService.util.annotations.ListColumn;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class TemplateComponentBean {
    @ListColumn(omit = true)
    @ComboKey
    @ComboValue(order = 1)
    private Long unumTempleCompId;

    @JsonIgnore
    private Integer unumIsvalid;

    @JsonIgnore
    private Date udtEffFrom;

    @JsonIgnore
    private Date udtEffTo;

    @JsonIgnore
    private Date udtEntryDate;

    private Double unumCompDisplayOrder;

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

    @NotBlank(message = "Print Text is mandatory")
    @ComboValue(order = 2,startSeparator = "-")
    @ListColumn(name = "Print Text")
    private String ustrCompPrintText;

    private String ustrDescription;

    private String ustrTemplCompCode;

    private Integer unumIsHidden;

    private Long unumTempledtlId;

    private String ustrCompHtml;

    private List<TemplateItemBean> items;
    
    private List<TemplateComponentDtlsBean> templateComponentDtlsBeanList;
}
