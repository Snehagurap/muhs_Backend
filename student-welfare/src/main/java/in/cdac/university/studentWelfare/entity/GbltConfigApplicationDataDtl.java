package in.cdac.university.studentWelfare.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Entity
@IdClass(GbltConfigApplicationDataDtlPK.class)
@Table(name="gblt_config_application_data_dtl", schema = "swtempledata")
public class GbltConfigApplicationDataDtl implements Serializable {
	@Id
	private Long unumApplicationId;

	@Id
	private Long unumApplicationdtlId;

	@Id
	private Long unumApplicantId;

	@Id
	private Long unumNid;

	@Id
	private Long unumNdtlId;

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

	@Column(name="unum_item_value")
	private Long unumItemValue;

	@Column(name="unum_mtemple_id")
	private Long unumMtempleId;

	@Column(name="unum_mtempledtl_id")
	private Long unumMtempledtlId;

	@Column(name="unum_temple_comp_item_id")
	private Long unumTempleCompItemId;

	@Column(name="unum_temple_comp_id")
	private Long unumTempleCompId;

	@Column(name="unum_temple_head_id")
	private Long unumTempleHeadId;

	@Column(name="unum_temple_id")
	private Long unumTempleId;

	@Column(name="unum_temple_item_id")
	private Long unumTempleItemId;

	@Column(name="unum_temple_subhead_id")
	private Long unumTempleSubheadId;

	@Column(name="unum_templedtl_id")
	private Long unumTempledtlId;

	@Column(name="unum_univ_id")
	private Integer unumUnivId;

	@Column(name="ustr_description")
	private String ustrDescription;

	@Column(name="ustr_item_value")
	private String ustrItemValue;

	@Column(name="ustr_temple_item_value")
	private String ustrTempleItemValue;
}