package in.cdac.university.authserver.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

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