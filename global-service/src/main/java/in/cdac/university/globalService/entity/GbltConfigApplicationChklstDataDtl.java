package in.cdac.university.globalService.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@IdClass(GbltConfigApplicationChklstDataDtlPK.class)
@Entity
@ToString
@Table(name="gblt_config_application_chklst_data_dtl", schema = "templedata")
public class GbltConfigApplicationChklstDataDtl implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;

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
	private Long unumChecklistId;

	@Id
	private Long unumMtempleId;

	@Id
	private Long unumMtempledtlId;

	@Id
	private Long unumTempleId;

	@Id
	private Long unumTempledtlId;

	@Id
	private Long unumTempleHeadId;

	@Id
	private Long unumTempleCompItemId;

	@Id
	private Long unumTempleCompId;

	@Id
	private Long unumTempleItemId;

	@Id
	private Integer unumIsvalid;

	@Temporal(TemporalType.DATE)
	@Column(name="udt_eff_from")
	private Date udtEffFrom;

	@Temporal(TemporalType.DATE)
	@Column(name="udt_eff_to")
	private Date udtEffTo;

	@Temporal(TemporalType.DATE)
	@Column(name="udt_entry_date")
	private Date udtEntryDate;

	@Temporal(TemporalType.DATE)
	@Column(name="udt_lst_mod_date")
	private Date udtLstModDate;

	@Column(name="unum_application_status_sno")
	private Long unumApplicationStatusSno;

	@Column(name="unum_approval_statusid")
	private Integer unumApprovalStatusid;

	@Column(name="unum_checklist_item_orderno")
	private Double unumChecklistItemOrderno;

	@Column(name="unum_component_order_no")
	private Double unumComponentOrderNo;

	@Column(name="unum_entry_uid")
	private Long unumEntryUid;

	@Column(name="unum_header_order_no")
	private Double unumHeaderOrderNo;

	@Column(name="unum_item_value")
	private Long unumItemValue;

	@Column(name="unum_lst_mod_uid")
	private Long unumLstModUid;

	@Column(name="unum_ofc_scrutiny_isitemverified")
	private Integer unumOfcScrutinyIsitemverified;

	@Column(name="unum_stream_id")
	private Long unumStreamId;

	@Column(name="unum_univ_id")
	private Integer unumUnivId;

	@Column(name="ustr_checklist_item_name")
	private String ustrChecklistItemName;

	@Column(name="ustr_checklist_name")
	private String ustrChecklistName;

	@Column(name="ustr_description")
	private String ustrDescription;

	@Column(name="ustr_ofc_scrutiny_remarks")
	private String ustrOfcScrutinyRemarks;

	@Column(name="ustr_temple_item_value")
	private String ustrTempleItemValue;

	@Column(name="unum_temple_subhead_id")
	private Long unumTempleSubheadId;
}