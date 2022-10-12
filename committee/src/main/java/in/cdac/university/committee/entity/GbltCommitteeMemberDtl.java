package in.cdac.university.committee.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name="gblt_committee_member_dtl", schema = "ucom")
@IdClass(GbltCommitteeMemberDtlPK.class)
public class GbltCommitteeMemberDtl implements Serializable {

	@Id
	private Long unumComMemberId;

	@Id
	private Integer unumIsvalid;

	@Temporal(TemporalType.DATE)
	@Column(name="udt_entry_date")
	private Date udtEntryDate;

	@Column(name="unum_com_id")
	private Long unumComId;

	@Column(name="unum_comroleid")
	private Long unumComroleid;

	@Column(name="unum_entry_uid")
	private Long unumEntryUid;

	@Column(name="unum_eventid")
	private Long unumEventid;

	@Column(name="unum_member_sno")
	private Integer unumMemberSno;

	@Column(name="unum_preference1_empid")
	private Long unumPreference1Empid;

	@Column(name="unum_preference1_empname")
	private String unumPreference1Empname;

	@Column(name="unum_preference2_empid")
	private Long unumPreference2Empid;

	@Column(name="unum_preference2_empname")
	private String unumPreference2Empname;

	@Column(name="unum_preference3_empid")
	private Long unumPreference3Empid;

	@Column(name="unum_preference3_empname")
	private String unumPreference3Empname;

	@Column(name="unum_preference4_empid")
	private Long unumPreference4Empid;

	@Column(name="unum_preference4_empname")
	private String unumPreference4Empname;

	@Column(name="unum_preference5_empid")
	private Long unumPreference5Empid;

	@Column(name="unum_preference5_empname")
	private String unumPreference5Empname;

	@Column(name="unum_role_id")
	private Integer unumRoleId;

	@Column(name="unum_univ_id")
	private Integer unumUnivId;

	@Column(name="ustr_com_member_description")
	private String ustrComMemberDescription;

}