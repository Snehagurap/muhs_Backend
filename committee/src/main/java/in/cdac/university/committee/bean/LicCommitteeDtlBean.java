package in.cdac.university.committee.bean;

import java.util.Date;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LicCommitteeDtlBean {
	
	private	Long unumLicMemberId;
	
	private	Long unumLicId ;
	
	private	Long unumLicRsId ;
	
	private	Long unumLicRsDtlId ;
	
	private	Integer unumLicMemberSno ;
	
	private	Integer unumLicRoleId ;
	
	private	Integer unumLicPref1Empid ;
	
	private	Integer unumLicPref2Empid ;
	
	private	Integer unumLicPref3Empid ;
	
	private	Integer unumLicPref4Empid ;
	
	private	Integer unumLicPref5Empid ;
	
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

}
