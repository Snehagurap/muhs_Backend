package in.cdac.university.committee.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name="gmst_committee_role_mst", schema = "ucom")
@IdClass(GmstCommitteeRoleMstPK.class)
public class GmstCommitteeRoleMst implements Serializable {

	@Id
	private Integer unumRoleId;

	@Id
	private Integer unumIsvalid;

	@Temporal(TemporalType.DATE)
	@Column(name="udt_eff_frm")
	private Date udtEffFrm;

	@Temporal(TemporalType.DATE)
	@Column(name="udt_eff_to")
	private Date udtEffTo;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="udt_entry_date")
	private Date udtEntryDate;

	@Column(name="unum_univ_id")
	private Integer unumUnivId;

	@Column(name="ustr_description")
	private String ustrDescription;

	@Column(name="ustr_role_fname")
	private String ustrRoleFname;

	@Column(name="ustr_role_sname")
	private String ustrRoleSname;

}