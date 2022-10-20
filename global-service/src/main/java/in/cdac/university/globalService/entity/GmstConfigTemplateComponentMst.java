package in.cdac.university.globalService.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@IdClass(GmstConfigTemplateComponentMstPK.class)
@Entity
@Table(name="gmst_config_template_component_mst", schema = "university")
public class GmstConfigTemplateComponentMst implements Serializable {

	@Id
	private Long unumTemplCompId;

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

	@Column(name="unum_comp_display_order")
	private Integer unumCompDisplayOrder;

	@Column(name="unum_comp_ismandy")
	private Integer unumCompIsmandy;

	@Column(name="unum_component_type_id")
	private Integer unumComponentTypeId;

	@Column(name="unum_entry_uid")
	private Long unumEntryUid;

	@Column(name="unum_is_merge_with_parent")
	private Integer unumIsMergeWithParent;

	@Column(name="unum_templ_parent_comp_id")
	private Long unumTemplParentCompId;

	@Column(name="unum_univ_id")
	private Integer unumUnivId;

	@Column(name="ustr_comp_allignment")
	private String ustrCompAllignment;

	@Column(name="ustr_comp_print_prefix_text")
	private String ustrCompPrintPrefixText;

	@Column(name="ustr_comp_print_text")
	private String ustrCompPrintText;

	@Column(name="ustr_description")
	private String ustrDescription;

	@Column(name="ustr_templ_comp_code")
	private String ustrTemplCompCode;

	@Column(name="unum_is_hidden")
	private Integer unumIsHidden;
}