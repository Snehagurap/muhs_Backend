package in.cdac.university.committee.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name="gblt_committee_type_mst", schema = "ucom")
@IdClass(GbltCommitteeTypeMstPK.class)
public class GbltCommitteeTypeMst implements Serializable {

	@Id
	private Integer unumComtypeId;

	@Id
	private Integer unumIsvalid;

	@Temporal(TemporalType.DATE)
	@Column(name="udt_comtype_valid_frm")
	private Date udtComtypeValidFrm;

	@Temporal(TemporalType.DATE)
	@Column(name="udt_comtype_valid_to")
	private Date udtComtypeValidTo;

	@Temporal(TemporalType.DATE)
	@Column(name="udt_entry_date")
	private Date udtEntryDate;

	@Column(name="unum_univ_id")
	private Integer unumUnivId;

	@Column(name="ustr_com_description")
	private String ustrComDescription;

	@Column(name="ustr_comtype_fname")
	private String ustrComtypeFname;

	@Column(name="ustr_comtype_sname")
	private String ustrComtypeSname;
}