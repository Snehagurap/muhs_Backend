package in.cdac.university.usm.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "gblt_country_mst_imsc", schema = "usm")
public class GbltCountryMstImsc implements java.io.Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="gblt_country_mst_imsc")
	@SequenceGenerator(name="gblt_country_mst_imsc", sequenceName = "usm.seq_gblt_country_mst_imsc", allocationSize = 1)
	@Column(name = "gnum_countrycode", nullable = false, precision = 3)
	private Integer gnumCountrycode;
	
	@Column(name = "gstr_countryname", nullable = false, length = 200)
	private String gstrCountryname;
	
	@Column(name = "gstr_countryshort", length = 5)
	private String gstrCountryshort;

	@Column(name = "gnum_seatid", precision = 8)
	private Integer gnumSeatid;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="gdt_entry_date", nullable = false, insertable = false, columnDefinition = "timestamp without time zone default current_timestamp")
	private Date gdtEntrydate;

	@Column(name="gnum_isvalid", nullable = false, insertable = false, columnDefinition = "numeric default 1")
	private Integer gnumIsvalid;

	@Column(name = "gstr_nationality", length = 30)
	private String gstrNationality;

	@Column(name = "gnum_is_default_country", nullable = false, precision = 1)
	private Integer gnumIsDefaultCountry;
	
	@Column(name = "gnum_hl7_code", precision = 10)
	private Long gnumHl7Code;
}
