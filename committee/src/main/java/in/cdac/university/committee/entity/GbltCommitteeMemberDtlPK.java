package in.cdac.university.committee.entity;

import lombok.*;

import javax.persistence.Column;
import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class GbltCommitteeMemberDtlPK implements Serializable {

	@Column(name="unum_com_member_id")
	private Long unumComMemberId;

	@Column(name="unum_isvalid")
	private Integer unumIsvalid;

}