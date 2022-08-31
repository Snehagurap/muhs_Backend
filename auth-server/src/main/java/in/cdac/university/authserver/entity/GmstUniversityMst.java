package in.cdac.university.authserver.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name="gmst_university_mst", schema = "usm")
public class GmstUniversityMst implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gmst_university_mst")
	@SequenceGenerator(name = "gmst_university_mst", sequenceName = "seq_gmst_university_mst", allocationSize = 1)
	@Column(name="unum_univ_id")
	private Integer unumUnivId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="udt_eff_from", nullable = false)
	private Date udtEffFrom;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="udt_eff_to")
	private Date udtEffTo;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="udt_entry_date")
	private Date udtEntryDate;

	@Column(name="unum_contactnumber1", nullable = false)
	private Long unumContactnumber1;

	@Column(name="unum_contactnumber2")
	private Long unumContactnumber2;

	@Column(name="unum_contactnumber3")
	private Long unumContactnumber3;

	@Column(name="unum_contactnumber4")
	private Long unumContactnumber4;

	@Column(name="unum_contactnumber5")
	private Long unumContactnumber5;

	@Column(name="unum_district_id")
	private Integer unumDistrictId;

	@Column(name="unum_isvalid")
	private Integer unumIsvalid;

	@Column(name="unum_pincode")
	private Integer unumPincode;

	@Column(name="unum_state_id")
	private Integer unumStateId;

	@Column(name="ustr_address2")
	private String ustrAddress2;

	@Column(name="ustr_address2_marathi")
	private String ustrAddress2Marathi;

	@Column(name="ustr_address3")
	private String ustrAddress3;

	@Column(name="ustr_address3_marathi")
	private String ustrAddress3Marathi;

	@Column(name="ustr_description")
	private String ustrDescription;

	@Column(name="ustr_email1", nullable = false)
	private String ustrEmail1;

	@Column(name="ustr_email2")
	private String ustrEmail2;

	@Column(name="ustr_email3")
	private String ustrEmail3;

	@Column(name="ustr_email4")
	private String ustrEmail4;

	@Column(name="ustr_email5")
	private String ustrEmail5;

	@Column(name="ustr_fax1")
	private String ustrFax1;

	@Column(name="ustr_fax2")
	private String ustrFax2;

	@Column(name="ustr_logo1")
	private String ustrLogo1;

	@Column(name="ustr_logo2")
	private String ustrLogo2;

	@Column(name="ustr_logo3")
	private String ustrLogo3;

	@Column(name="ustr_street_address")
	private String ustrStreetAddress;

	@Column(name="ustr_street_address_marathi")
	private String ustrStreetAddressMarathi;

	@Column(name="ustr_univ_code")
	private String ustrUnivCode;

	@Column(name="ustr_univ_fname")
	private String ustrUnivFname;

	@Column(name="ustr_univ_fname_marathi")
	private String ustrUnivFnameMarathi;

	@Column(name="ustr_univ_sname")
	private String ustrUnivSname;

	@Column(name="ustr_univ_sname_marathi")
	private String ustrUnivSnameMarathi;

	@Column(name="ustr_website1")
	private String ustrWebsite1;

	@Column(name="ustr_website2")
	private String ustrWebsite2;

	@Column(name="ustr_website3")
	private String ustrWebsite3;

	@Column(name = "gstr_password", length = 500)
	private String gstrPassword;

	@Column(name = "gstr_user_name", length = 100, unique = true)
	private String gstrUserName;
}