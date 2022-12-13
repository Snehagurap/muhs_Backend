package in.cdac.university.globalService.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name="gmst_course_cat_mst", schema = "university")
@IdClass(GmstCourseDurationCatMstPK.class)
public class GmstCourseDurationCatMst implements Serializable {

	@Id
	private Long unumCdCategoryId;

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

	@Column(name="unum_entry_uid")
	private Long unumEntryUid;

	@Column(name="unum_exon_cd_categoryid")
	private String unumExonCdCategoryid;

	@Column(name="unum_intra_cd_categoryid")
	private String unumIntraCdCategoryid;

	@Column(name="unum_univ_id")
	private Integer unumUnivId;

	@Column(name="ustr_cd_category_fname")
	private String ustrCdCategoryFname;

	@Column(name="ustr_cd_category_sname")
	private String ustrCdCategorySname;

	@Column(name="ustr_description")
	private String ustrDescription;

}