package in.cdac.university.globalService.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@IdClass(GmstConfigUicontrolTypeMstPK.class)
@Entity
@Table(name="gmst_config_uicontrol_type_mst", schema = "university")
public class GmstConfigUicontrolTypeMst implements Serializable {
	@Id
	private Long unumUictId;

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

	@Column(name="unum_univ_id")
	private Integer unumUnivId;

	@Column(name="ustr_description")
	private String ustrDescription;

	@Column(name="ustr_uict_code")
	private String ustrUictCode;

	@Column(name="ustr_uict_desc")
	private String ustrUictDesc;
}