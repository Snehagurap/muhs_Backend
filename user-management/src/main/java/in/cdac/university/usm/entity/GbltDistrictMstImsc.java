package in.cdac.university.usm.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "gblt_district_mst_imsc", schema = "usm")
public class GbltDistrictMstImsc implements java.io.Serializable {

	@Id
	@GeneratedValue(strategy= GenerationType.SEQUENCE, generator="gblt_district_mst_imsc")
	@SequenceGenerator(name="gblt_district_mst_imsc", sequenceName = "seq_gblt_district_mst_imsc", allocationSize = 1)
	@Column(name = "num_dist_id", nullable = false, precision = 5)
	private Integer numDistId;
	
	@Column(name = "str_dist_code", length = 6)
	private String strDistCode;
	
	@Column(name = "str_dist_name", length = 200)
	private String strDistName;
	
	@Column(name = "str_dist_st_name", length = 5)
	private String strDistStName;
	
	@Column(name = "gnum_slno", nullable = false, precision = 3)
	private Integer gnumSlno;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "gdt_effective_frm", nullable = false)
	private Date gdtEffectiveFrm;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "gdt_effective_to")
	private Date gdtEffectiveTo;

	@Column(name="gnum_isvalid", nullable = false, insertable = false, columnDefinition = "numeric default 1")
	private Integer gnumIsvalid;
	
	@Column(name = "gnum_seat_id", precision = 8)
	private Integer gnumSeatId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="gdt_entry_date", nullable = false, insertable = false, columnDefinition = "timestamp without time zone default current_timestamp")
	private Date gdtEntryDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "gdt_lstmod_date")
	private Date gdtLstmodDate;
	
	@Column(name = "gnum_lstmod_seatid", precision = 8)
	private Integer gnumLstmodSeatid;
	
	@Column(name = "gstr_remarks", length = 500)
	private String gstrRemarks;
	
	@Column(name = "gnum_statecode", precision = 2)
	private Integer gnumStatecode;
	
	@Column(name = "num_zone_id", precision = 5)
	private Integer numZoneId;
	
	@Column(name = "hstnum_longitude", precision = 10, scale = 6)
	private Double hstnumLongitude;
	
	@Column(name = "hstnum_latitude", precision = 10, scale = 6)
	private Double hstnumLatitude;
	
	@Column(name = "hststr_map_feature_id", length = 15)
	private String hststrMapFeatureId;

}
