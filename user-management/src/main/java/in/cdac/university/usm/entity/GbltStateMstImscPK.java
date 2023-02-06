package in.cdac.university.usm.entity;

import lombok.*;

import javax.persistence.Column;
import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class GbltStateMstImscPK implements Serializable {

    @Column(name = "gnum_statecode")
    private Integer gnumStatecode;

    @Column(name = "gnum_isvalid")
    private Integer gnumIsvalid;

}
