package in.cdac.university.usm.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "ummt_dataset_mst", schema = "usm")
public class UmmtDatasetMst implements java.io.Serializable {

	@Id
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
	private Long gnumEntryBy;
	
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

	@Formula("(select m.gstr_module_schema from usm.umst_db_schema_mst m where m.gbl_isvalid=1 and m.gnum_module_id=gnum_module_id)")
	private String gstrSchemaName;

	@Formula("coalesce((select string_agg(udf.gstr_filter_column_name , ', ' order by udf.gstr_filter_order) from usm.ummt_dataset_filter_mst udf  where udf.gstr_filter_column_name <> '1' and udf.gnum_dataset_id = gnum_dataset_id),'-')")
	private String gstrFilterColumnName;

}
