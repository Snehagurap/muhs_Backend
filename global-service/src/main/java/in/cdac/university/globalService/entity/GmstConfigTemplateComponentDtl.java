package in.cdac.university.globalService.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@IdClass(GmstConfigTemplateComponentDtlPK.class)
@Entity
@Table(name="gmst_config_template_component_dtl", schema = "university")
public class GmstConfigTemplateComponentDtl implements Serializable {

	@Id
	private Long unumTemplCompItemId;

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

	@Column(name="unum_templ_comp_id")
	private Long unumTemplCompId;

	@Column(name="unum_templ_item_id")
	private Long unumTemplItemId;

	@Column(name="unum_univ_id")
	private Integer unumUnivId;

	@Column(name="ustr_description")
	private String ustrDescription;

}