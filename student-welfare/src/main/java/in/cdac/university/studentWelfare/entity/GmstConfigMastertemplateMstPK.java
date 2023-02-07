package in.cdac.university.studentWelfare.entity;

import lombok.*;

import javax.persistence.Column;
import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class GmstConfigMastertemplateMstPK implements Serializable {

	@Column(name="unum_mtemple_id")
	private Long unumMtempleId;

	@Column(name="unum_isvalid")
	private Integer unumIsvalid;

}