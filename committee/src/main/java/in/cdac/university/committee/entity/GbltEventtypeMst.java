package in.cdac.university.committee.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name="gblt_eventtype_mst", schema = "ucom")
@IdClass(GbltEventtypeMstPK.class)
public class GbltEventtypeMst implements Serializable {

	@Id
	private Long unumEventTypeid;

	@Id
	private Integer unumIsvalid;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="udt_entry_date")
	private Date udtEntryDate;

	@Column(name="unum_entry_uid")
	private Long unumEntryUid;

	@Column(name="unum_univ_id")
	private Integer unumUnivId;

	@Column(name="ustr_event_typedesc")
	private String ustrEventTypedesc;

	@Column(name="ustr_event_typename")
	private String ustrEventTypename;

}