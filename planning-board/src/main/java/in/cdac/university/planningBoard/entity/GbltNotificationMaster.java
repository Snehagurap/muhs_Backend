package in.cdac.university.planningBoard.entity;

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
@IdClass(GbltNotificationMasterPK.class)
@Table(name="gblt_notification_master", schema = "upb")
public class GbltNotificationMaster implements Serializable {

	@Id
	private Long unumNid;

	@Id
	private Integer unumIsvalid;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="udt_entry_date")
	private Date udtEntryDate;

	@Temporal(TemporalType.DATE)
	@Column(name="udt_n_dt")
	private Date udtNDt;

	@Temporal(TemporalType.DATE)
	@Column(name="udt_n_out_dt")
	private Date udtNOutDt;

	@Temporal(TemporalType.DATE)
	@Column(name="udt_valid_frm")
	private Date udtValidFrm;

	@Temporal(TemporalType.DATE)
	@Column(name="udt_valid_to")
	private Date udtValidTo;

	@Column(name="unum_approving_userid")
	private Long unumApprovingUserid;

	@Column(name="unum_cfaculty_id")
	private Integer unumCfacultyId;

	@Column(name="unum_dept_id")
	private Integer unumDeptId;

	@Column(name="unum_entry_uid")
	private Long unumEntryUid;

	@Column(name="unum_is_ammendment")
	private Integer unumIsAmmendment;

	@Column(name="unum_main_lang_id")
	private Integer unumMainLangId;

	@Column(name="unum_main_nid")
	private Long unumMainNid;

	@Column(name="unum_n_recepient")
	private Integer unumNRecepient;

	@Column(name="unum_n_style")
	private Integer unumNStyle;

	@Column(name="unum_npurpose_id")
	private Integer unumNtypeId;

	@Column(name="unum_univ_id")
	private Integer unumUnivId;

	@Column(name="ustr_academic_year")
	private String ustrAcademicYear;

	@Column(name="ustr_approving_orderno")
	private String ustrApprovingOrderno;

	@Column(name="ustr_approving_username")
	private String ustrApprovingUsername;

	@Column(name="ustr_description")
	private String ustrDescription;

	@Column(name="ustr_n_main_heading")
	private String ustrNMainHeading;

	@Column(name="ustr_n_no")
	private String ustrNNo;

	@Column(name="ustr_n_out_no")
	private String ustrNOutNo;

	@Column(name="ustr_n_sub_heading")
	private String ustrNSubHeading;

}