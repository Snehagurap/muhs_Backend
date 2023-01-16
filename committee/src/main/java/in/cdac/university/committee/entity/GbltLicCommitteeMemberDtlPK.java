package in.cdac.university.committee.entity;

import java.io.Serializable;
import javax.persistence.*;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class GbltLicCommitteeMemberDtlPK implements Serializable{
	
	@Column(name="unum_lic_member_id") 
	private	Long unumLicMemberId;

	@Column(name="unum_isvalid") 
	private	Integer unumIsValid;

}
