package in.cdac.university.globalService.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name="gmst_affiliationstatus_mst", schema = "university")
@IdClass(GmstAffiliationstatusMstPK.class)
public class GmstAffiliationstatusMst implements Serializable {

	@Id
	private Long unumAffStatusId;

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
	private Integer unumEntryUid;

	@Column(name="ustr_aff_ftatus_fname")
	private String ustrAffFtatusFname;

	@Column(name="ustr_aff_status_sname")
	private String ustrAffStatusSname;

	@Column(name="ustr_description")
	private String ustrDescription;
}