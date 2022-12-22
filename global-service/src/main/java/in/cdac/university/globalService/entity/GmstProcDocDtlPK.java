package in.cdac.university.globalService.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode
public class GmstProcDocDtlPK implements Serializable {
	@Column(name="unum_doc_id")
	private Long unumDocId;

	@Column(name="unum_process_id")
	private Long unumProcessId;

	@Column(name="unum_isvalid")
	private Integer unumIsvalid;
}