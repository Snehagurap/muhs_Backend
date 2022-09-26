package in.cdac.university.globalService.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GmstCourseDurationCatMstPK implements Serializable {

	@Column(name="unum_cd_category_id")
	private Long unumCdCategoryId;

	@Column(name="unum_isvalid")
	private Integer unumIsvalid;

}