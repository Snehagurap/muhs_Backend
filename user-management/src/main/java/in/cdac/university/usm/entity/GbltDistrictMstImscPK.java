package in.cdac.university.usm.entity;

import lombok.*;

import javax.persistence.Column;
import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class GbltDistrictMstImscPK implements Serializable {

    @Column(name = "num_dist_id")
    private Integer numDistId;

    @Column(name = "gnum_isvalid")
    private Integer gnumIsvalid;

}
