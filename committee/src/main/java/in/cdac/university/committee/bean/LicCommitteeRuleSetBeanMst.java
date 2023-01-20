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
	@ListColumn(omit = true)
	private Long unumComRsId;
	
	private Integer unumComRsCatId;

	private Integer unumComdurationDays;

	private Integer udtComStartDate;

	private Date udtComEndDate;
	
	@ListColumn(name = "Ruleset Name", order= 3)
	@ComboValue
	private String ustrComRsName;
	
	@ListColumn(name = "No Of Members", order= 4)
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

	@ListColumn(name = "Category Name", order= 2)
	private String ustrCatName="LIC1 Committee";
	List<LicCommitteeRuleSetDtlBean> committeeRuleList;

}
