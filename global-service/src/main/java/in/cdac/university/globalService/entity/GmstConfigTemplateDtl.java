package in.cdac.university.globalService.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Entity
@IdClass(GmstConfigTemplateDtlPK.class)
@Table(name="gmst_config_template_dtl", schema = "university")
public class GmstConfigTemplateDtl implements Serializable {

	@Id
	private Long unumTemplId;

	@Id
	private Integer unumIsvalid;

	@Temporal(TemporalType.DATE)
	@Column(name="udt_eff_from")
	private Date udtEffFrom;

	@Temporal(TemporalType.DATE)
	@Column(name="udt_eff_to")
	private Date udtEffTo;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="udt_entry_date")
	private Date udtEntryDate;

	@Column(name="unum_entry_uid")
	private Long unumEntryUid;

	@Column(name="unum_faculty_id")
	private Integer unumFacultyId;

	@Column(name="unum_is_merge_with_parent")
	private Integer unumIsMergeWithParent;

	@Column(name="unum_templ_comp_id")
	private Long unumTemplCompId;

	@Column(name="unum_templ_display_order")
	private Integer unumTemplDisplayOrder;

	@Column(name="unum_templ_head_id")
	private Long unumTemplHeadId;

	@Column(name="unum_templ_ismandy")
	private Integer unumTemplIsmandy;

	@Column(name="unum_templ_item_id")
	private Long unumTemplItemId;

	@Column(name="unum_templ_parent_id")
	private Long unumTemplParentId;

	@Column(name="unum_templ_subhead_id")
	private Long unumTemplSubheadId;

	@Column(name="unum_template_for_yyyy")
	private Integer unumTemplateForYyyy;

	@Column(name="unum_template_for_yyyymm")
	private Integer unumTemplateForYyyymm;

	@Column(name="unum_template_type")
	private Integer unumTemplateType;

	@Column(name="unum_univ_id")
	private Integer unumUnivId;

	@Column(name="ustr_temp_description")
	private String ustrTempDescription;

	@Column(name="ustr_templ_allignment")
	private String ustrTemplAllignment;

	@Column(name="ustr_templ_code")
	private String ustrTemplCode;

	@Column(name="ustr_templ_print_prefix_text")
	private String ustrTemplPrintPrefixText;

	@Column(name="ustr_templ_print_text")
	private String ustrTemplPrintText;

}