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
@IdClass(GmstAdjacentDistrictDtlPK.class)
@Table(name="gmst_adjacent_district_dtl", schema = "university")
public class GmstAdjacentDistrictDtl implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;

	@Id
	private Integer numDistId;

	@Id
	private Integer numAdjacentDistId;

	@Id
	private Integer unumIsvalid;

	@Column(name="num_adjacency_priority")
	private Integer numAdjacencyPriority;

	@Temporal(TemporalType.DATE)
	@Column(name="udt_eff_from")
	private Date udtEffFrom;

	@Temporal(TemporalType.DATE)
	@Column(name="udt_eff_to")
	private Date udtEffTo;

	@Column(name="udt_entry_date")
	private Date udtEntryDate;

	@Temporal(TemporalType.DATE)
	@Column(name="udt_lst_mod_date")
	private Date udtLstModDate;

	@Column(name="unum_entry_uid")
	private Long unumEntryUid;

	@Column(name="unum_lst_mod_uid")
	private Long unumLstModUid;

	@Column(name="ustr_description")
	private String ustrDescription;
}