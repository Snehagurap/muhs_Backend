package in.cdac.university.usm.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Getter
@Setter
@EqualsAndHashCode
@Embeddable
public class UmmtDatasetFilterPK implements java.io.Serializable {

	@Column(name = "gnum_dataset_id", nullable = false)
	private Integer gnumDatasetId;

	@Column(name = "gnum_sl_no", nullable = false)
	private Integer gnumSlNo;
}
