package in.cdac.university.usm.entity;

import lombok.*;

import java.io.Serializable;
import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class UmmtUserMstPK implements Serializable {

	@Column(name = "gnum_userid", nullable = false)
	private Integer gnumUserid;

	@Column(name = "gnum_isvalid", nullable = false, precision = 1)
	private Integer gnumIsvalid;

}