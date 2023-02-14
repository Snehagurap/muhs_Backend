package in.cdac.university.globalService.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@IdClass(CmstUnivCollegeEventMstPK.class)
@Table(name = "cmst_univ_college_event_mst", schema = "university")
public class CmstUnivCollegeEventMst implements Serializable {

    @Id
    @Column(name = "unum_col_event_id")
    private Integer unumColEventId;

    @Column(name = "ustr_col_event_sname")
    private String ustrColEventSname;

    @Column(name = "ustr_col_event_fname")
    private String ustrColEventFname;

    @Column(name = "ustr_description")
    private String usteDescription;

    @Column(name = "unum_univ_id")
    private Integer unumUnivId;

    @Temporal(TemporalType.DATE)
    @Column(name = "udt_entry_date")
    private Date udtEntryDate;

    @Id
    @Column(name = "unum_isvalid")
    private Integer unumIsvalid;

    @Column(name = "unum_entry_uid")
    private Long unumEntryId;

    @Column(name = "unum_lst_mod_uid")
    private Long unumLstModUid;

    @Temporal(TemporalType.DATE)
    @Column(name = "udt_lst_mod_date")
    private Date udtLstModDate;

}
