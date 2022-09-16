package in.cdac.university.globalService.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
public class GmstPostMstPK implements Serializable {

	@Column(name="unum_post_id")
	private Integer unumPostId;

	@Column(name="unum_isvalid")
	private Integer unumIsvalid;

}