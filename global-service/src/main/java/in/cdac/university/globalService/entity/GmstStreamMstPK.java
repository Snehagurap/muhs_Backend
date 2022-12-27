package in.cdac.university.globalService.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import javax.persistence.*;

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