package in.cdac.university.committee.bean;

import java.util.Date;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LicCommitteeDtlBean {
	
	@NotNull(message = "Lic Member Id is mandatory")
	private	Long unumLicMemberId;
	
	private	Long unumLicId ;
	
	@NotNull(message = "Rule Set Id is mandatory")
	private	Long unumLicRsId ;
	
	private	Long unumLicRsDtlId ;
	
	private	Integer unumLicMemberSno ;
	
	private	Integer unumLicRoleId ;
	
	private	Long unumLicPref1Empid ;
	
	private	Long unumLicPref2Empid ;
	
	private	Long unumLicPref3Empid ;
	
	private	Long unumLicPref4Empid ;
	
	private	Long unumLicPref5Empid ;
	
	private	String unumLicPref1Empname;
	
	private	String unumLicPref2Empname;
	
	private	String unumLicPref3Empname;
	
	private	String unumLicPref4Empname;
	
	private	String unumLicPref5Empname;
	
	private	String ustrLicDescription;
	
	private	Integer unumUnivId ;
	
	private	Date udtEntryDate;
	
	private	Integer unumIsValid;
	
	private	Long unumEntryUid;
	
	private	Date udtRevalidationDate ;
	
	private	Integer unumIsFormulaBased;
	
	private	Integer unumLoginId;
	
	private	String ustrPass  ;
	
	private	Integer unumCtypeid;     

	private String roleName;
}
