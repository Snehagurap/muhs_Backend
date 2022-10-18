package in.cdac.university.globalService.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@IdClass(GmstConfigMastertemplateTemplatedtlPK.class)
@Entity
@Table(name="gmst_config_mastertemplate_templatedtl", schema = "university")
public class GmstConfigMastertemplateTemplatedtl implements Serializable {

	@Id
	private Long unumMtempledtlId;

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

	@Column(name="unum_mtemple_id")
	private Long unumMtempleId;

	@Column(name="unum_temple_id")
	private Long unumTempleId;

	@Column(name="unum_univ_id")
	private Integer unumUnivId;

	@Column(name="ustr_mtempledtl_description")
	private String ustrMtempledtlDescription;

}