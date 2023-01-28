package in.cdac.university.globalService.entity;

import java.io.Serial;
import java.io.Serializable;

import javax.persistence.Column;

public class GmstStuSubcatMstPK implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;
	

	@Column(name="unum_stu_sub_cat_id")
	private Long 	unumStuSubCatId;

	@Column(name="unum_isvalid")
	private Integer unumIsvalid;

}
