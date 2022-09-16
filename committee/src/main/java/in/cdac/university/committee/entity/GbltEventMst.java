package in.cdac.university.committee.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name="gblt_event_mst", schema = "ucom")
@IdClass(GbltEventMstPK.class)
public class GbltEventMst implements Serializable {

	@Id
	private Long unumEventid;

	@Id
	private Integer unumIsvalid;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="udt_entry_date", insertable = false)
	private Date udtEntryDate;

	@Temporal(TemporalType.DATE)
	@Column(name="udt_event_fromdt")
	private Date udtEventFromdt;

	@Temporal(TemporalType.DATE)
	@Column(name="udt_event_todt")
	private Date udtEventTodt;

	@Column(name="unum_college_id")
	private Integer unumCollegeId;

	@Column(name="unum_comid")
	private Long unumComid;

	@Column(name="unum_entry_uid")
	private Long unumEntryUid;

	@Column(name="unum_event_duration_days")
	private Integer unumEventDurationDays;

	@Column(name="unum_event_typeid")
	private Integer unumEventTypeid;

	@Column(name="unum_univ_id")
	private Integer unumUnivId;

	@Column(name="ustr_college_name")
	private String ustrCollegeName;

	@Column(name="ustr_event_description")
	private String ustrEventDescription;

	@Column(name="ustr_event_name")
	private String ustrEventName;

}