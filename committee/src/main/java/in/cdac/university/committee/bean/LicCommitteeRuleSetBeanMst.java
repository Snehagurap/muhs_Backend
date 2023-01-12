package in.cdac.university.committee.bean;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LicCommitteeRuleSetBeanMst {

	@NotNull(message = "Lic Committee Master ID is mandatory")
	private Integer unumComRsId;

	private Integer unumComRsCatId;

	private Integer unumComdurationDays;

	private Integer udtComStartDate;

	private Date udtComEndDate;

	private String ustrComRsName;

	private Integer unumNoOfMembers;

	private Integer unumComCfacultyId;

	private String ustrComForyear;

	private Integer ustrComFormonth;
	
	private String ustrComDescription;

	private Integer unumUnivId;

	private Date udtEntryDate;// to check

	private Integer unumIsValid;

	private Long unumEntryUid;

	private Date udtRevalidationDate;//to check

	private Integer unumIsFormulaBased;

	private Integer unumIsLongRestricted;

	private Integer unumStreamId;

	private String ustrCtypeids;

	List<LicCommitteeRuleSetDtlBean> committeeRuleList;

}
