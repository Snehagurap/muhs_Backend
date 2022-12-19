package in.cdac.university.globalService.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import in.cdac.university.globalService.util.annotations.ComboKey;
import in.cdac.university.globalService.util.annotations.ComboValue;
import in.cdac.university.globalService.util.annotations.ListColumn;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
public class TemplateItemBean {

    @ComboKey
    @ComboValue(endSeparator = ": ")
    @ListColumn(omit = true)
    private Long unumTempleItemId;

    @JsonIgnore
    private Integer unumIsvalid;

    @JsonIgnore
    private Date udtEffFrom;

    @JsonIgnore
    private Date udtEffTo;

    @JsonIgnore
    private Date udtEntryDate;

    private Integer unumAudiologyAndSpeechFlag;

    private Integer unumAyurvedFlag;

    private Integer unumDentalFlag;

    @JsonIgnore
    private Long unumEntryUid;

    private Integer unumHomeopathyFlag;

    private Integer unumIsHidden;

    private Integer unumIsMergeWithParent = 0;

    @NotNull(message = "Display order is mandatory")
    private Integer unumItemDisplayOrder;

    private Integer unumItemIsmandy = 0;

    private Integer unumItemType;

    private Long unumLinkvalTempleCompId;

    private Long unumLinkvalTempleHeadId;

    private Long unumLinkvalTempleId;

    private Long unumLinkvalTempleItemId;

    private Long unumLinkvalTempleSubheadId;

    private Integer unumMedicalFlag;

    private Integer unumNAndYsFlag;

    private Integer unumNursingFlag;

    private Integer unumOccupationalTherapyFlag;

    private Integer unumOptionValueType;

    private Integer unumOptometryFlag;

    private Integer unumPAndOFlag;

    private String unumParentValueCheckFlag;

    private Integer unumPhysiotherapyFlag;

    private Integer unumPrintitemFlag;

    private Long unumTempleParentItemId;

    @NotNull(message = "Item Control ID is mandatory")
    private Integer unumUiControlId;

    @JsonIgnore
    private Integer unumUnivId;

    private String ustrDescription;

    @NotBlank(message = "Alignment is mandatory")
    private String ustrItemAllignment;

    private String ustrItemApiUrl;

    @NotBlank(message = "Data Type is mandatory")
    private String ustrItemDatatype;

    private String ustrItemMaxval;

    private String ustrItemMinval;

    @ComboValue(order = 3)
    @ListColumn(order = 3, name = "Pre Text")
    private String ustrItemPrintPreText;

    @ListColumn(order = 4, name = "Post Text")
    private String ustrItemPrintPostText;

    @ComboValue(order = 2)
    @ListColumn(order = 2, name = "Prefix Text")
    private String ustrItemPrintPrefixText;


    private String ustrOptionText;

    private String ustrOptionValueProcname;

    private String ustrOptionValueQuery;

    private String ustrTableRowColId;

    @NotNull(message = "Item Code is mandatory")
    private String ustrTempleItemCode;

    private String ustrValueText;

    @ListColumn(order = 5, name = "Control Type")
    private String controlTypeName;

    private String ustrParentValChildlist;

    private String ustrHelpTxt;

    private Long unumTempledtlId;

    private Long unumTemplCompItemId;

    private String ustrItemValue;

    List<TemplateItemBean> children;
}
