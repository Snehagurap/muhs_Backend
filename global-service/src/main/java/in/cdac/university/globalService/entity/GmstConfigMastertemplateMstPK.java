package in.cdac.university.globalService.entity;

import lombok.*;

import java.io.Serializable;
import javax.persistence.*;

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