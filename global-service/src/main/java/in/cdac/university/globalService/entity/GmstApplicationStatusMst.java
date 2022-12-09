package in.cdac.university.globalService.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@IdClass(GmstApplicationStatusMstPK.class)
@Entity
@Table(name="gmst_application_status_mst", schema = "university")
public class GmstApplicationStatusMst {

    @Id
    private Long unumApplicationStatusId;

    @Id
    private Integer unumIsvalid;

    @Column(name="ustr_application_status_name")
    private String ustrApplicationStatusName;

    @Column(name="unum_univ_id")
    private Integer unumUnivId;

    @Column(name = "udt_entry_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date udtEntryDate;

    @Column(name = "unum_entry_uid")
    private Long unumEntryUid;

    @Column(name = "ustr_description")
    private String ustrDescription;
}
