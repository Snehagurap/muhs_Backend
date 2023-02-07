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
@Table(name = "gmst_salutation_mst", schema = "university")
public class GmstSalutationMst implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "unum_salutation_id")
    private Integer unumSalutationId;

    @Column(name = "unum_isvalid")
    private Integer unumIsvalid;

    @Column(name = "ustr_salutation_sname")
    private String ustrSalutationSname;

    @Column(name = "ustr_salutation_fname")
    private String ustrSalutationFname;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "udt_eff_frm")
    private Date udtEffFrom;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "udt_eff_to")
    private Date udtEffTo;

    @Column(name = "ustr_description")
    private String ustrDescription;

    @Column(name = "unum_univ_id")
    private Integer unumUnivId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "udt_entry_date")
    private Date udtEntryDate;

    @Column(name = "unum_entry_uid")
    private Long unumEntryUid;

    @Column(name = "unum_lst_mod_uid")
    private Long unumLstModUid;

    @Column(name = "udt_lst_mod_date")
    private Date udtLstModDt;
}
