package in.cdac.university.globalService.entity;

import java.io.Serial;
import java.io.Serializable;

import javax.persistence.Column;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
 
 

@Getter
@Setter
@EqualsAndHashCode
public class GmstStreamMstPK implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;

	@Column(name="unum_stream_id")
	private Long unumStreamId;

	@Column(name="unum_isvalid")
	private Integer unumIsvalid;
 
}
