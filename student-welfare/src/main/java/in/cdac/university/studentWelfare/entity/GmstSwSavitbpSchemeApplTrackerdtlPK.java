package in.cdac.university.studentWelfare.entity;

import java.io.Serializable;

import javax.persistence.Column;

import org.springframework.data.jpa.repository.JpaRepository;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class GmstSwSavitbpSchemeApplTrackerdtlPK implements Serializable{
	
	@Column(name="unum_savitbp_applicationid")       
	private Long 	unumSavitbpApplicationid;


	@Column(name="unum_isvalid")
	private Integer unumIsvalid;
}
