package in.cdac.university.globalService.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name="gmst_config_template_item_api_mst")
@IdClass(GmstConfigTemplateItemApiMstPK.class)
public class GmstConfigTemplateItemApiMst implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;

	@Id
	private long unumApiId;

	@Id
	private Integer unumIsvalid;

	@Temporal(TemporalType.DATE)
	@Column(name="udt_eff_from")
	private Date udtEffFrom;

	@Temporal(TemporalType.DATE)
	@Column(name="udt_eff_to")
	private Date udtEffTo;

	@Column(name="udt_entry_date")
	private Date udtEntryDate;

	@Column(name="unum_api_function_mode")
	private Integer unumApiFunctionMode;

	@Column(name="unum_api_type_id")
	private Integer unumApiTypeId;

	@Column(name="unum_entry_uid")
	private Long unumEntryUid;

	@Column(name="ustr_api_function_name")
	private String ustrApiFunctionName;

	@Column(name="ustr_api_name")
	private String ustrApiName;

	@Column(name="ustr_api_url")
	private String ustrApiUrl;

	@Column(name="ustr_api_url_params")
	private String ustrApiUrlParams;

	@Column(name="ustr_description")
	private String ustrDescription;
}