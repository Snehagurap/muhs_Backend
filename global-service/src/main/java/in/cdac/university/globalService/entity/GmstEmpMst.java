package in.cdac.university.globalService.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name="gmst_emp_mst", schema = "university")
@IdClass(GmstEmpMstPK.class)
public class GmstEmpMst implements Serializable {

	@Id
	private Long unumEmpId;

	@Id
	private Integer unumIsvalid;

	@Column(name = "deanforpanel")
	private String deanforpanel;

	@Column(name="lictl_pgrecog")
	private String lictlPgrecog;

	@Column(name="lictl_ugapproved")
	private String lictlUgapproved;

	@Column(name = "selectedfor")
	private String selectedfor;

	@Column(name="selectedforlic_id")
	private Integer selectedforlicId;

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
	@Column(name="udt_t_dob")
	private Date udtTDob;

	@Column(name="unum_deanforpanel")
	private Integer unumDeanforpanel;

	@Column(name="unum_entry_uid")
	private Long unumEntryUid;

	@Column(name="unum_mobile_no")
	private Long unumMobileNo;

	@Column(name="unum_t_appointtype_id")
	private Integer unumTAppointtypeId;

	@Column(name="unum_t_pgrecog")
	private Integer unumTPgrecog;

	@Column(name="unum_ugapproved")
	private Integer unumUgapproved;

	@Column(name="unum_univ_id")
	private Integer unumUnivId;

	@Column(name="unum_willingness_to_work_on_lic")
	private Integer unumWillingnessToWorkOnLic;

	@Column(name="ustr_description")
	private String ustrDescription;

	@Column(name="ustr_t_aadhar_no")
	private String ustrTAadharNo;

	@Column(name="ustr_t_appointtype")
	private String ustrTAppointtype;

	@Column(name="ustr_t_emailid")
	private String ustrTEmailid;

	@Column(name="ustr_t_pan_no")
	private String ustrTPanNo;

	@Column(name="ustr_emp_name")
	private String ustrEmpName;

	@Column(name="willingness_to_work_on_lic")
	private String willingnessToWorkOnLic;

	@Column(name="unum_dept_id")
	private Integer unumDeptId;

	@Column(name="ustr_emp_id")
	private String ustrEmpId;

	@Column(name="unum_is_teacher")
	private Integer unumIsTeacher;

	@Column(name="unum_is_selectedfor")
	private Integer unumIsSelectedfor;

	@Column(name="unum_tot_lic_chairman_count")
	private Integer unumTotLicChairmanCount;

	@Column(name="unum_tot_lic_member1_count")
	private Integer unumTotLicMember1Count;

	@Column(name="unum_tot_lic_member2_count")
	private Integer unumTotLicMember2Count;

	@Column(name="unum_ay_lic_chairman_count")
	private Integer unumAyLicChairmanCount;

	@Column(name="unum_ay_lic_member1_count")
	private Integer unumAyLicMember1Count;

	@Column(name="unum_ay_lic_member2_count")
	private Integer unumAyLicMember2Count;
}