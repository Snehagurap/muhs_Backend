package in.cdac.university.globalService.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class TemplateItemBean {

    private Long unumTemplItemId;

    private Integer unumIsvalid;

    private Date udtEffFrom;

    private Date udtEffTo;

    private Date udtEntryDate;

    private Long unumEntryUid;

    private Integer unumIsMergeWithParent;

    private Integer unumItemDisplayOrder;

    private Integer unumItemIsmandy;

    private Integer unumOptionValueType;

    private Long unumTemplParentItemId;

    private Integer unumUiControlId;

    private Integer unumUnivId;

    private String ustrDescription;

    private String ustrItemAllignment;

    private String ustrItemApiUrl;

    private String ustrItemDatatype;

    private String ustrItemMaxval;

    private String ustrItemMinval;

    private String ustrItemPrintPostText;

    private String ustrItemPrintPreText;

    private String ustrItemPrintPrefixText;

    private String ustrOptionText;

    private String ustrOptionValueProcname;

    private String ustrOptionValueQuery;

    private String ustrTemplItemCode;

    private String ustrValueText;
}
