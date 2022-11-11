package in.cdac.university.usm.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "ummt_role_mst", schema = "usm")
public class UmmtRoleMst implements java.io.Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ummt_role_mst")
	@SequenceGenerator(name="ummt_role_mst", sequenceName = "usm.seq_ummt_role_mst", allocationSize = 1)
	@Column(name = "gnum_role_id")
	private Integer gnumRoleId;
 
	@Column(name = "gstr_role_name", nullable = false, length = 100)
	private String gstrRoleName;

	@Column(name = "gnum_entry_by", nullable = false)
	private Long gnumEntryBy;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "gdt_effect_date", nullable = false, columnDefinition="timestamp without time zone default current_timestamp")
	private Date gdtEffectDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="gdt_entry_date", nullable = false, insertable = false, columnDefinition = "timestamp without time zone default current_timestamp")
	private Date gdtEntrydate;

	@Column(name="gbl_isvalid", nullable = false, columnDefinition = "numeric default 1")
	private Integer gblIsvalid;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "gnum_module_id")
	private UmstModuleMst module;
}
