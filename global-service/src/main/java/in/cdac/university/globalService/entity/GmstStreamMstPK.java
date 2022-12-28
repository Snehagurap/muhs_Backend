package in.cdac.university.globalService.entity;

import java.io.Serializable;

import javax.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class GmstStreamMstPK  implements Serializable{

	@Column(name="unum_stream_id")
	private Long unumStreamId;

	@Column(name="unum_isvalid")
	private Integer unumIsvalid;
}
