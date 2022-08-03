package in.cdac.university.authserver.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UmmtUserMstId implements Serializable {

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ummt_user_mst")
    @SequenceGenerator(name = "ummt_user_mst", sequenceName = "seq_ummt_user_mst", allocationSize = 1)
    @Column(name = "gnum_userid", nullable = false)
    private Long gnumUserId;

    @Column(name = "gnum_isvalid", nullable = false, insertable = false, columnDefinition = "numeric default 1")
    private Integer gnumIsvalid;
}
