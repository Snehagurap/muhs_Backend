package in.cdac.university.globalService.entity;

import lombok.*;

import java.io.Serializable;
import javax.persistence.*;

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