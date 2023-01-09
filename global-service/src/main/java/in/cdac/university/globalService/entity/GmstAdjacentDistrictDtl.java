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
@Table(name = "gmst_adjacent_district_dtl", schema = "university")
@IdClass(GmstAdjacentDistrictDtlPK.class)
public class GmstAdjacentDistrictDtl implements Serializable {

    @Id
    @Column(name = "num_dist_id", nullable = false)
    private Integer numDistId;

    @Id
    @Column(name = "num_adjacent_dist_id", nullable = false)
    private Integer numAdjacentDistId;

    @Column(name = "num_adjacency_priority")
    private Integer numAdjacencyPriority;

    @Column(name = "ustr_description" , length = 200)
    private String ustrDescription;

    @Column(name = "udt_eff_from", nullable = false, columnDefinition="timestamp without time zone default current_timestamp")
    @Temporal(TemporalType.DATE)
    private Date udtEffFrom;

    @Column(name = "udt_eff_to", columnDefinition="timestamp without time zone default current_timestamp")
    @Temporal(TemporalType.DATE)
    private Date udtEffTo;

    @Column(name = "udt_entry_date", nullable = false, columnDefinition="timestamp without time zone default current_timestamp")
    @Temporal(TemporalType.DATE)
    private Date udtEntryDate;

    @Id
    @Column(name = "unum_isvalid", nullable = false, columnDefinition = "numeric default 1")
    private Integer unumIsvalid;

    @Column(name  = "unum_entry_uid",  columnDefinition = "numeric default 1")
    private Long unumEntryUid;

    @Column(name = "unum_lst_mod_uid", columnDefinition = "numeric default 1")
    private Integer unumLstModUid;

    @Column(name = "udt_lst_mod_date")
    @Temporal(TemporalType.DATE)
    private Date udtLstModDate;

    @Column(name = "gnum_statecode")
    private Integer gnumStatecode;

    @Formula("(select u.str_dist_name from usm.gblt_district_mst_imsc u where u.num_dist_id = num_dist_id)")
    private String strDistName;



}
