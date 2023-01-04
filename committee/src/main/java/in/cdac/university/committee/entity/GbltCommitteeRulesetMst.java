package in.cdac.university.committee.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@IdClass(GbltCommitteeRulesetMstPK.class)
@Entity
@Table(name="gblt_committee_ruleset_mst", schema = "ucom")
public class GbltCommitteeRulesetMst implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;

	@Id
	private Long unumComRsId;

	@Id
	private Integer unumIsvalid;

	@Column(name="udt_entry_date")
	private Date udtEntryDate;

	@Column(name="unum_com_rs_cat_id")
	private Integer unumComRsCatId;

	@Column(name="unum_entry_uid")
	private Long unumEntryUid;

	@Column(name="unum_no_of_members")
	private Integer unumNoOfMembers;

	@Column(name="unum_univ_id")
	private Integer unumUnivId;

	@Column(name="ustr_com_description")
	private String ustrComDescription;

	@Column(name="ustr_com_rs_name")
	private String ustrComRsName;

	@Column(name="udt_eff_from")
	private Date udtEffFrom;

	@Column(name="udt_eff_to")
	private Date udtEffTo;

}