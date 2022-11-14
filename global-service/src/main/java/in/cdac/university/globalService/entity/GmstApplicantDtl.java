package in.cdac.university.globalService.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@IdClass(GmstApplicantDtlPK.class)
@Entity
@Table(name="gmst_applicant_dtl", schema = "university")
public class GmstApplicantDtl implements Serializable {
	@Id
	private Long unumApplicantDocId;

	@Id
	private Integer unumIsvalid;

	@Temporal(TemporalType.DATE)
	@Column(name="udt_eff_from")
	private Date udtEffFrom;

	@Temporal(TemporalType.DATE)
	@Column(name="udt_eff_to")
	private Date udtEffTo;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="udt_entry_date")
	private Date udtEntryDate;

	@Column(name="unum_applicant_id")
	private Long unumApplicantId;

	@Column(name="unum_doc_id")
	private Integer unumDocId;

	@Column(name="unum_doc_is_verified")
	private Integer unumDocIsVerified;

	@Column(name="unum_entry_uid")
	private Long unumEntryUid;

	@Column(name="ustr_description")
	private String ustrDescription;

	@Column(name="ustr_doc_name")
	private String ustrDocName;

	@Column(name="ustr_doc_path")
	private String ustrDocPath;
}