package in.cdac.university.usm.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@ToString
//@IdClass(GbltUserLogPK.class)
@Table(name = "gblt_user_log", schema = "usm")
public class GbltUserLog implements java.io.Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gblt_user_log")
	@SequenceGenerator(name = "gblt_user_log", sequenceName = "usm.seq_gblt_user_log", allocationSize = 1)
	private Long gnumLogId;

	private Long gnumUserid;

	private Integer unumUnivId;;

	private Date gdtLoginDate;

	@Column(name = "gdt_logutt_date", length = 29)
	private Date gdtLoguttDate;

	@Column(name = "gstr_ip_number", length = 250)
	private String gstrIpNumber;
}
