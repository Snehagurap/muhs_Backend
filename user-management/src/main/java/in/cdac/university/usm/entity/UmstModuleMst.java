package in.cdac.university.usm.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "umst_module_mst", schema = "usm")
public class UmstModuleMst implements java.io.Serializable {

	@Id
	@Column(name = "gnum_module_id", unique = true, nullable = false, precision = 2)
	private Integer gnumModuleId;

	@Column(name = "gstr_module_name", length = 200)
	private String gstrModuleName;

	@Column(name = "gbl_isvalid", nullable = false, insertable = false, columnDefinition = "numeric default 1")
	private Integer gblIsvalid;

	@Column(name = "gnum_module_type", precision = 1)
	private Integer gnumModuleType;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="gdt_entry_date", nullable = false, insertable = false, columnDefinition = "timestamp without time zone default current_timestamp")
	private Date gdtEntryDate;

	@Column(name = "gstr_module_schema", length = 30)
	private String gstrModuleSchema;

}
