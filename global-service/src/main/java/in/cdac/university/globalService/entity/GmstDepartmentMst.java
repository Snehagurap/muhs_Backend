package in.cdac.university.globalService.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name="gmst_department_mst", schema = "university")
@IdClass(GmstDepartmentMstPK.class)
public class GmstDepartmentMst implements Serializable {

	@Id
	private Integer unumDeptId;

	@Id
	private Integer unumIsvalid;

	@Temporal(TemporalType.DATE)
	@Column(name="udt_eff_frm")
	private Date udtEffFrm;

	@Temporal(TemporalType.DATE)
	@Column(name="udt_eff_to")
	private Date udtEffTo;

	@Temporal(TemporalType.DATE)
	@Column(name="udt_entry_date")
	private Date udtEntryDate;

	@Column(name="unum_parent_dept_id")
	private Integer unumParentDeptId;

	@Column(name="unum_univ_id")
	private Integer unumUnivId;

	@Column(name="ustr_dept_fname")
	private String ustrDeptFname;

	@Column(name="ustr_dept_sname")
	private String ustrDeptSname;

	@Column(name="ustr_description")
	private String ustrDescription;

	@Formula("(select u.ustr_dept_fname from university.gmst_department_mst u where u.unum_isvalid = 1 and u.unum_dept_id = unum_parent_dept_id)")
	private String parentDepartmentName;
}