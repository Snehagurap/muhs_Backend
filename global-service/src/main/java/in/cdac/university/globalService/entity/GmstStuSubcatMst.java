package in.cdac.university.globalService.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.IdClass;
import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@IdClass(GmstStuSubcatMstPK.class)
@Entity
@Table(name="gmst_stu_subcat_mst", schema = "university")
public class GmstStuSubcatMst {

	@Id
	private Long 	unumStuSubCatId;

	@Id
	private Integer unumIsvalid;

	@Column(name="unum_stu_cat_id")
	private Long 	unumStuCatId;

	@Column(name="ustr_stu_sub_cat_sname")
	private String  ustrStuSubCatSname;

	@Column(name="ustr_stu_sub_cat_fname")
	private String  ustrStuSubCatFname;

	@Column(name="unum_univ_id")
	private Integer unumUnivId;

	@Temporal(TemporalType.DATE)
	@Column(name="udt_eff_from")
	private Date 	udtEffFrom;

	@Temporal(TemporalType.DATE)
	@Column(name="udt_eff_to")
	private Date    udtEffTo;

	@Column(name="unum_entry_uid")
	private Integer unumEntryUid;

	@Temporal(TemporalType.DATE)
	@Column(name="udt_entry_date")
	private Date    udtEntryDate;

	@Column(name="unum_lst_mod_uid")
	private Integer unumLstModUid;

	@Temporal(TemporalType.DATE)
	@Column(name="udt_lst_mod_date")
	private Date    udtLstModDate;

}
