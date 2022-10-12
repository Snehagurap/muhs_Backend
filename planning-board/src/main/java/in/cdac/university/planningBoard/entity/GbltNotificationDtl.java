package in.cdac.university.planningBoard.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@ToString
@IdClass(GbltNotificationDtlPK.class)
@Entity
@Table(name="gblt_notification_dtl", schema = "upb")
public class GbltNotificationDtl implements Serializable {

	@Id
	private Long unumNdtlId;

	@Id
	private Integer unumIsvalid;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="udt_entry_date")
	private Date udtEntryDate;

	@Column(name="unum_coursetype_id")
	private Integer unumCoursetypeId;

	@Column(name="unum_department_id")
	private Integer unumDepartmentId;

	@Column(name="unum_doc_id")
	private Integer unumDocId;

	@Column(name="unum_entry_uid")
	private Long unumEntryUid;

	@Column(name="unum_faculty_id")
	private Integer unumFacultyId;

	@Column(name="unum_notification_type_id")
	private Integer unumNotificationTypeId;

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

	@Column(name="ustr_ndtl_sno_description")
	private String ustrNdtlSnoDescription;
}