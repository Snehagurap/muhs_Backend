package in.cdac.university.committee.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@IdClass(GbltScrutinycommitteeMstPK.class)
@Entity
@Table(name="gblt_scrutinycommittee_mst", schema = "ucom")
public class GbltScrutinycommitteeMst implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;

	@Id
	private Long unumScomId;

	@Id
	private Integer unumIsvalid;

	@Column(name="udt_entry_date")
	private Date udtEntryDate;

	@Column(name="udt_revalidation_date")
	private Date udtRevalidationDate;

	@Temporal(TemporalType.DATE)
	@Column(name="udt_scom_create_date")
	private Date udtScomCreateDate;

	@Temporal(TemporalType.DATE)
	@Column(name="udt_scom_from_date")
	private Date udtScomFromDate;

	@Temporal(TemporalType.DATE)
	@Column(name="udt_scom_to_date")
	private Date udtScomToDate;

	@Column(name="unum_com_rs_id")
	private Long unumComRsId;

	@Column(name="unum_ctypeid")
	private Integer unumCtypeid;

	@Column(name="unum_entry_uid")
	private Long unumEntryUid;

	@Column(name="unum_is_formula_based")
	private Integer unumIsFormulaBased;

	@Column(name="unum_login_required")
	private Integer unumLoginRequired;

	@Column(name="unum_no_of_members")
	private Integer unumNoOfMembers;

	@Column(name="unum_scom_cfaculty_id")
	private Long unumScomCfacultyId;

	@Column(name="unum_stream_id")
	private Integer unumStreamId;

	@Column(name="unum_univ_id")
	private Integer unumUnivId;

	@Column(name="ustr_com_description")
	private String ustrComDescription;

	@Column(name="ustr_scom_name")
	private String ustrScomName;

	@Column(name="unum_sub_id")
	private Long unumSubId;

}