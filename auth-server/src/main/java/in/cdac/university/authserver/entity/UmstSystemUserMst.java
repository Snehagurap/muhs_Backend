package in.cdac.university.authserver.entity;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

import lombok.*;
import org.hibernate.annotations.GeneratorType;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "umst_system_user_mst", schema = "usm")
public class UmstSystemUserMst implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "umst_system_user_mst")
	@SequenceGenerator(name = "umst_system_user_mst", sequenceName = "seq_umst_system_user_mst", allocationSize = 1)
	@Column(name = "gnum_sys_user_id", unique = true, nullable = false)
	private Integer gnumSysUserId;

	@Column(name = "gstr_sys_user_name", nullable = false, length = 200)
	private String gstrSysUserName;

	@Column(name = "gbl_isvalid", nullable = false, precision = 1, scale = 0)
	private Integer gblIsvalid;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "gdt_entry_date", nullable = false)
	private Date gdtEntryDate;

	@Column(name = "gstr_sys_password", nullable = false, length = 250)
	private String gstrSysPassword;

	@Column(name = "gstr_sys_login_id", nullable = false, length = 100)
	private String gstrSysLoginId;

}
