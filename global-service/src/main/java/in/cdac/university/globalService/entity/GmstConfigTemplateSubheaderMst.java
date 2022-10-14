package in.cdac.university.globalService.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@IdClass(GmstConfigTemplateSubheaderMstPK.class)
@Entity
@Table(name="gmst_config_template_subheader_mst", schema = "university")
public class GmstConfigTemplateSubheaderMst implements Serializable {

	@Id
	private Long unumTemplSubheadId;

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

	@Column(name="unum_is_merge_with_parent")
	private Integer unumIsMergeWithParent;

	@Column(name="unum_subhead_display_order")
	private Integer unumSubheadDisplayOrder;

	@Column(name="unum_subhead_ismandy")
	private Integer unumSubheadIsmandy;

	@Column(name="unum_templ_parent_subhead_id")
	private Long unumTemplParentSubheadId;

	@Column(name="unum_univ_id")
	private Integer unumUnivId;

	@Column(name="ustr_description")
	private String ustrDescription;

	@Column(name="ustr_subhead_allignment")
	private String ustrSubheadAllignment;

	@Column(name="ustr_subhead_print_prefix_text")
	private String ustrSubheadPrintPrefixText;

	@Column(name="ustr_subhead_print_text")
	private String ustrSubheadPrintText;

	@Column(name="ustr_templ_subhead_code")
	private String ustrTemplSubheadCode;
}