package in.cdac.university.usm.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Getter
@Setter
@EqualsAndHashCode
@Embeddable
@ToString
public class UmmtUserDatasetPK implements java.io.Serializable {

    @Column(name = "gnum_dataset_id", nullable = false)
    private Integer gnumDatasetId;

    @Column(name = "gnum_userid", nullable = false)
    private Long gnumUserId;

    @Column(name = "gstr_column_value", nullable= false)
    private String gstrColumnValue;

    @Column(name = "unum_univ_id", nullable = false)
    private Integer unumUnivId;
}
