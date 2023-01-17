package in.cdac.university.committee.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@ToString
@Table(name="gblt_liccommittee_mst", schema = "ucom")
@IdClass(GbltLicCommitteeMstPK.class)
public class GbltLicCommitteeMst {
	
	@Id
	@Column(name="unum_lic_id")
	private Long	unumLicId ;

	@Id
	@Column(name="unum_isvalid")  
	private Integer	unumIsValid ;

	@Column(name="ustr_lic_name")  
	private String	ustrLicName ;

	@Column(name="unum_com_rs_id")  
	private Long	unumComRsId ;

	@Column(name="udt_lic_create_date")  
	private Date	udtLicCreateDate ;

	@Column(name="unum_lic_cfaculty_id")  
	private Integer	unumLicCfacultyId ;

	@Column(name="unum_stream_id")  
	private Integer	unumStreamId ; 

	@Column(name="udt_lic_from_date")  
	private Date	udtLicFromDate ; 

	@Column(name="udt_lic_to_date")  
	private Date	udtLicToDate ;

	@Column(name="unum_login_required")  
	private Integer	unumLoginRequired ; 

	@Column(name="unum_no_of_members")  
	private Integer	unumNoOfMembers ;

	@Column(name="ustr_com_description")  
	private String	ustrComDescription ; 

	@Column(name="unum_univ_id")  
	private Integer	unumUnivId ;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="udt_entry_date")  
	private Date    udtEntryDate ; 

	@Column(name="unum_entry_uid")  
	private Integer	unumEntryUid ;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="udt_revalidation_date")  
	private Date	udtRevalidationDate ; 

	@Column(name="unum_is_formula_based")  
	private Integer	unumIsFormulaBased ;

	@Column(name="unum_ctypeid")  
	private Integer	unumCtypeId ;

	@Column(name="unum_sub_id")  
	private Integer	unumSubId ;

}
