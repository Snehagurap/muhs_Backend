package in.cdac.university.usm.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@ToString
@Entity
@Table(name = "ummt_user_mst", schema = "usm")
@IdClass(UmmtUserMstPK.class)
public class UmmtUserMst implements java.io.Serializable {

	@Id
	private Long gnumUserid;

	@Id
	private Integer gnumIsvalid;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "gdt_effect_date")
	private Date gdtEffectDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="gdt_entry_date", nullable = false, columnDefinition = "timestamp without time zone default current_timestamp")
	private Date gdtEntrydate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "gdt_expiry_date")
	private Date gdtExpiryDate;

	@Column(name = "gnum_dataset_id")
	private Integer gnumDatasetId;

	@Column(name = "gnum_default_data_id")
	private Integer gnumDefaultDataId;

	@Column(name = "gnum_default_data_value", length = 500)
	private String gnumDefaultDataValue;

	@Column(name = "gnum_designation")
	private Integer gnumDesignation;

	@Column(name = "gnum_entry_by", nullable = false, precision = 9)
	private Long gnumEntryBy;

	@Column(name = "gnum_islock", nullable = false, precision = 1)
	private Integer gnumIslock;

	@Column(name = "gnum_mobile_number", length = 25)
	private String gnumMobileNumber;

	@Column(name = "gnum_role_id", nullable = false, precision = 4)
	private Integer gnumRoleId;

	@Column(name = "gnum_statecode", precision = 2)
	private Integer gnumStatecode;

	@Column(name = "gnum_user_cat_id", nullable = false)
	private Integer gnumUserCatId;

	@Column(name = "gnum_user_type_id", nullable = false)
	private Integer gnumUserTypeId;

	@Column(name = "gstr_aadhar_number", length = 50)
	private String gstrAadharNumber;

	@Column(name = "gstr_email_id", length = 500)
	private String gstrEmailId;

	@Column(name = "gstr_password", nullable = false, length = 500)
	private String gstrPassword;

	@Column(name = "gstr_old_password", length = 500)
	private String gstrOldPassword;

	@Column(name = "gstr_user_full_name", nullable = false, length = 500)
	private String gstrUserFullName;

	@Column(name = "gstr_user_name", nullable = false, length = 100)
	private String gstrUserName;

	@Column(name = "num_dist_id", precision = 5)
	private Integer numDistId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "gdt_changepassword_date", length = 29)
	private Date gdtChangepasswordDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "gdt_lstmod_date", length = 29)
	private Date gdtLstmodDate;
	
	@Column(name = "gnum_lstmod_by", precision = 9)
	private Integer gnumLstmodBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "gnum_last_login_date", length = 29)
	private Date gnumLastLoginDate;

	// Mappings
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "unum_univ_id")
	@ToString.Exclude
	private GmstUniversityMst universityMst;

	@Formula("(select (case gnum_islock " +
			"when 0 then 'Unlocked' " +
			"else 'Locked' end))")
	private String lockStatus;

}
