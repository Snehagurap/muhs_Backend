package in.cdac.university.globalService.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name="gmst_college_mst", schema = "university")
@IdClass(GmstCollegeMstPK.class)
public class GmstCollegeMst implements Serializable {

	@Id
	private Long unumCollegeId;

	@Id
	private Integer unumIsvalid;

	@Temporal(TemporalType.DATE)
	@Column(name="udt_eff_from")
	private Date udtEffFrom;

	@Temporal(TemporalType.DATE)
	@Column(name="udt_eff_to")
	private Date udtEffTo;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="udt_entry_date")
	private Date udtEntryDate;

	@Column(name="unum_biometric_status")
	private Integer unumBiometricStatus;

	@Column(name="unum_cctv_status")
	private Integer unumCctvStatus;

	@Column(name="unum_contactnumber1")
	private String unumContactnumber1;

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

	@Column(name="unum_entry_uid")
	private Long unumEntryUid;

	@Column(name="unum_lab_availability_status")
	private Integer unumLabAvailabilityStatus;

	@Column(name="unum_nknconnectivity_status")
	private Integer unumNknconnectivityStatus;

	@Column(name="unum_pincode")
	private String unumPincode;

	@Column(name="unum_present_aff_status_id")
	private Integer unumPresentAffStatusId;

	@Column(name="unum_regionid")
	private Integer unumRegionid;

	@Column(name = "unum_admin_region_id")
	private Integer unumAdminRegionId;

	@Column(name="unum_state_id")
	private Integer unumStateId;

	@Column(name="unum_tot_noof_contractteachers")
	private Integer unumTotNoofAddhocteachers;

	@Column(name="unum_tot_noof_labs")
	private Integer unumTotNoofLabs;

	@Column(name="unum_tot_noof_practical_capacity")
	private Integer unumTotNoofPracticalCapacity;

	@Column(name="unum_tot_noof_presentcourses")
	private Integer unumTotNoofPresentcourses;

	@Column(name="unum_tot_noof_presentfaculties")
	private Integer unumTotNoofPresentfaculties;

	@Column(name="unum_tot_noof_regularteachers")
	private Integer unumTotNoofRegularteachers;

	@Column(name="unum_tot_noof_theory_capacity")
	private Integer unumTotNoofTheoryCapacity;

	@Column(name="unum_univ_id")
	private Integer unumUnivId;

	@Column(name="ustr_address2")
	private String ustrAddress2;

	@Column(name="ustr_address2_marathi")
	private String ustrAddress2Marathi;

	@Column(name="ustr_address3")
	private String ustrAddress3;

	@Column(name="ustr_address3_marathi")
	private String ustrAddress3Marathi;

	@Column(name="ustr_anti_raggingcell_contactno")
	private String ustrAntiRaggingcellContactno;

	@Column(name="ustr_anti_raggingcell_emailid")
	private String ustrAntiRaggingcellEmailid;

	@Column(name="ustr_col_code")
	private String ustrColCode;

	@Column(name="ustr_col_fname")
	private String ustrColFname;

	@Column(name="ustr_col_fname_marathi")
	private String ustrColFnameMarathi;

	@Column(name="ustr_col_sname")
	private String ustrColSname;

	@Column(name="ustr_col_sname_marathi")
	private String ustrColSnameMarathi;

	@Column(name="ustr_description")
	private String ustrDescription;

	@Column(name="ustr_email1")
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

	@Column(name="ustr_hostel_contactno")
	private String ustrHostelContactno;

	@Column(name="ustr_hostel_emailid")
	private String ustrHostelEmailid;

	@Column(name="ustr_intranet_body")
	private String ustrIntranetBody;

	@Column(name="ustr_intranet_facultyid")
	private String ustrIntranetFacultyid;

	@Column(name="ustr_intranet_pin")
	private String ustrIntranetPin;

	@Column(name="ustr_intranet_state")
	private String ustrIntranetState;

	@Column(name="ustr_logo1")
	private String ustrLogo1;

	@Column(name="ustr_logo2")
	private String ustrLogo2;

	@Column(name="ustr_logo3")
	private String ustrLogo3;

	@Column(name="ustr_naac_accrediationno")
	private String ustrNaacAccrediationno;

	@Column(name="ustr_scstobccell_contactno")
	private String ustrScstobccellContactno;

	@Column(name="ustr_scstobccell_emailid")
	private String ustrScstobccellEmailid;

	@Column(name="ustr_sex_harr_cell_contactno")
	private String ustrSexHarrCellContactno;

	@Column(name="ustr_sex_harr_cell_emailid")
	private String ustrSexHarrCellEmailid;

	@Column(name="ustr_static_ipaddress")
	private String ustrStaticIpaddress;

	@Column(name="ustr_street_address")
	private String ustrStreetAddress;

	@Column(name="ustr_street_address_marathi")
	private String ustrStreetAddressMarathi;

	@Column(name="ustr_website1")
	private String ustrWebsite1;

	@Column(name="ustr_website2")
	private String ustrWebsite2;

	@Column(name="ustr_website3")
	private String ustrWebsite3;

	@Formula("(select t.ustr_region_fname from university.gmst_region_mst t where t.unum_region_id = unum_regionid)")
	private String regionName;
}