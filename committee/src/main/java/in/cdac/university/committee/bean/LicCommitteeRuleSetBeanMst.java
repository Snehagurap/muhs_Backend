package in.cdac.university.committee.bean;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import in.cdac.university.committee.util.annotations.ComboKey;
import in.cdac.university.committee.util.annotations.ComboValue;
import in.cdac.university.committee.util.annotations.ListColumn;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LicCommitteeRuleSetBeanMst {
	
	
	@ComboKey
	@NotNull(message = "Lic Committee Master ID is mandatory")
	private Long unumComRsId;
	
	@ListColumn(name = "Category Ruleset Id")
	private Integer unumComRsCatId;

	private Integer unumComdurationDays;

	private Integer udtComStartDate;

	private Date udtComEndDate;
	
	@ListColumn(name = "Ruleset Name")
	@ComboValue
	private String ustrComRsName;
	
	@ListColumn(name = "No Of Members")
	private Integer unumNoOfMembers;

	private Integer unumComCfacultyId;

	private String ustrComForyear;

	private Integer ustrComFormonth;
	
	private String ustrComDescription;

	private Integer unumUnivId;

	private Date udtEntryDate;

	@NotNull(message = "Lic Committee Status is mandatory")
	private Integer unumIsValid;

	private Long unumEntryUid;

	private Date udtRevalidationDate;

	private Integer unumIsFormulaBased;

	private Integer unumIsLongRestricted;

	private Integer unumStreamId;

	@ListColumn(name = "Ctype ids")
	private String ustrCtypeids;

	List<LicCommitteeRuleSetDtlBean> committeeRuleList;

}
