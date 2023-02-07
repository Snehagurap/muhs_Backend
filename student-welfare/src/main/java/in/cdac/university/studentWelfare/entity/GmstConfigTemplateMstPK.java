package in.cdac.university.studentWelfare.entity;

import lombok.*;

import javax.persistence.Column;
import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class GmstConfigTemplateMstPK implements Serializable {
	@Column(name="unum_temple_id")
	private Long unumTempleId;

	@Column(name="unum_isvalid")
	private Integer unumIsvalid;
}