package in.cdac.university.studentWelfare.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
public class CheckListBean {
	
//	private Long unumTempleId;
//	private Integer unumChecklistId;
//	private String ustrChecklistName;
	private List<HeadClassCheckList> headClass;

	private List<CheckListItems> checkListItems;			// Used for displaying checklist items on application form



	private Long unumApplicationId;

	private Long unumApplicationdtlId;

	private Long unumApplicantId;

	private Long unumNid;

	private Long unumNdtlId;

	private Long unumChecklistId;

	private Long unumMtempleId;

	private Long unumMtempledtlId;

	private Long unumTempleId;

	private Long unumTempledtlId;

	private Long unumTempleHeadId;

	private Long unumTempleSubheadId;

	private Long unumTempleCompItemId;

	private Long unumTempleCompId;

	private Long unumTempleItemId;

	private Integer unumIsvalid;

	private Date udtEffFrom;

	private Date udtEffTo;

	private Date udtEntryDate;

	private Date udtLstModDate;

	private Long unumApplicationStatusSno;

	private Integer unumApprovalStatusid;

	private Double unumChecklistItemOrderno;

	private Double unumComponentOrderNo;

	private Long unumEntryUid;

	private Double unumHeaderOrderNo;

	private Long unumItemValue;

	private Long unumLstModUid;

	private Integer unumOfcScrutinyIsitemverified;

	private Long unumStreamId;

	private Integer unumUnivId;

	private String ustrChecklistItemName;

	private String ustrChecklistName;

	private String ustrDescription;

	private String ustrOfcScrutinyRemarks;

	private String ustrTempleItemValue;


}
