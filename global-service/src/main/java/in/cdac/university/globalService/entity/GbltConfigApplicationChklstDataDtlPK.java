package in.cdac.university.globalService.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode
public class GbltConfigApplicationChklstDataDtlPK implements Serializable {
	//default serial version id, required for serializable classes.
	@Serial
	private static final long serialVersionUID = 1L;

	@Column(name="unum_application_id")
	private Long unumApplicationId;

	@Column(name="unum_applicationdtl_id")
	private Long unumApplicationdtlId;

	@Column(name="unum_applicant_id")
	private Long unumApplicantId;

	@Column(name="unum_nid")
	private Long unumNid;

	@Column(name="unum_ndtl_id")
	private Long unumNdtlId;

	@Column(name="unum_checklist_id")
	private Long unumChecklistId;

	@Column(name="unum_mtemple_id")
	private Long unumMtempleId;

	@Column(name="unum_mtempledtl_id")
	private Long unumMtempledtlId;

	@Column(name="unum_temple_id")
	private Long unumTempleId;

	@Column(name="unum_templedtl_id")
	private Long unumTempledtlId;

	@Column(name="unum_temple_head_id")
	private Long unumTempleHeadId;

	@Column(name="unum_temple_comp_item_id")
	private Long unumTempleCompItemId;

	@Column(name="unum_temple_comp_id")
	private Long unumTempleCompId;

	@Column(name="unum_temple_item_id")
	private Long unumTempleItemId;

	@Column(name="unum_isvalid")
	private Integer unumIsvalid;

}