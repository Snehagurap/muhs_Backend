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
public class LicCommitteeBean {
	
	private Long	unumLicId ;
	
	private String	ustrLicName ;
	
	private Long	unumComRsId ;
	
	private Date	udtLicCreateDate ;
	
	private Integer	unumLicCfacultyId ;
	
	private Integer	unumStreamId ; 
	
	private Date	udtLicFromDate ; 
	
	private Date	udtLicToDate ;
	
	private Integer	unumLoginRequired ; 
	
	private Integer	unumNoOfMembers ;
	
	private String	ustrComDescription ; 
	
	private Integer	unumUnivId ;
	
	private Date    udtEntryDate ; 
	
	private Integer	unumIsValid  ;
	
	private Long	unumEntryUid ;
	
	private Date	udtRevalidationDate ; 
	
	private Integer	unumIsFormulaBased ;
	
	private Integer	unumCtypeId ;
	
	private Integer	unumSubId ;
	
	private List<LicCommitteeDtlBean> licCommitteeDtlBean;
}
