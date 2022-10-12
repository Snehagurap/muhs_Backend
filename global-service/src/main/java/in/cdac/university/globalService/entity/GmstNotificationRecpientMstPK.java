package in.cdac.university.globalService.entity;

import lombok.*;

import javax.persistence.Column;
import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class GmstNotificationRecpientMstPK implements Serializable {

	@Column(name="unum_nrec_id")
	private Long unumNrecId;

	@Column(name="unum_isvalid")
	private Integer unumIsvalid;

}