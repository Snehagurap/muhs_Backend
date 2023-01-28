package in.cdac.university.globalService.entity;

import java.io.Serial;
import java.io.Serializable;

import javax.persistence.Column;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class GmstStuCatMstPK implements Serializable {
	
	@Serial
	private static final long serialVersionUID = 1L;
	
	@Column(name="unum_stu_cat_id") 
	private Long unumStuCatId;

	@Column(name="unum_isvalid") 
	private Integer unumIsvalid;
	
}
