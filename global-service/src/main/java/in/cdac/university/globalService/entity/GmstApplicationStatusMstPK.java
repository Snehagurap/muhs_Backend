package in.cdac.university.globalService.entity;

import lombok.*;

import javax.persistence.Column;
import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class GmstApplicationStatusMstPK implements Serializable {

    @Column(name="unum_application_status_id")
    private Integer unumApplicationStatusId;

    @Column(name="unum_isvalid")
    private Integer unumIsvalid;
}
