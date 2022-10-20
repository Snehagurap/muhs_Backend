package in.cdac.university.globalService.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@IdClass(GmstApplicantMstPK.class)
@Entity
@Table(name="gmst_applicant_mst", schema = "university")
public class GmstApplicantMst implements Serializable {

	@Id
	private Long unumApplicantId;

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

	@Temporal(TemporalType.DATE)
	@Column(name="udt_registration_date")
	private Date udtRegistrationDate;

	@Column(name="unum_applicant_districtid")
	private Integer unumApplicantDistrictid;

	@Column(name="unum_applicant_draftid")
	private Long unumApplicantDraftid;

	@Column(name="unum_applicant_mobile")
	private Long unumApplicantMobile;

	@Column(name="unum_applicant_pincode")
	private Long unumApplicantPincode;

	@Column(name="unum_applicant_reg_status")
	private Integer unumApplicantRegStatus;

	@Column(name="unum_applicant_stateid")
	private Integer unumApplicantStateid;

	@Column(name="unum_applicant_type_id")
	private Long unumApplicantTypeId;

	@Column(name="unum_entry_uid")
	private Long unumEntryUid;

	@Column(name="unum_is_verified_applicant")
	private Integer unumIsVerifiedApplicant;

	@Column(name="unum_registered_districtid")
	private Integer unumRegisteredDistrictid;

	@Column(name="unum_registered_mobileno")
	private Long unumRegisteredMobileno;

	@Column(name="unum_registered_pincode")
	private Long unumRegisteredPincode;

	@Column(name="unum_registered_stateid")
	private Integer unumRegisteredStateid;

	@Column(name="ustr_addrof_registered_soceity")
	private String ustrAddrofRegisteredSoceity;

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

	@Column(name="ustr_nameof_registered_soceity")
	private String ustrNameofRegisteredSoceity;

	@Column(name="ustr_pass")
	private String ustrPass;

	@Column(name="ustr_president_sec_name")
	private String ustrPresidentSecName;

	@Column(name="ustr_registered_city")
	private String ustrRegisteredCity;

	@Column(name="ustr_registration_no")
	private String ustrRegistrationNo;

	@Column(name="ustr_temp_pass")
	private String ustrTempPass;

	@Column(name="ustr_temp_uid")
	private String ustrTempUid;

	@Column(name="ustr_uid")
	private String ustrUid;

}