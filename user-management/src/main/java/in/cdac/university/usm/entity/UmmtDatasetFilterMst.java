package in.cdac.university.usm.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@IdClass(UmmtDatasetFilterPK.class)
@Table(name = "ummt_dataset_filter_mst", schema = "usm")
public class UmmtDatasetFilterMst implements java.io.Serializable {

	@Id
	private Integer gnumDatasetId;

	@Id
	private Integer gnumSlNo;
	
	@Column(name = "gstr_table_name", length = 100)
	private String gstrTableName;
	
	@Column(name = "gstr_filter_column_name", length = 100)
	private String gstrFilterColumnName;
	
	@Column(name = "gstr_filter_query", length = 2000)
	private String gstrFilterQuery;
	
	@Column(name = "gbl_isvalid", insertable = false, precision = 1, columnDefinition = "numeric DEFAULT 1")
	private Integer gblIsvalid;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "gdt_entry_date", insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Date gdtEntryDate;
	
	@Column(name = "gnum_module_id", precision = 2)
	private Integer gnumModuleId;
	
	@Column(name = "gstr_filter_order", precision = 1)
	private Integer gstrFilterOrder;
	
	@Column(name = "gstr_filter_display", nullable = false, length = 50)
	private String gstrFilterDisplay;

}
