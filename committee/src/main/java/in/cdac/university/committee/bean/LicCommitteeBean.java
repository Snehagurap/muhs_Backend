package in.cdac.university.committee.bean;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import in.cdac.university.committee.util.annotations.ListColumn;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LicCommitteeBean {
	
	@NotNull(message = "Lic Id is mandatory")
	@ListColumn(omit = true)
	private Long	unumLicId ;
	
	@ListColumn(name = "Lic Committee Name")
	private String	ustrLicName ;
	
	private Long	unumComRsId ;
	
	private Date	udtLicCreateDate ;
	
	private Long	unumLicCfacultyId ;
	
	private Long	unumStreamId ; 
	
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
	
	@ListColumn(name = "Lic Committee RuleSet Name")
	private String	ustrLicRsName ;    
	
	@ListColumn(name = "Faculty Name")
	private String	ustrLicCfacultyName ; 
	
	@ListColumn(name = "Stream Name")
	private String	ustrStreamName ; 
}
