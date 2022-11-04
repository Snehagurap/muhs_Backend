package in.cdac.university.authserver.entity;

import java.util.Date;

import javax.persistence.*;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@IdClass(GbltUserLogPK.class)
@Table(name = "gblt_user_log", schema = "usm")
public class GbltUserLog implements java.io.Serializable {

	@Id
	private Long gnumUserid;

	@Id
	private Integer unumUnivId;;

	@Id
	private Date gdtLoginDate;

	@Column(name = "gdt_logutt_date", length = 29)
	private Date gdtLoguttDate;

	@Column(name = "gstr_ip_number", length = 250)
	private String gstrIpNumber;
}
