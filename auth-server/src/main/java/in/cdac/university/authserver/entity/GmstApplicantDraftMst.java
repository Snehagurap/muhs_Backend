package in.cdac.university.authserver.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Entity
@ToString
@IdClass(GmstApplicantDraftMstPK.class)
@Table(name="gmst_applicant_draft_mst", schema = "university")
public class GmstApplicantDraftMst implements Serializable {

	@Id
	private Long unumApplicantDraftid;

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

	@Column(name="unum_applicant_districtid")
	private Integer unumApplicantDistrictid;

	@Column(name="unum_applicant_mobile")
	private Long unumApplicantMobile;

	@Column(name="unum_applicant_pincode")
	private Long unumApplicantPincode;

	@Column(name="unum_applicant_reg_status")
	private Integer unumApplicantRegStatus;

	@Column(name="unum_applicant_stateid")
	private Integer unumApplicantStateid;

	@Column(name="unum_entry_uid")
	private Long unumEntryUid;

	@Column(name="ustr_applicant_address")
	private String ustrApplicantAddress;

	@Column(name="ustr_applicant_city")
	private String ustrApplicantCity;

	@Column(name="ustr_applicant_email")
	private String ustrApplicantEmail;

	@Column(name="ustr_applicant_name")
	private String ustrApplicantName;

	@Column(name="ustr_description")
	private String ustrDescription;

	@Column(name="ustr_generated_emailotp")
	private String ustrGeneratedEmailotp;

	@Column(name="ustr_generated_motp")
	private String ustrGeneratedMotp;

	@Column(name="ustr_temp_pass")
	private String ustrTempPass;

	@Column(name="ustr_temp_uid")
	private String ustrTempUid;

	@Column(name="ustr_plain_pass")
	private String ustrPlainPass;

}