package in.cdac.university.globalService.entity;

import lombok.*;

import java.io.Serializable;
import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class GmstDocumentMstPK implements Serializable {

	@Column(name="unum_doc_id")
	private Long unumDocId;

	@Column(name="unum_isvalid")
	private Integer unumIsvalid;

}