package in.cdac.university.globalService.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode
public class GmstLanguageMstPK implements Serializable {

	@Column(name="unum_lang_id")
	private Integer unumLangId;

	@Column(name="unum_isvalid")
	private Integer unumIsvalid;

}