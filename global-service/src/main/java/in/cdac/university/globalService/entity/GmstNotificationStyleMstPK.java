package in.cdac.university.globalService.entity;

import lombok.*;

import javax.persistence.Column;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class GmstNotificationStyleMstPK implements Serializable {

	@Column(name="unum_nstyle_id")
	private Long unumNstyleId;

	@Column(name="unum_isvalid")
	private Integer unumIsvalid;
}