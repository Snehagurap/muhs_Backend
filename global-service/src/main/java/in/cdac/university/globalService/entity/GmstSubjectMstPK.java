package in.cdac.university.globalService.entity;

import lombok.*;

import javax.persistence.Column;
import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class GmstSubjectMstPK implements Serializable {

	@Column(name="unum_sub_id")
	private Long unumSubId;

	@Column(name="unum_isvalid")
	private Integer unumIsvalid;

}