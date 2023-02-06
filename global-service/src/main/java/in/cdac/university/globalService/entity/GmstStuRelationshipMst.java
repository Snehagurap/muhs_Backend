package in.cdac.university.globalService.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "gmst_stu_relationship_mst", schema = "university")
public class GmstStuRelationshipMst implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "unum_relationship_id")
    private Integer unumRelationshipId;

    @Column(name = "ustr_rel_sname")
    private String ustrRelSname;

    @Column(name = "ustr_rel_fname")
    private String ustrRelFname;

    @Column(name = "unum_isvalid")
    private Integer unumIsvalid;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "udt_eff_from")
    private Date udtEffFrom;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "udt_eff_to")
    private Date udtEffTo;

    @Column(name = "unum_univ_id")
    private Integer unumUnivId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "udt_entry_date")
    private Date udtEntryDate;

    @Column(name = "unum_entry_uid")
    private Long unumEntryUid;

    @Column(name = "unum_lst_mod_uid")
    private Long unumLstModUid;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "udt_lst_mod_date")
    private Date udtLstModDt;
}
