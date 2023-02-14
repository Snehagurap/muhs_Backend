package in.cdac.university.globalService.entity;

import lombok.*;

import javax.persistence.Column;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class CmstUnivCollegeEventMstPK implements Serializable {

    @Column(name = "unum_col_event_id")
    private Integer unumColEventId;

    @Column(name = "unum_isvalid")
    private Integer unumIsvalid;
}
