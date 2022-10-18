package in.cdac.university.globalService.entity;

import lombok.*;

import javax.persistence.Column;
import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class GmstConfigTemplateDtlPK implements Serializable {
	@Column(name="unum_templ_id")
	private Long unumTemplId;

	@Column(name="unum_isvalid")
	private Integer unumIsvalid;

}