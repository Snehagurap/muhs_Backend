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
public class LicCommitteeRuleSetDtlBean {

	
	private Long unumComRsDtlId;
	
	private Integer unumComrolsno;
	
	private Long unumComRsId;
	
	private Integer unumCtypeid;
	
	private Integer unumRoleCfacultyId;
	
	private Integer unumRoleStreamId;
	
	private Integer unumRoleIsExternal;
	
	private Integer unumStaffTypeId;
	
	private Integer unumRoleDepartmentId;
	
	private Integer unumRolePostId;
	
	private Integer unumRoleMinExpYears;
	
	private Integer unumRoleMaxExpYears;
	
	private Integer unumRoleMinYearlyOccurance;
	
	private Integer unumRoleMaxYearlyOccurance;
	
	private Integer unumVcNominatedFlag;
	
	private Integer unumMinApprovedExp;
	
	private Integer unumMaxApprovedExp;
	
	private Integer unumExcludeColZone;
	
	private Integer unumExcludeColDistrict;
	
	private Integer unumAdjacentDistrict;
	
	private String ustrComroleDescription;
	
	private Integer unumUnivId;
	
	private Date udtEntryDate;
	
	private Integer unumIsValid;

	private Integer unumRoleId;
	
	private Long unumEntryUid;
	
	private String UstrRoleName;
	
	private Integer  UnumNoOfMembers;
	
	private List<ComboBean> teacherCombo;

	private Long unumLicRsId;

	private Long unumLicRsDtlId;
}
