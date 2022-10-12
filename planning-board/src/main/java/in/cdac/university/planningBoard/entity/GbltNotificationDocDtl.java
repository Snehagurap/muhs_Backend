package in.cdac.university.planningBoard.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@IdClass(GbltNotificationDocDtlPK.class)
@Entity
@Table(name="gblt_notification_doc_dtl", schema = "upb")
public class GbltNotificationDocDtl implements Serializable {

	@Id
	private Long unumNdocid;

	@Id
	private Integer unumIsvalid;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="udt_entry_date")
	private Date udtEntryDate;

	@Column(name="unum_doctype_id")
	private Integer unumDoctypeId;

	@Column(name="unum_doctype_name")
	private String unumDoctypeName;

	@Column(name="unum_entry_uid")
	private Long unumEntryUid;

	@Column(name="unum_nid")
	private Long unumNid;

	@Column(name="unum_s_no")
	private Integer unumSNo;

	@Column(name="unum_sno_displayorder")
	private Integer unumSnoDisplayorder;

	@Column(name="unum_univ_id")
	private Integer unumUnivId;

	@Column(name="ustr_description")
	private String ustrDescription;

	@Column(name="ustr_file_path")
	private String ustrFilePath;

}