package in.cdac.university.committee.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@IdClass(GbltCommitteeRulesetDtlPK.class)
@Entity
@Table(name="gblt_committee_ruleset_dtl", schema = "ucom")
public class GbltCommitteeRulesetDtl implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;

	@Id
	private Long unumComRsDtlId;

	@Id
	private Integer unumIsvalid;

	@Column(name="udt_entry_date")
	private Date udtEntryDate;

	@Column(name="unum_com_rs_id")
	private Long unumComRsId;

	@Column(name="unum_comrolsno")
	private Integer unumComrolsno;

	@Column(name="unum_entry_uid")
	private Long unumEntryUid;

	@Column(name="unum_role_cfaculty_id")
	private Integer unumRoleCfacultyId;

	@Column(name="unum_role_department_id")
	private Integer unumRoleDepartmentId;

	@Column(name="unum_role_id")
	private Integer unumRoleId;

	@Column(name="unum_role_post_id")
	private Integer unumRolePostId;

	@Column(name="unum_univ_id")
	private Integer unumUnivId;

	@Column(name="unum_vc_nominated_flag")
	private Integer unumVcNominatedFlag;

	@Column(name="ustr_comrole_description")
	private String ustrComroleDescription;

	@Column(name="udt_eff_from")
	private Date udtEffFrom;

	@Column(name="udt_eff_to")
	private Date udtEffTo;

}