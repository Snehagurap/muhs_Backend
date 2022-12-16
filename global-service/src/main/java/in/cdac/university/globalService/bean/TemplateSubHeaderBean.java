package in.cdac.university.globalService.bean;

import in.cdac.university.globalService.util.annotations.ComboKey;
import in.cdac.university.globalService.util.annotations.ComboValue;
import in.cdac.university.globalService.util.annotations.ListColumn;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@ToString
public class TemplateSubHeaderBean {
    @ListColumn(omit = true)
    @ComboKey
    private Long unumTempleSubheadId;

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

    @ListColumn(order = 2, name = "Prefix Text")
    @ComboValue
    private String ustrSubheadPrintPrefixText;

    @ListColumn(order = 3, name = "Text")
    @NotBlank(message = "Sub Header text is mandatory")
    private String ustrSubheadPrintText;

    private String ustrTempleSubheadCode;

    @NotNull(message = "Header Id is mandatory")
    private Long unumTempleHeadId;
}
