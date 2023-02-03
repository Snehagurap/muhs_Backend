package in.cdac.university.globalService.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@IdClass(GmstConfigTemplateHeaderMstPK.class)
@Entity
@Table(name="gmst_config_template_header_mst", schema = "university")
public class GmstConfigTemplateHeaderMst implements Serializable {

	@Id
	private Long unumTempleHeadId;

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

	@Column(name="unum_head_display_order")
	private Integer unumHeadDisplayOrder;

	@Column(name="unum_head_ismandy")
	private Integer unumHeadIsmandy;

	@Column(name="unum_is_merge_with_parent")
	private Integer unumIsMergeWithParent;

	@Column(name="unum_temple_parent_head_id")
	private Long unumTempleParentHeadId;

	@Column(name="unum_univ_id")
	private Integer unumUnivId;

	@Column(name="ustr_description")
	private String ustrDescription;

	@Column(name="ustr_head_allignment")
	private String ustrHeadAllignment;

	@Column(name="ustr_head_print_prefix_text")
	private String ustrHeadPrintPrefixText;

	@Column(name="ustr_head_print_text")
	private String ustrHeadPrintText;

	@Column(name="ustr_temple_head_code")
	private String ustrTempleHeadCode;

    @Column(name="unum_is_hidden")
    private Integer unumIsHidden;
}