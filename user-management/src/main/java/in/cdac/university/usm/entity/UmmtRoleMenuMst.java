package in.cdac.university.usm.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "ummt_role_menu_mst", schema = "usm",
		uniqueConstraints = @UniqueConstraint(name = "uq_ummt_role_menu_mst", columnNames = {"gnum_role_id", "gnum_menu_id", "gnum_module_id"}))
public class UmmtRoleMenuMst implements java.io.Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ummt_role_menu_mst")
	@SequenceGenerator(name = "ummt_role_menu_mst", sequenceName = "seq_ummt_role_menu_mst", allocationSize = 1)
	@Column(name = "gnum_role_menu_slno", nullable = false)
	private Integer gnumRoleMenuSlno;

	@Column(name = "gnum_role_id", nullable = false)
	private Integer gnumRoleId;

	@Column(name = "gnum_menu_id", nullable = false)
	private Integer gnumMenuId;

	@Column(name = "gnum_module_id", nullable = false)
	private Integer gnumModuleId;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "gdt_entry_date", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Date gdtEntryDate;
	
	@Column(name = "gnum_isvalid")
	private Integer gnumIsvalid;
	
	@Column(name = "gnum_display_order")
	private Integer gnumDisplayOrder;

	@Column(name = "gnum_entry_by")
	private Integer gnumEntryBy;
}
