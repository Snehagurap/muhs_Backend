package in.cdac.university.usm.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import java.util.Date;

@Getter
@Setter
public class GbltUserLogPK implements java.io.Serializable {

	@Column(name = "gnum_userid", nullable = false)
	private Long gnumUserid;

	@Column(name="unum_univ_id")
	private Integer unumUnivId;

	@Column(name = "gdt_login_date", nullable = false, length = 29)
	private Date gdtLoginDate;
}
