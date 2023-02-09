package in.cdac.university.studentWelfare.bean;

import java.util.Date;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SchemeMstBean {
	private Long 	unumSchemeId;
	
	private String  ustrSchemeCode;
	
	private String  ustrSchemeSname;

	private String  ustrSchemeFname;

	private Integer unumSchemeTypeid;

	private Integer unumSchemePurposeid;

	private Integer unumMaxBeneficiariesUniversity;

	private Integer unumMaxBeneficiariesPercolege;

	private Integer unumMaxAmountUniversity;

	private Integer unumMaxAmountPercolege;

	private Integer unumMaxAmountPerbeneficiary;

	private String  ustrEligibleCoursetypeids;

	private Integer unumCoursetypeLimittypeFlag;

	private Integer unumUgLimitUniversity;

	private Integer unumPgLimitUniversity;

	private Integer unumInternLimitUniversity;

	private Integer unumOmsLimitUniversity;

	private Integer ustrElegibleFaculties;

	private Integer ustrElegibleStreams;

	private Integer unumApplicationFeesRequired;

	private Integer unumGenderSpecific;

	private Integer unumStudentType;

	private Integer unumBeneficiaryMaxIncome;

	private Integer unumIsMedicalRelated;

	private Long	unumFacultyId;

	private Long 	unumStreamId;

	private Long 	unumCourseId;

	private Integer unumMinColIntakeNotinuse;

	private Integer unumMaxColIntakeNotinuse;

	private String  ustrDescription;

	private Date 	udtEffFrom;

	private Date 	udtEffTo;

	private Integer unumUnivId;

	private Long 	udtEntryDate;

	private Integer unumIsvalid;

	private Integer unumEntryUid;

	private Integer unumLstModUid;

	private Date 	udtLstModDt;

	private Integer unumApprovalStatusid;

	private Integer unumInternEligibleFlag;

	private Integer unumNriEligibleFlag;

	private Integer unumOmsEligibleFlag;

	private Integer unumStipendEarnerEligibleFlag;

	private Integer unumManagementquotaEligibleFlag;

	private Integer unumSchemeFor;
	
	private Integer unumPaymentTo;

}
