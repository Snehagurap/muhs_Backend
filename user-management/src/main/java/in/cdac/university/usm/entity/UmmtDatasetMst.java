package in.cdac.university.usm.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "ummt_dataset_mst", schema = "usm")
public class UmmtDatasetMst implements java.io.Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ummt_dataset_mst")
	@SequenceGenerator(name = "ummt_dataset_mst", sequenceName = "seq_ummt_dataset_mst", allocationSize = 1)
	@Column(name = "gnum_dataset_id", unique = true, nullable = false)
	private Integer gnumDatasetId;
	 
	@Column(name = "gstr_table_name", length = 100)
	private String gstrTableName;
	
	@Column(name = "gstr_column_name", length = 100)
	private String gstrColumnName;
	
	@Column(name = "gstr_display_column", length = 100)
	private String gstrDisplayColumn;

	@Column(name="gbl_isvalid", nullable = false, insertable = false, columnDefinition = "numeric default 1")
	private Integer gblIsvalid;
	
	@Column(name = "gnum_entry_by", nullable = false, precision = 8)
	private Integer gnumEntryBy;
	
	@Column(name = "gstr_dataset_name", length = 500)
	private String gstrDatasetName;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "gdt_entry_date", insertable = false, columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Date gdtEntryDate;
	
	@Column(name = "gnum_module_id", precision = 2)
	private Integer gnumModuleId;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "gdt_lstmod_date")
	private Date gdtLstmodDate;
	
	@Column(name = "gnum_lstmod_by", precision = 8)
	private Integer gnumLstmodBy;
}
