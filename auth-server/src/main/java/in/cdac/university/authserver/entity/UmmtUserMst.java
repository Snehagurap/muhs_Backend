package in.cdac.university.authserver.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "ummt_user_mst", schema = "usm")
public class UmmtUserMst implements Serializable {

    @EmbeddedId
    private UmmtUserMstId id;

    @Column(name = "gstr_username", unique = true, nullable = false, length = 100)
    private String gstrUsername;

    @Column(name = "unum_univ_id", nullable = false)
    private Integer gnumUniversityId;

    @Column(name = "gstr_password", nullable = false, length = 100)
    private String gstrPassword;

    @Column(name = "gstr_user_full_name", nullable = false, length = 500)
    private String gstrUserFullName;

    @Column(name = "gnum_user_cat_id", nullable = false)
    private Integer gnumUserCatId;

    @Temporal(TemporalType.DATE)
    @Column(name = "gdt_effect_date", nullable = false)
    private Date gdtEffectDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "gdt_expiry_date", nullable = false)
    private Date gdtExpiryDate;

    @Column(name = "gnum_entry_by", nullable = false)
    private Integer gnumEntryBy;

    @Temporal(TemporalType.DATE)
    @Column(name = "gdt_entry_date", nullable = false, insertable = false, updatable = false, columnDefinition = "timestamp default current_timestamp")
    private Date gdtEntryDate;

    @Column(name = "gnum_user_type_id")
    private Integer gnumUserTypeId;

    @Column(name = "gnum_designation", length = 500)
    private String gnumDesignation;

    @Column(name = "gnum_islock", nullable = false, precision = 1, insertable = false, columnDefinition = "numeric default 0")
    private Integer gnumIslock;

    @Column(name = "num_dist_id", precision = 5)
    private Integer numDistId;

    @Column(name = "gstr_email_id", length = 500)
    private String gstrEmail;

    @Column(name = "gnum_mobile_number", length = 25)
    private String gnumMobileNumber;

    @Column(name = "gnum_statecode", nullable = false)
    private Integer gnumStatecode;

    @Column(name = "gnum_role_id", nullable = false)
    private Integer gnumRoleId;

    @Column(name = "gnum_default_data_id")
    private Integer gnumDefaultDataId;

    @Column(name = "gnum_default_data_value", length = 500)
    private String gnumDefaultDataValue;

    @Column(name = "gstr_aadhar_number", length = 50)
    private String gstrAadharNumber;

    @Column(name = "gnum_dataset_id")
    private Integer gnumDatasetId;
}

