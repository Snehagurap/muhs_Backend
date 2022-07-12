package in.cdac.university.authserver.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

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

    @Column(name = "gstr_username", nullable = false, length = 25)
    private String gstrUsername;

    @Column(name = "gstr_password", nullable = false, length = 100)
    private String gstrPassword;

    @Column(name = "gnum_university_id", nullable = false, precision = 5)
    private Integer gnumUniversityId;

    @Column(name = "gnum_user_cat_id", nullable = false, precision = 5)
    private Integer gnumUserCatId;

    @Temporal(TemporalType.DATE)
    @Column(name = "gdt_effect_date", nullable = false, length = 100)
    private Date gdtEffectDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "gdt_expiry_date", nullable = false, length = 15)
    private Date gdtExpiryDate;

    @Column(name = "gnum_entry_by", nullable = false, precision = 8)
    private Integer gnumEntryBy;

    @Temporal(TemporalType.DATE)
    @Column(name = "gdt_entry_date", nullable = false, length = 29)
    private Date gdtEntryDate;

    @Column(name = "gnum_isvalid", nullable = false, precision = 1)
    private Integer gnumIsvalid;

    @Column(name = "gnum_user_type_id", precision = 5)
    private Integer gnumUserTypeId;

    @Column(name = "gnum_userlevel", precision = 1)
    private Integer gnumUserlevel;

    @Column(name = "gnum_designation", length = 50)
    private String gnumDesignation;

    @Column(name = "gnum_islock", nullable = false, precision = 1)
    private Integer gnumIslock;

    @Column(name = "num_dist_id", precision = 5)
    private Integer numDistId;

    @Column(name = "gstr_email_id", length = 60)
    private String gstrEmail;

    @Column(name = "gnum_mobile_number", length = 25)
    private String gnumMobileNumber;

    @Column(name = "gnum_statecode", nullable = false, precision = 2)
    private Integer gnumStatecode;

    @Column(name = "gnum_role_id", nullable = false, precision = 4)
    private Integer gnumRoleId;

    @Column(name = "gnum_default_data_id", nullable = false, precision = 25)
    private Integer gnumDefaultDataId;

    @Column(name = "gnum_default_data_value", length = 250)
    private String gnumDefaultDataValue;

    @Column(name = "gstr_aadhar_number", length = 50)
    private String gstrAadharNumber;

    @Column(name = "gnum_dataset_id", nullable = false, precision = 3)
    private Integer gnumDatasetId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UmmtUserMst that = (UmmtUserMst) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

