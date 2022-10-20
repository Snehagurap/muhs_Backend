package in.cdac.university.globalService.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="gmst_config_template_item_mst", schema = "university")
@Getter
@Setter
@IdClass(GmstConfigTemplateItemMstPK.class)
public class GmstConfigTemplateItemMst implements Serializable {

	@Id
	private Long unumTemplItemId;

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

	@Column(name="unum_audiology_and_speech_flag")
	private Integer unumAudiologyAndSpeechFlag;

	@Column(name="unum_ayurved_flag")
	private Integer unumAyurvedFlag;

	@Column(name="unum_dental_flag")
	private Integer unumDentalFlag;

	@Column(name="unum_entry_uid")
	private Long unumEntryUid;

	@Column(name="unum_homeopathy_flag")
	private Integer unumHomeopathyFlag;

	@Column(name="unum_is_hidden")
	private Integer unumIsHidden;

	@Column(name="unum_is_merge_with_parent")
	private Integer unumIsMergeWithParent;

	@Column(name="unum_item_display_order")
	private Integer unumItemDisplayOrder;

	@Column(name="unum_item_ismandy")
	private Integer unumItemIsmandy;

	@Column(name="unum_item_type")
	private Integer unumItemType;

	@Column(name="unum_linkval_templ_comp_id")
	private Long unumLinkvalTemplCompId;

	@Column(name="unum_linkval_templ_head_id")
	private Long unumLinkvalTemplHeadId;

	@Column(name="unum_linkval_templ_id")
	private Long unumLinkvalTemplId;

	@Column(name="unum_linkval_templ_item_id")
	private Long unumLinkvalTemplItemId;

	@Column(name="unum_linkval_templ_subhead_id")
	private Long unumLinkvalTemplSubheadId;

	@Column(name="unum_medical_flag")
	private Integer unumMedicalFlag;

	@Column(name="unum_n_and_ys_flag")
	private Integer unumNAndYsFlag;

	@Column(name="unum_nursing_flag")
	private Integer unumNursingFlag;

	@Column(name="unum_occupational_therapy_flag")
	private Integer unumOccupationalTherapyFlag;

	@Column(name="unum_option_value_type")
	private Integer unumOptionValueType;

	@Column(name="unum_optometry_flag")
	private Integer unumOptometryFlag;

	@Column(name="unum_p_and_o_flag")
	private Integer unumPAndOFlag;

	@Column(name="unum_parent_value_check_flag")
	private String unumParentValueCheckFlag;

	@Column(name="unum_physiotherapy_flag")
	private Integer unumPhysiotherapyFlag;

	@Column(name="unum_printitem_flag")
	private Integer unumPrintitemFlag;

	@Column(name="unum_templ_parent_item_id")
	private Long unumTemplParentItemId;

	@Column(name="unum_ui_control_id")
	private Integer unumUiControlId;

	@Column(name="unum_unani_flag")
	private Integer unumUnaniFlag;

	@Column(name="unum_univ_id")
	private Integer unumUnivId;

	@Column(name="ustr_description")
	private String ustrDescription;

	@Column(name="ustr_item_allignment")
	private String ustrItemAllignment;

	@Column(name="ustr_item_api_url")
	private String ustrItemApiUrl;

	@Column(name="ustr_item_datatype")
	private String ustrItemDatatype;

	@Column(name="ustr_item_maxval")
	private String ustrItemMaxval;

	@Column(name="ustr_item_minval")
	private String ustrItemMinval;

	@Column(name="ustr_item_print_post_text")
	private String ustrItemPrintPostText;

	@Column(name="ustr_item_print_pre_text")
	private String ustrItemPrintPreText;

	@Column(name="ustr_item_print_prefix_text")
	private String ustrItemPrintPrefixText;

	@Column(name="ustr_option_text")
	private String ustrOptionText;

	@Column(name="ustr_option_value_procname")
	private String ustrOptionValueProcname;

	@Column(name="ustr_option_value_query")
	private String ustrOptionValueQuery;

	@Column(name="ustr_table_row_col_id")
	private String ustrTableRowColId;

	@Column(name="ustr_templ_item_code")
	private String ustrTemplItemCode;

	@Column(name="ustr_value_text")
	private String ustrValueText;

	@Formula("(select c.ustr_uict_desc from university.gmst_config_uicontrol_type_mst c where c.unum_uict_id = unum_ui_control_id and c.unum_isvalid = 1)")
	private String controlTypeName;
}