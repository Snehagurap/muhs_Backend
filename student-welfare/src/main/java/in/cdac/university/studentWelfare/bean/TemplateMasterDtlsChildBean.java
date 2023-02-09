package in.cdac.university.studentWelfare.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import in.cdac.university.studentWelfare.util.annotations.ListColumn;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
public class TemplateMasterDtlsChildBean {

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


	@JsonIgnore
	private Long unumTempleId;

	private Integer unumTempleIsmandy;

	
	private Long unumTempleItemId;


	@JsonIgnore
	private Integer unumUnivId;

	private String ustrTempledtlDescription;

	private Integer unumDisplayOrder;

	private Integer unumHideComponentTxt;

	private Integer unumHideHeaderTxt;

	private Integer unumHideItemTxt;

	private Integer unumPageColumns;
	
	private String ustrItemPrintPreText;
	private String ustrDescription;
	private String ustrItemPrintPrefixText;
	private String controlTypeName;
	private String ustrItemPrintPostText;

	private String ustrChecklistItemName;
	private Integer unumChecklistItemOrderno;

	private Integer unumUiControlId;
}
