package in.cdac.university.globalService.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@IdClass(GmstConfigTemplateMstPK.class)
@Entity
@Table(name="gmst_config_template_mst", schema = "university")
public class GmstConfigTemplateMst implements Serializable {

	@Id
	private Long unumTempleId;

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

	@Column(name="unum_entry_uid")
	private Long unumEntryUid;

	@Column(name="unum_faculty_id")
	private Integer unumFacultyId;

	@Column(name="unum_template_for_yyyy")
	private Integer unumTemplateForYyyy;

	@Column(name="unum_template_for_yyyymm")
	private Integer unumTemplateForYyyymm;

	@Column(name="unum_template_type")
	private Integer unumTemplateType;

	@Column(name="unum_univ_id")
	private Integer unumUnivId;

	@Column(name="ustr_temp_description")
	private String ustrTempDescription;

	@Column(name="ustr_temple_code")
	private String ustrTempleCode;

	@Column(name="ustr_temple_name")
	private String ustrTempleName;
	
	@Column(name="unum_ctype_id")
	private Integer unumCtypeId;

}