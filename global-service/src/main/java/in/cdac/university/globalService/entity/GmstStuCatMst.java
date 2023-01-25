package in.cdac.university.globalService.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.IdClass;
import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@IdClass(GmstStuCatMstPK.class)
@Entity
@Table(name="gmst_stu_cat_mst", schema = "university")
public class GmstStuCatMst {
	
	@Id
	private Long unumStuCatId;

	@Id
	private Integer unumIsvalid;

	@Column(name="ustr_stu_cat_sname") 
	private String  ustrStuCatSname;

	@Column(name="ustr_stu_cat_fname") 
	private String ustrStuCatFname;

	@Column(name="unum_univ_id") 
	private Integer unumUnivId;

	@Temporal(TemporalType.DATE)
	@Column(name="udt_eff_from") 
	private Date udtEffFrom;

	@Temporal(TemporalType.DATE)
	@Column(name="udt_eff_to") 
	private Date udtEffTo;

	@Column(name="unum_entry_uid") 
	private Integer unumEntryUid;

	@Column(name="udt_entry_date") 
	@Temporal(TemporalType.DATE)
	private Date udtEntryDate;

	@Column(name="unum_lst_mod_uid") 
	private Integer unumLstModUid;

	@Temporal(TemporalType.DATE)
	@Column(name="udt_lst_mod_date") 
	private Date udtLstModDate;
}
