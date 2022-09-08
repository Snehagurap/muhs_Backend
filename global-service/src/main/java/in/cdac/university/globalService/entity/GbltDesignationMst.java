package in.cdac.university.globalService.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "gblt_designation_mst", schema = "university")
public class GbltDesignationMst implements java.io.Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gblt_designation_mst")
	@SequenceGenerator(name = "gblt_designation_mst", sequenceName = "university.seq_gblt_designation_mst", allocationSize = 1)
	@Column(name = "gnum_desig_code", nullable = false, precision = 5)
	private Integer gnumDesigCode;

	@Column(name = "gstr_desig_name", length = 500)
	private String gstrDesignationName;
	
	@Column(name = "gstr_desig_st_name", length = 40)
	private String gstrDesignationShortName;

	@Column(name = "gnum_seat_id")
	private Integer gnumSeatid;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="gdt_entry_date", nullable = false, insertable = false, columnDefinition = "timestamp without time zone default current_timestamp")
	private Date gdtEntrydate;

	@Column(name="gnum_isvalid", nullable = false, insertable = false, columnDefinition = "numeric default 1")
	private Integer gnumIsvalid;

	@Column(name = "gstr_remarks", length = 500)
	private String gstrRemarks;
}
