package in.cdac.university.globalService.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import in.cdac.university.globalService.util.annotations.ListColumn;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Date;


@Getter
@Setter
public class TemplateMasterDtlsBean {

	@ListColumn(omit = true)
	private Long unumTempledtlId;

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

	private Long unumTempleCompItemId;

	private Long unumTempleCompId;

	@NotNull(message = "Template Head Id is mandatory")
	private Long unumTempleHeadId;

	@JsonIgnore
	private Long unumTempleId;

	private Integer unumTempleIsmandy;

	
	private Long unumTempleItemId;

	
	private Long unumTempleSubheadId;

	@JsonIgnore
	private Integer unumUnivId;

	private String ustrTempledtlDescription;

	private Integer unumDisplayOrder;

	private Integer unumHideComponentTxt;

	private Integer unumHideHeaderTxt;

	private Integer unumHideItemTxt;

	private Integer unumPageColumns;
	
	private String ustrItemPrintPreText;
	
	private String ustrCompPrintText;
	
	private String ustrDescription;
	
	private String ustrHeadPrintText;
	
	private String ustrSubheadPrintText;
	
	private String ustrItemPrintPrefixText;
	private String controlTypeName;
	private String ustrItemPrintPostText;
	
    private Integer unumHeaderOrderNo;
    
    private Integer unumComponentOrderNo;

	private Long unumChecklistId;

	private String ustrChecklistName;

	private String ustrChecklistItemName;

	private Integer unumChecklistItemOrderno;
    
}
