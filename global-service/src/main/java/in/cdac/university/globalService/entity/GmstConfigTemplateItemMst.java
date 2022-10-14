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

	@Column(name="unum_entry_uid")
	private Long unumEntryUid;

	@Column(name="unum_is_merge_with_parent")
	private Integer unumIsMergeWithParent;

	@Column(name="unum_item_display_order")
	private Integer unumItemDisplayOrder;

	@Column(name="unum_item_ismandy")
	private Integer unumItemIsmandy;

	@Column(name="unum_option_value_type")
	private Integer unumOptionValueType;

	@Column(name="unum_templ_parent_item_id")
	private Long unumTemplParentItemId;

	@Column(name="unum_ui_control_id")
	private Integer unumUiControlId;

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

	@Column(name="ustr_templ_item_code")
	private String ustrTemplItemCode;

	@Column(name="ustr_value_text")
	private String ustrValueText;

	@Formula("(select c.ustr_uict_desc from university.gmst_config_uicontrol_type_mst c where c.unum_uict_id = unum_ui_control_id and c.unum_isvalid = 1)")
	private String controlTypeName;
}