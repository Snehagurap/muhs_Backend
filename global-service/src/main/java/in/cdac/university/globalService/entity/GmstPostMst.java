package in.cdac.university.globalService.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name="gmst_post_mst", schema = "university")
@IdClass(GmstPostMstPK.class)
public class GmstPostMst implements Serializable {

	@Id
	private Integer unumPostId;

	@Id
	private Integer unumIsvalid;

	@Temporal(TemporalType.DATE)
	@Column(name="udt_eff_frm")
	private Date udtEffFrm;

	@Temporal(TemporalType.DATE)
	@Column(name="udt_eff_to")
	private Date udtEffTo;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="udt_entry_date")
	private Date udtEntryDate;

	@Column(name="unum_parent_post_id")
	private Integer unumParentPostId;

	@Column(name="unum_univ_id")
	private Integer unumUnivId;

	@Column(name="ustr_description")
	private String ustrDescription;

	@Column(name="ustr_post_fname")
	private String ustrPostFname;

	@Column(name="ustr_post_sname")
	private String ustrPostSname;

}