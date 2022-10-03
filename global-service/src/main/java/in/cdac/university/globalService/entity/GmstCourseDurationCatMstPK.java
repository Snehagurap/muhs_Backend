package in.cdac.university.globalService.entity;

import lombok.*;

import javax.persistence.Column;
import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class GmstCourseDurationCatMstPK implements Serializable {

	@Column(name="unum_cd_category_id")
	private Long unumCdCategoryId;

	@Column(name="unum_isvalid")
	private Integer unumIsvalid;

}