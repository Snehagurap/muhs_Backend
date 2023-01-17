package in.cdac.university.committee.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;

public class GbltLicCommitteeMstPK implements Serializable {
	
	@Column(name="unum_lic_id")
	private Long	unumLicId ;

	@Column(name="unum_isvalid")  
	private Integer	unumIsValid ;

}
