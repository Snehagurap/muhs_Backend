package in.cdac.university.globalService.entity;

import lombok.*;

import javax.persistence.Column;
import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class GmstRegionMstPK implements Serializable {

	@Column(name="unum_region_id")
	private Long unumRegionId;

	@Column(name="unum_isvalid")
	private Long unumIsvalid;
}