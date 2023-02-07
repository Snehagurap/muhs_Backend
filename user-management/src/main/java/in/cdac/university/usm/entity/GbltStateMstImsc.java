package in.cdac.university.usm.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@IdClass(GbltStateMstImscPK.class)
@Entity
@Table(name = "gblt_state_mst_imsc", schema = "usm")
public class GbltStateMstImsc implements java.io.Serializable {

	@Id
	@Column(name = "gnum_statecode", nullable = false, precision = 2)
	private Integer gnumStatecode;

	@Id
	@Column(name="gnum_isvalid", nullable = false, insertable = false, columnDefinition = "numeric default 1")
	private Integer gnumIsvalid;

	@Column(name = "gnum_countrycode", precision = 3)
	private Integer gnumCountrycode;

	@Column(name = "gstr_statename", length = 200)
	private String gstrStatename;

	@Column(name = "gstr_stateshort", length = 5)
	private String gstrStateshort;

	@Column(name = "gnum_seatid", precision = 8)
	private Integer gnumSeatid;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="gdt_entry_date", nullable = false, insertable = false, columnDefinition = "timestamp without time zone default current_timestamp")
	private Date gdtEntrydate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "gdt_lstmod_date")
	private Date gdtLstmodDate;

	@Column(name = "gnum_lstmod_seatid", precision = 8)
	private Integer gnumLstmodSeatid;

	@Column(name = "gstr_remarks", length = 500)
	private String gstrRemarks;

	@Column(name = "gnum_hl7_code", precision = 10)
	private Integer gnumHl7Code;

	@Column(name = "gnum_is_default_state", precision = 1)
	private Integer gnumIsDefaultState;

	@Column(name = "gnum_is_default_ut", precision = 1)
	private Integer gnumIsDefaultUt;
	
	@Column(name = "gstr_datasource_name", length = 50)
	private String gstrDatasourceName;
	
	@Column(name = "gstr_schema_name", length = 50)
	private String gstrSchemaName;

}
