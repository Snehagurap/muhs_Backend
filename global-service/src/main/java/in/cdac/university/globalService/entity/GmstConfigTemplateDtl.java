package in.cdac.university.globalService.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@IdClass(GmstConfigTemplateDtlPK.class)
@Entity
@Table(name="gmst_config_template_dtl", schema = "university")
public class GmstConfigTemplateDtl implements Serializable {

	@Id
	private Long unumTempledtlId;

	@Id
	private Integer unumIsvalid;

	@Temporal(TemporalType.DATE)
	@Column(name="udt_eff_from")
	private Date udtEffFrom;

	@Temporal(TemporalType.DATE)
	@Column(name="udt_eff_to")
	private Date udtEffTo;

	@Column(name="udt_entry_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date udtEntryDate;

	@Column(name="unum_entry_uid")
	private Long unumEntryUid;

	@Column(name="unum_temple_comp_item_id")
	private Long unumTempleCompItemId;

	@Column(name="unum_temple_comp_id")
	private Long unumTempleCompId;

	@Column(name="unum_temple_head_id")
	private Long unumTempleHeadId;

	@Column(name="unum_temple_id")
	private Long unumTempleId;

	@Column(name="unum_temple_ismandy")
	private Integer unumTempleIsmandy;

	@Column(name="unum_temple_item_id")
	private Long unumTempleItemId;

	@Column(name="unum_temple_subhead_id")
	private Long unumTempleSubheadId;

	@Column(name="unum_univ_id")
	private Integer unumUnivId;

	@Column(name="ustr_templedtl_description")
	private String ustrTempledtlDescription;

	@Column(name="unum_display_order")
	private Integer unumDisplayOrder;

	@Column(name="unum_hide_component_txt")
	private Integer unumHideComponentTxt;

	@Column(name="unum_hide_header_txt")
	private Integer unumHideHeaderTxt;

	@Column(name="unum_hide_item_txt")
	private Integer unumHideItemTxt;

	@Column(name="unum_page_columns")
	private Integer unumPageColumns;
	
	@Column(name="unum_header_order_no")
    private Integer unumHeaderOrderNo;
    
	@Column(name="unum_component_order_no")
    private Integer unumComponentOrderNo;

	@Column(name="unum_checklist_id")
	private Integer unumChecklistId;

	@Column(name="ustr_checklist_name")
	private String ustrChecklistName;

	@Column(name="ustr_checklist_item_name")
	private String ustrChecklistItemName;

	@Column(name="unum_checklist_item_orderno")
	private Integer unumChecklistItemOrderno;

	@Override
	public String toString() {
		return "Template Detail Id = " + this.unumTempledtlId
				+ ", Template Id = " + this.unumTempleId
				+ ", Head Id = " + this.unumTempleHeadId
				+ ", Sub Head Id = " + this.unumTempleSubheadId
				+ ", Comp Item Id = " + this.unumTempleCompItemId
				+ ", Comp Id = " + this.unumTempleCompId
				+ ", Item Id = " + this.unumTempleItemId
				+ ", Entry UID = " + this.unumEntryUid
				+ ", Head Order No = " + this.unumHeaderOrderNo
				+ ", Component Order No = " + this.unumComponentOrderNo
				+ ", Checklist ID = " + this.unumChecklistId
				+ ", Checklist Order No = " + this.unumChecklistItemOrderno;
	}
}