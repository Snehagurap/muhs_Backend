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
public class GmstRegionMstPK implements Serializable {

	@Column(name="unum_region_id")
	private Long unumRegionId;

	@Column(name="unum_isvalid")
	private Long unumIsvalid;
}