package in.cdac.university.globalService.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@IdClass(GmstConfigMastertemplateMstPK.class)
@Entity
@Table(name="gmst_config_mastertemplate_mst", schema = "university")
public class GmstConfigMastertemplateMst implements Serializable {

	@Id
	private Long unumMtempleId;

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

	@Column(name="unum_ctype_id")
	private Integer unumCtypeId;

	@Column(name="unum_entry_uid")
	private Long unumEntryUid;

	@Column(name="unum_faculty_id")
	private Integer unumFacultyId;

	@Column(name="unum_mtemplate_for_yyyy")
	private Integer unumMtemplateForYyyy;

	@Column(name="unum_mtemplate_for_yyyymm")
	private Integer unumMtemplateForYyyymm;

	@Column(name="unum_mtemplate_type")
	private Integer unumMtemplateType;

	@Column(name="unum_univ_id")
	private Integer unumUnivId;

	@Column(name="ustr_mtemp_description")
	private String ustrMtempDescription;

	@Column(name="ustr_mtemple_code")
	private String ustrMtempleCode;

	@Column(name="ustr_mtemple_name")
	private String ustrMtempleName;

}