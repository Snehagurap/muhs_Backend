package in.cdac.university.globalService.entity;

import lombok.*;

import javax.persistence.Column;
import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class GmstAdjacentDistrictDtlPK implements Serializable {

    @Column(name = "num_dist_id")
    private Integer numDistId;

    @Column(name = "num_adjacent_dist_id")
    private Integer numAdjacentDistId;

    @Column(name = "unum_isvalid")
    private Integer unumIsvalid;
}
