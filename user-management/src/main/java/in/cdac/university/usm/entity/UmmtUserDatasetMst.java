package in.cdac.university.usm.entity;

import in.cdac.university.usm.util.annotations.ComboKey;
import in.cdac.university.usm.util.annotations.ComboValue;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@ToString
@IdClass(UmmtUserDatasetPK.class)
@Table(name = "ummt_user_dataset_mst", schema = "usm")
public class UmmtUserDatasetMst implements java.io.Serializable{


    @Id
    @Column(name = "gnum_userid", nullable = false)
    private Long gnumUserId;

    @Id
    @Column(name = "unum_univ_id", nullable = false)
    private Integer unumUnivId;

    @Id
    @Column(name = "gnum_dataset_id", nullable = false)
    private Integer gnumDatasetId;

    @Id
    @Column(name = "gstr_column_value", nullable = false, length = 100)
    @ComboKey
    private String gstrColumnValue;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "gdt_entry_date", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date gdtEntryDate;

    @Column(name = "gnum_isvalid", nullable = false)
    private Integer gnumIsvalid;

    @Column(name = "gnum_module_id", nullable = false)
    private Integer gnumModuleId;

    @Column(name = "gstr_display_value", length = 250)
    @ComboValue
    private String gstrDisplayValue;

    @Column(name = "gnum_is_default", nullable = false)
    private Integer gnumIsDefault;


}
