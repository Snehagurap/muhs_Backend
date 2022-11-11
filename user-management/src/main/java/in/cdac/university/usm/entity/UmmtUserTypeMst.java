package in.cdac.university.usm.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="ummt_user_type_mst", schema = "usm")
public class UmmtUserTypeMst implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ummt_user_type_mst")
	@SequenceGenerator(name="ummt_user_type_mst", sequenceName = "usm.seq_ummt_user_type_mst", allocationSize = 1)
	@Column(name="gnum_user_type_id")
	private Integer gnumUserTypeId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="gdt_entry_date", nullable = false, insertable = false, columnDefinition = "timestamp without time zone default current_timestamp")
	private Date gdtEntryDate;

	@Column(name="gnum_alert_flag")
	private Integer gnumAlertFlag;

	@Column(name="gnum_dataset_id")
	private Integer gnumDatasetId;

	@Column(name="gnum_entry_by", nullable = false)
	private Long gnumEntryBy;

	@Column(name="gnum_isvalid", nullable = false, columnDefinition = "numeric default 1")
	private Integer gnumIsvalid;

	@Column(name="gnum_module_id", nullable = false)
	private Integer gnumModuleId;

	@Column(name="gnum_task_flag")
	private Integer gnumTaskFlag;

	@Column(name="gstr_user_type_name", nullable = false)
	private String gstrUserTypeName;

	@Formula("(select a.gstr_dataset_name from usm.ummt_dataset_mst a where a.gnum_dataset_id = gnum_dataset_id)")
	private String defaultDataset;

//	@Column(name="gnum_role_id")
//	private Integer gnumRoleId;Default Dataset'
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "gnum_role_id")
	private UmmtRoleMst roleMst;

//	@Column(name="gnum_user_cat_id", nullable = false)
//	private Integer gnumUserCatId;
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "gnum_user_cat_id")
	private UmstUserCategoryMst userCategoryMst;
}