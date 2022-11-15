package in.cdac.university.usm.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@ToString
@Table(name = "ummt_menu_mst", schema = "usm")
public class UmmtMenuMst implements java.io.Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ummt_menu_mst")
	@SequenceGenerator(name = "ummt_menu_mst", sequenceName = "usm.seq_ummt_menu_mst", allocationSize = 1)
	@Column(name = "gnum_menu_id", nullable = false)
	private Integer gnumMenuId;
	
	@Column(name = "gnum_parent_id")
	private Integer gnumParentId;
	
	@Column(name = "gstr_menu_name", nullable = false, length = 500)
	private String gstrMenuName;

	@Column(name = "gnum_menu_level", nullable = false)
	private Integer gnumMenuLevel;

	@Column(name = "gnum_entry_by", nullable = false)
	private Long gnumEntryBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "gdt_entry_date", nullable = false, columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Date gdtEntryDate;
	
	@Column(name = "gstr_url", length = 500)
	private String gstrUrl;
	
	@Column(name = "gnum_isvalid", nullable = false, precision = 1)
	private Integer gnumIsvalid;

	@Column(name = "gnum_module_id", nullable = false)
	private Integer gnumModuleId;

	@Column(name = "root_menu_id")
	private Integer rootMenuId;

	@Formula("(select m.gstr_module_name from usm.UMST_MODULE_MST m where m.gbl_isvalid=1 and m.gnum_module_id=gnum_module_id)")
	private String gstrModuleName;

	@Formula("(select m.gstr_menu_name from usm.ummt_menu_mst m where m.gnum_isvalid=1 and m.gnum_menu_id=gnum_parent_id)")
	private String gstrParentName;
}
