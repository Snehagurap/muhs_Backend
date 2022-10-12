package in.cdac.university.committee.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@ToString
@Entity
@Table(name="gblt_committee_mst", schema = "ucom")
@IdClass(GbltCommitteeMstPK.class)
public class GbltCommitteeMst implements Serializable {

	@Id
	private Long unumComid;

	@Id
	private Integer unumIsvalid;

	@Temporal(TemporalType.DATE)
	@Column(name="udt_com_end_date")
	private Date udtComEndDate;

	@Temporal(TemporalType.DATE)
	@Column(name="udt_com_start_date")
	private Date udtComStartDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="udt_entry_date", insertable = false)
	private Date udtEntryDate;

	@Column(name="unum_com_cfaculty_id")
	private Integer unumComCfacultyId;

	@Column(name="unum_comduration_days")
	private Integer unumComdurationDays;

	@Column(name="unum_comtype_id")
	private Integer unumComtypeId;

	@Column(name="unum_no_of_members")
	private Integer unumNoOfMembers;

	@Column(name="unum_univ_id")
	private Integer unumUnivId;

	@Column(name="ustr_com_description")
	private String ustrComDescription;

	@Column(name="ustr_com_formonth")
	private Integer ustrComFormonth;

	@Column(name="ustr_com_foryear")
	private Integer ustrComForyear;

	@Column(name="ustr_com_name")
	private String ustrComName;

	@Column(name = "unum_entry_uid")
	private Long unumEntryUid;

	@Formula("(select t.ustr_comtype_fname from ucom.gblt_committee_type_mst t where t.unum_comtype_id = unum_comtype_id )")
	private String committeeTypeName;

	@Formula("(select t.ustr_cfaculty_fname from university.gmst_coursefaculty_mst t where t.unum_cfaculty_id = unum_com_cfaculty_id)")
	private String facultyName;
}