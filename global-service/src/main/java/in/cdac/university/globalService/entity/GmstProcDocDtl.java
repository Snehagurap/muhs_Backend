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
@IdClass(GmstProcDocDtlPK.class)
@Table(name = "gmst_proc_doc_dtl", schema = "university")
public class GmstProcDocDtl implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private Long unumDocId;

    @Id
    private Long unumProcessId;

    @Id
    private Integer unumIsvalid;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "udt_eff_from")
    private Date udtEffFrom;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "udt_eff_to")
    private Date udtEffTo;

    @Column(name = "udt_entry_date")
    private Date udtEntryDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "udt_lst_mod_dt")
    private Date udtLstModDt;

    @Column(name = "unum_entry_uid")
    private Long unumEntryUid;

    @Column(name = "unum_lst_mod_uid")
    private Long unumLstModUid;

    @Column(name = "ustr_description")
    private String ustrDescription;

    @Column(name = "ustr_doc_fname")
    private String ustrDocFname;

    @Column(name = "ustr_doc_sname")
    private String ustrDocSname;

}