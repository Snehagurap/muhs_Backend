package in.cdac.university.globalService.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@IdClass(GmstApplicantTypeMstPK.class)
@Entity
@Table(name="gmst_applicant_type_mst", schema = "university")
public class GmstApplicantTypeMst implements Serializable {
	@Id
	private Long unumApplicantTypeId;

	@Id
	private Integer unumIsvalid;

	@Temporal(TemporalType.DATE)
	@Column(name = "udt_eff_from")
	private Date udtEffFrom;

	@Temporal(TemporalType.DATE)
	@Column(name = "udt_eff_to")
	private Date udtEffTo;

	@Column(name = "udt_entry_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date udtEntryDate;

	@Column(name = "unum_entry_uid")
	private Long unumEntryUid;

	@Column(name = "ustr_applicant_type_code")
	private String ustrApplicantTypeCode;

	@Column(name = "ustr_applicant_type_fname")
	private String ustrApplicantTypeFname;

	@Column(name = "ustr_applicant_type_sname")
	private String ustrApplicantTypeSname;

	@Column(name = "ustr_description")
	private String ustrDescription;

}