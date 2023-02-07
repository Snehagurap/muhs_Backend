package in.cdac.university.globalService.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import in.cdac.university.globalService.util.annotations.ComboKey;
import in.cdac.university.globalService.util.annotations.ComboValue;
import in.cdac.university.globalService.util.annotations.ListColumn;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
public class TemplateHeaderBean {

    @ComboKey
    @ListColumn(omit = true)
    private Long unumTempleHeadId;

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

    private Double unumHeadDisplayOrder;

    @Range(min = 0, max = 1, message = "Is Mandatory can be 0 or 1")
    private Integer unumHeadIsmandy = 0;

    private Integer unumIsMergeWithParent;

    private Long unumTempleParentHeadId;

    @JsonIgnore
    private Integer unumUnivId;

    private String ustrDescription;

    private String ustrHeadAllignment;

    @ListColumn( order = 3, name = "Header Prefix Text")
    private String ustrHeadPrintPrefixText;

    @ComboValue
    @ListColumn( order = 4, name = "Header Text")
    @NotBlank(message="Header Text is mandatory")
    private String ustrHeadPrintText;

    @ListColumn( order = 2 , name = "Header Code")
    @NotBlank(message="Header Code is mandatory")
    private String ustrTempleHeadCode;

    @Range(min = 0, max = 1, message = "Is Hidden can be 0 or 1")
    private Integer unumIsHidden = 0;

    private Long unumTempledtlId;

    private Integer unumPageColumns;

    private String ustrHeadHtml;

    List<TemplateComponentBean> components;
}
