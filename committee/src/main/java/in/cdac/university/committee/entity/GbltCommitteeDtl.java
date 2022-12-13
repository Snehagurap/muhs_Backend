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
@Table(name="gblt_committee_dtl", schema = "ucom")
@IdClass(GbltCommitteeDtlPK.class)
public class GbltCommitteeDtl implements Serializable {

	@Id
	private Long unumComroleid;

	@Id
	private Integer unumIsvalid;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="udt_entry_date", insertable = false)
	private Date udtEntryDate;

	@Column(name="unum_comid")
	private Long unumComid;

	@Column(name="unum_comrolsno")
	private Integer unumComrolsno;

	@Column(name="unum_role_id")
	private Integer unumRoleId;

	@Column(name="unum_role_cfaculty_id")
	private Integer unumRoleCfacultyId;

	@Column(name="unum_role_department_id")
	private Integer unumRoleDepartmentId;

	@Column(name="unum_role_is_external")
	private Integer unumRoleIsExternal;

	@Column(name="unum_role_max_exp_years")
	private Integer unumRoleMaxExpYears;

	@Column(name="unum_role_max_yearly_occurance")
	private Integer unumRoleMaxYearlyOccurance;

	@Column(name="unum_role_min_exp_years")
	private Integer unumRoleMinExpYears;

	@Column(name="unum_min_approved_exp")
	private Integer unumMinApprovedExp;

	@Column(name="unum_role_min_yearly_occurance")
	private Integer unumRoleMinYearlyOccurance;

	@Column(name="unum_role_post_id")
	private Integer unumRolePostId;

	@Column(name="unum_univ_id")
	private Integer unumUnivId;

	@Column(name="ustr_comrole_description")
	private String ustrComroleDescription;

	@Column(name = "unum_entry_uid")
	private Long unumEntryUid;

	@Formula("(select t.ustr_cfaculty_fname from university.gmst_faculty_mst t where t.unum_cfaculty_id = unum_role_cfaculty_id)")
	private String facultyName;

	@Formula("(select t.ustr_post_fname from university.gmst_post_mst t where t.unum_post_id = unum_role_post_id)")
	private String departmentName;

	@Formula("(select t.ustr_dept_fname from university.gmst_department_mst t where t.unum_dept_id = unum_role_department_id)")
	private String designationName;
}