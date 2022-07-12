package in.cdac.university.authserver.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
public class UmmtUserMstId implements Serializable {

    @Column(name = "gnum_userid", unique = true, nullable = false, precision = 8)
    private Integer gnumUserId;

    @Column(name = "gstr_user_full_name", nullable = false, length = 300)
    private String gstrUserFullName;
}
